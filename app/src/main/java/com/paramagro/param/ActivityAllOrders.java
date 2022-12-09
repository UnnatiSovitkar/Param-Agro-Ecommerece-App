package com.paramagro.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paramagro.RecyclerAdapter.AllOrderAdapter;
import com.paramagro.RecyclerModelClass.AllOrderModel;
import com.example.param.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityAllOrders extends AppCompatActivity {
    Toolbar toolbar;
    //farmer Share prefference
    SharedPreferences sharedPreferences;//for header name and phno
    private static final String SHARED_PREF_NAME_FARMAR="mypref";
    private static final String Key_CUSTID_F="custid";

    String custId;
    ImageView empty_img;

    AllOrderAdapter adapter;
    List<AllOrderModel> list=new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        recyclerView=findViewById(R.id.orderrv);
        empty_img=findViewById(R.id.emt_cart_farmer);
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME_FARMAR, Context.MODE_PRIVATE);
        custId=sharedPreferences.getString(Key_CUSTID_F,null);

        System.out.println("custididid"+custId);
//        if(!(custId.equals(null))){
        if(custId!=null){

            empty_img.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new AllOrderAdapter(this, list);
        recyclerView.setAdapter(adapter);
        getData();
        }else {

            recyclerView.setVisibility(View.GONE);
            Toast.makeText(this, "No record found", Toast.LENGTH_SHORT).show();
        }
        //toolbar with back button

        toolbar=findViewById(R.id.toolbarallorder);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1="https://comzent.in/paramdash/apis/farmers/get_transaction.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ss",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
//                    String dd=respObj.getString("message");
//                    Log.d("dd",dd);

//                    if(!(dd.equals("No Records Found...!"))) {

                    JSONArray jsonArray = respObj.getJSONArray("proinfo");

                    Log.d("class details", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String orderid = jsonObject.getString("order_id");
                        String orderno = jsonObject.getString("order_no");
                        String orderamt = jsonObject.getString("order_amt");
                        String orderdate = jsonObject.getString("order_date");
                        String paymentstatus = jsonObject.getString("payment_status");
                        String orderstatus = jsonObject.getString("order_status");


                        AllOrderModel listModel = new AllOrderModel(orderid, orderno, orderamt, orderdate, paymentstatus, orderstatus);
                        list.add(listModel);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter=new AllOrderAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();


                params.put("cust_id",custId);



                return params;
            }
        };

        queue.add(request);


    }

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);


    }
}
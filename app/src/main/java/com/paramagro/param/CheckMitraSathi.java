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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paramagro.RecyclerAdapter.UserListAdapter;
import com.paramagro.RecyclerModelClass.CheckListModel;
import com.example.param.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckMitraSathi extends AppCompatActivity {

    TextView title;
    Toolbar toolbar;
    String type,id;
    SharedPreferences sharedPreferences_user;

    private static final String SHARED_PREF_NAME_USER="myprefuser";
    private static final String Key_USERID="uid";
    private static final String Key_USER_TYPE="utype";//used for checking is user param sathi or param mitra


    UserListAdapter adapter;//defining adpater
    List<CheckListModel> checklist=new ArrayList<>();//initializing model arraylist

    RecyclerView recyclerv;//defining recyclerview


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_mitra_sathi);

        title=findViewById(R.id.title_list);

//        Toast.makeText(this, "welcome", Toast.LENGTH_SHORT).show();

        //toolbar with back button

        toolbar=findViewById(R.id.toolbaruserlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences_user =getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        type=sharedPreferences_user.getString(Key_USER_TYPE,null);
        id=sharedPreferences_user.getString(Key_USERID,null);

        Log.d("cust_id",id);
/*
*this is used for saparate fetching of farmer list
*and parammitra list .if param sathi is logined in then parammitra list
* will display else farmer list will be displayed.
*
* here if user type is 2 then parammitra list will be displayed if type is 3
* then farmer list will be displayed.
 */
        //2:paramsathi and 3:parammitra
        if(type.equals("2")){

            title.setText("ParamMitra List");
            getParamMitraData();
        }
        else if(type.equals("3")){

            title.setText("Farmer List");
            getFarmerData();
        }
//        Toast.makeText(this, refid, Toast.LENGTH_SHORT).show();
        recyclerv=findViewById(R.id.recyclercheck);//initializing recycler view



        //gridlayout manager used for dispaying data in grid view
        recyclerv.setLayoutManager(new LinearLayoutManager(this));
        recyclerv.setHasFixedSize(true);
//        adapter=new ShoppingItemAdapter(getContext(),Myitemlist);
//        mrecyclerv.setAdapter(adapter);
        adapter=new UserListAdapter(this,checklist);
        recyclerv.setAdapter(adapter);

    }


    private void getFarmerData(){

        ////fetching staff Profile api using mobile number
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://comzent.in/paramdash/apis/farmers/get_farmer_list.php";//Api//
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject Obj = new JSONObject(response);
                    JSONArray jsonArray1 = Obj.getJSONArray("todayhw");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String instid = jsonObject.getString("cust_id");
                        String username = jsonObject.getString("cust_name");
                        String usermob = jsonObject.getString("cust_mobile");
                        String useradd = jsonObject.getString("cust_address");

                        CheckListModel cModel = new CheckListModel(instid,username,usermob,useradd);
                        checklist.add(cModel);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                adapter=new UserListAdapter(getApplicationContext(),checklist);
                recyclerv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    JSONObject respObj = new JSONObject(String.valueOf(error));
                    String errorMsg = respObj.getString("message");
                    Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter=new UserListAdapter(getApplicationContext(),checklist);
                recyclerv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parms = new HashMap<>();

                parms.put("us_id", id);


                return parms;
            }
        };
        //Adding the string request to the queue
        queue.add(request);



    }

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);


    }



    private void getParamMitraData(){

        ////fetching staff Profile api using mobile number
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://comzent.in/paramdash/apis/farmers/get_param_mitra_list.php";//Api//
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject Obj = new JSONObject(response);
                    JSONArray jsonArray1 = Obj.getJSONArray("todayhw");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String instid = jsonObject.getString("us_id");
                        String username = jsonObject.getString("us_name");
                        String usermob = jsonObject.getString("us_mobile");
                        String useradd = jsonObject.getString("us_address");

                        CheckListModel cModel = new CheckListModel(instid,username,usermob,useradd);
                        checklist.add(cModel);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                adapter=new UserListAdapter(getApplicationContext(),checklist);
                recyclerv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    JSONObject respObj = new JSONObject(String.valueOf(error));
                    String errorMsg = respObj.getString("message");
                    Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter=new UserListAdapter(getApplicationContext(),checklist);
                recyclerv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parms = new HashMap<>();

                parms.put("us_id", id);


                return parms;
            }
        };
        //Adding the string request to the queue
        queue.add(request);



    }

}
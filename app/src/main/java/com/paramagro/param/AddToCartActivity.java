package com.paramagro.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paramagro.RecyclerAdapter.ItemCartAdapter;
import com.paramagro.RecyclerModelClass.ItemCartModel;
import com.example.param.R;
import com.paramagro.param.interfaces.TotalListnerInterface;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddToCartActivity extends AppCompatActivity implements TotalListnerInterface, PaymentResultListener {
    //farmer Share prefference
    SharedPreferences sharedPreferences;//for header name and phno
    private static final String SHARED_PREF_NAME_FARMAR="mypref";
    private static final String KEY_MOBILE_F="mobile";

    //mitra sthi Share prefference
    SharedPreferences sharedPreferences_user;
    private static final String SHARED_PREF_NAME_USER="myprefuser";
    private static final String KEY_MOBILE_USER="umobile";

    private Management management;

    Toolbar toolbar;
    TextView name,description,total_p;
    ImageView img;
    String cust_id,mobp,mobf;
    String cart_id;
    String totalbill;
    RequestQueue requestQueue;
    ProgressDialog progress;
    Button check_out,continue_shopping;
    RecyclerView recyclerView;

    ItemCartModel itemCartModel;
    ItemCartAdapter adapter;
    List<ItemCartModel> itemlist=new ArrayList<>();
    LinearLayout cart_empty,cart_full;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        cart_empty = findViewById(R.id.emptyc);
        cart_full = findViewById(R.id.notempty);

        //this is for parammitra/sathi shareprefference
        sharedPreferences_user =getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        mobp= sharedPreferences_user.getString(KEY_MOBILE_USER,null);

        //fARMER share prefference details for header name and mob no
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME_FARMAR, Context.MODE_PRIVATE);
        mobf= sharedPreferences.getString(KEY_MOBILE_F,null);



        total_p = findViewById(R.id.total);
        recyclerView = findViewById(R.id.cart_items);
        check_out = findViewById(R.id.chout);
        continue_shopping = findViewById(R.id.contnu);

        ProgressDialog progress=new ProgressDialog(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);


        adapter = new ItemCartAdapter(this, itemlist, AddToCartActivity.this, total_p);
        recyclerView.setAdapter(adapter);


//        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,new IntentFilter("Mytotalprice"));
//        total_p.setText((int) adapter.getTotalPrice());
//        adapter.notifyDataSetChanged();

        //toolbar with back button

        toolbar = findViewById(R.id.toolbarcart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cust_id = getIntent().getExtras().getString("custid");

        Log.d("id", cust_id);

        getData();




            check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(total_p.getText().toString().equals("Total Amount"))){

                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra("totalbill", totalbill);
                    startActivity(intent);               
                }else {
                    Toast.makeText(AddToCartActivity.this, "Cart is Empty", Toast.LENGTH_SHORT).show();
                }

                /*
                if(!(totalbill.equals("Total Amount"))) {

                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra("totalbill", t_bill);
                    startActivity(intent);



                    makePayment();
                }else if(total_p.getText().toString().equals("Total Amount")){

                    Toast.makeText(AddToCartActivity.this, "There is no Items in a cart..!", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });

        continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mobp!=null){
                    Intent intent=new Intent(getApplicationContext(),ParamsathiMainActivity.class);
                    startActivity(intent);
                }
                if(mobf!=null){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

            }
        });
        //progress dialogue

        progress.setMessage("Please Wait");
        progress.show();

        //giving delay of 3 sec to progress dialogue
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.setCanceledOnTouchOutside(true);
                progress.dismiss();

            }
        },3000);


    }

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1="https://comzent.in/paramdash/apis/farmers/get_shoping_cart_list.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ClassDetails",response);

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
                            String proid = jsonObject.getString("pro_id");
                            String psid = jsonObject.getString("ps_id");
                            String proname = jsonObject.getString("pro_name");
                            String prodesc = jsonObject.getString("pro_desc");
                            String prodmrp = jsonObject.getString("ps_mrp_price");
                            String prodsell = jsonObject.getString("ps_sell_price");
                            String proddisc = jsonObject.getString("ps_discount");
                            String prodsize = jsonObject.getString("ps_size");
                            String proimg = jsonObject.getString("pro_img");
                            String proqty = jsonObject.getString("pro_qty");
                            String cartid = jsonObject.getString("cartid");


                            ItemCartModel listModel = new ItemCartModel(proid, psid, proname, prodesc, prodmrp, prodsell, proddisc,
                                    prodsize, proimg, proqty, cartid);
                            itemlist.add(listModel);
//                        int sum=0,i;
//                        for (i=0;itemlist.size();i++)
//                            sum=sum+(itemlist.get(i).getPs_sell_price()*itemlist.get(i).getPro_qty());
//
//
//                        total_p.setText(sum);

                        }

                        String obj = respObj.getString("get_totalamt_pro");
                        Log.d("tb", obj);
                        total_p.setText( "\u20B9" + obj+" /-");
                        totalbill = obj;


//                    }
//                    else {
//
//                        cart_empty.setVisibility(View.VISIBLE);
//                        cart_full.setVisibility(View.GONE);
//
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter=new ItemCartAdapter(getApplicationContext(),itemlist,AddToCartActivity.this,total_p);
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


                params.put("cust_id",cust_id);



                return params;
            }
        };

        queue.add(request);


    }



    @Override
    public void onItemView(int position) {


    }

    @Override
    public void onItemClick(int position) {

        cart_id=itemlist.get(position).getCartid();
        deleteItem();
        itemlist.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyDataSetChanged();
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);

        //giving delay of 3 sec to progress dialogue

    }

    private void deleteItem() {

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url1="https://comzent.in/paramdash/apis/farmers/cart_product_delete.php";


            StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        JSONObject respObj = new JSONObject(response);
                        Toast.makeText(AddToCartActivity.this, "Item Deleted Successfully..!", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


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


                    params.put("cartid",cart_id);



                    return params;
                }
            };

            queue.add(request);

    }

    private void makePayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_tX7PmT1fDEDB0X");
        checkout.setImage(R.drawable.paramlogo);
        final Activity activity = this;
        int total=Integer.parseInt(totalbill);

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Param Agro");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#B10435");
            options.put("currency", "INR");
            options.put("amount",total*100);//pass amount in currency subunits 500*100
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","8788831601");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 100);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }



    }
    //success dialogue
    public void withCustomIcon() {
        new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE).setTitleText("Success!").setContentText("Payment Successfull.").setCustomImage(R.drawable.check).show();

    }
    //failed dialogue
    public void errorMessage() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText("Something went wrong!").show();
    }


    @Override
    public void onPaymentSuccess(String s) {
//        textView.setText("Successful Payment Id:"+s);
        withCustomIcon();
    }

    @Override
    public void onPaymentError(int i, String s) {
//        textView.setText("Failed payment and caus is:"+s);
//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        errorMessage();

    }
}

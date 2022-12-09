package com.paramagro.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.param.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    Toolbar toolbar;

    Button button,shopbtn;
    TextView textView,totalamt,nametxt,mobiletxt,addresstxt,statustxt,datetxt,transtxt;
    String total,mobp,mobf,strDate,custid,custidf,custidp,orderno,razorpayId;
    SharedPreferences sharedPreferences_user;
    LinearLayout layout,succ_layout;

    //create a share preferences name and keys name
    //shareprefference of parammitra registration
    private static final String SHARED_PREF_NAME_USER="myprefuser";
    private static final String KEY_NAME_USER="uName";
    private static final String KEY_MOBILE_USER="umobile";
    private static final String Key_USERID="uid";
    private static final String Key_USER_TYPE="utype";
    private static final String Key_PARAMSATHIID_USER="uparamsathiid";
    private static final String Key_USER_ADDRESS="uparamsathiaddress";


    SharedPreferences sharedPreferences;
    //SharedPreferences sp;

    //create a share preferences of farmer name and keys name

    private static final String SHARED_PREF_NAME_FARMAR="mypref";
    private static final String KEY_NAME_F="FName";
    private static final String KEY_MOBILE_F="mobile";
    private static final String Key_CUSTID_F="custid";
    private static final String Key_PARAMSATHIID_F="paramsathiid";
    private static final String Key_FARMER_ADDRESS="farmeraddress";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        button=findViewById(R.id.paybtn);
        totalamt=findViewById(R.id.txttotal);
        nametxt=findViewById(R.id.txtnm);
        mobiletxt=findViewById(R.id.txtmn);
        addresstxt=findViewById(R.id.txtad);
        statustxt=findViewById(R.id.txtst);
        datetxt=findViewById(R.id.txtdt);
        transtxt=findViewById(R.id.txttid);
        layout=findViewById(R.id.layout_update);
        succ_layout=findViewById(R.id.layout_success);
        shopbtn=findViewById(R.id.backbtn);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyy");
        strDate = mdformat.format(calendar.getTime());

        //share prefference of paramsathi
        sharedPreferences_user =getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        mobp= sharedPreferences_user.getString(KEY_MOBILE_USER,null);
        custidp= sharedPreferences_user.getString(Key_USERID,null);

        //share prefference of farmer
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME_FARMAR, Context.MODE_PRIVATE);
        mobf= sharedPreferences.getString(KEY_MOBILE_F,null);
        custidf= sharedPreferences.getString(KEY_MOBILE_F,null);

        //check if farmer is logged in or mitra/sathi

        if(mobf!=null){
            nametxt.setText(sharedPreferences.getString(KEY_NAME_F,null));
            mobiletxt.setText(sharedPreferences.getString(KEY_MOBILE_F,null));
            addresstxt.setText(sharedPreferences.getString(Key_FARMER_ADDRESS,null));
            custid=custidf;

        }


        if(mobp!=null){
            nametxt.setText(sharedPreferences_user.getString(KEY_NAME_USER,null));
            mobiletxt.setText(sharedPreferences_user.getString(KEY_MOBILE_USER,null));
            addresstxt.setText(sharedPreferences_user.getString(Key_USER_ADDRESS,null));
            custid=custidp;


        }

        System.out.println("ididid"+custid);

        Checkout.preload(getApplicationContext());


        total=getIntent().getExtras().getString("totalbill");
        totalamt.setText("\u20B9"+total+"/-");

        getOrderNo();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    makePayment();

                }catch (Exception e){
                 e.printStackTrace();
                }
            }

        });

        shopbtn.setOnClickListener(new View.OnClickListener() {
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
        //toolbar with back button

        toolbar = findViewById(R.id.toolbarpayment);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void getOrderNo() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/pay_payment.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array0 = new JSONArray(response);
                    for(int i=0;i<array0.length();i++) {
                        JSONObject object1=array0.getJSONObject(i);
                        orderno = object1.getString("order_id");
                        Log.d("dd",orderno);

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("cust_id",custid);

                return parms;
            }
        };

        queue.add(request);

    }


    private void makePayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_tX7PmT1fDEDB0X");
        checkout.setImage(R.drawable.paramlogo);
        final Activity activity = this;

        Log.d("ooooo",orderno);
        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Param Agro");
            options.put("description", nametxt.getText().toString());
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
//            options.put("order_id", ""+orderno);//from response of step 3.
            options.put("prefill.order_id", ""+orderno);
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", Integer.parseInt(total)*100);//pass amount in currency subunits 500*100
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","8788831601");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 100);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            errorMessage();
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
        statustxt.setText("Paid");
        datetxt.setText(strDate);
        transtxt.setText(s);
        razorpayId=s;
        layout.setVisibility(View.VISIBLE);
        succ_layout.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        shopbtn.setVisibility(View.VISIBLE);
        verifyPayment();
    }

    @Override
    public void onPaymentError(int i, String s) {
        textView.setText("Failed payment and caus is:"+s);
        errorMessage();
        Log.d("errrr",s);

    }


    //sending payment details to server database

    public void verifyPayment(){


            ////fetching fees data using api//
            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            String url="https://comzent.in/paramdash/apis/farmers/verify_payment.php ";


            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {


                        JSONObject jsonObject=new JSONObject(response);
                        String msg=jsonObject.getString("proinfomessage");
                        Toast.makeText(PaymentActivity.this, msg, Toast.LENGTH_LONG).show();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();            }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> parms=new HashMap<>();
                    parms.put("order_id",orderno);
                    parms.put("cust_id",custid);
                    parms.put("razorpay_payment_id",razorpayId);

                    return parms;
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
package com.paramagro.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText name,mobn,add,refid,otp;
    Button registor,login,verify_otp,get_otp;
    Toolbar toolbar;
    TextView Login_alreadyRegistered;



    SharedPreferences sharedPreferences;
    //SharedPreferences sp;

    //create a share preferences name and keys name

    private static final String SHARED_PREF_NAME_FARMAR="mypref";
    private static final String KEY_NAME_F="FName";
    private static final String KEY_MOBILE_F="mobile";
    private static final String Key_CUSTID_F="custid";
    private static final String Key_PARAMSATHIID_F="paramsathiid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name=findViewById(R.id.custname);
        mobn=findViewById(R.id.custmobn);
        add=findViewById(R.id.custadd);
        refid=findViewById(R.id.custrefid);
        registor=findViewById(R.id.registeruser);
        Login_alreadyRegistered=findViewById(R.id.farmer_login);
        get_otp=findViewById(R.id.getotp);
        otp=findViewById(R.id.enterotp);
        verify_otp=findViewById(R.id.verifyotp);


        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME_FARMAR, Context.MODE_PRIVATE);


        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getOtpData();

            }
        });

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerifyOtpData();

            }
        });

        registor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(name.getText().toString().equals("") ||
                        mobn.getText().toString().equals("") ||
//                        password.getText().toString().equals("") ||
                        refid.getText().toString().equals(""))) {

                    Log.d("mob",mobn.getText().toString());
                    getData();
//                    Toast.makeText(RegistrationActivity.this, "Successfully registored", Toast.LENGTH_SHORT).show();

                }
                else{

                    Toast.makeText(RegistrationActivity.this, "Fill All the Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //login button

        Login_alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(RegistrationActivity.this,FarmarLoginActivity.class);
                startActivity(intent);
            }
        });

        //toolbar with back button

        toolbar=findViewById(R.id.toolbarfeedback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    private void getData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/farmer_create_account.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");
                    if(msg.equals("Registration Successfull..!")){

                        Toast.makeText(RegistrationActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), FarmarLoginActivity.class);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("cust_name",name.getText().toString());
                parms.put("cust_mobile",mobn.getText().toString());
                parms.put("cust_address",add.getText().toString());
//                parms.put("cust_password",password.getText().toString());
                parms.put("parammitrid",refid.getText().toString());
                parms.put("cust_status","1");


                return parms;
            }
        };

        queue.add(request);

    }


    //generate otp api when mobile no.is send

    private void getOtpData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/farmer_create_account_and_send_otp.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");



                    Log.d("msg",msg);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    if(msg.equals("SMS Request Is Initiated")) {


                        otp.setVisibility(View.VISIBLE);
                        get_otp.setVisibility(View.INVISIBLE);
                        verify_otp.setVisibility(View.VISIBLE);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("cust_mobile",mobn.getText().toString());
                parms.put("cust_name",name.getText().toString());
                parms.put("parammitrid",refid.getText().toString());
                parms.put("cust_status","1");

                return parms;
            }
        };

        queue.add(request);

    }

    //fetching api data


    private void VerifyOtpData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/farmer_verifyOtp.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");

                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                    Log.d("message",msg);
                    if(msg.equals(" Otp Verified Successfully")) {

                        JSONArray jsonArray=jsonObject.getJSONArray("techer_details");
                        for(int i=0;i<= jsonArray.length();i++) {
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String fnumber=jsonObject1.getString("cust_mobile");
                            Log.d("number",fnumber);
                            String fid=jsonObject1.getString("cust_id");
                            String fname=jsonObject1.getString("cust_name");
                            String psathiId=jsonObject1.getString("paramsatiid");


                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_MOBILE_F, fnumber);
                            editor.putString(KEY_NAME_F,fname);
                            editor.putString(Key_CUSTID_F,fid);
                            editor.putString(Key_PARAMSATHIID_F,psathiId);

                            //Saving values to editor
                            editor.commit();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            //goto Mainactivity
                            Intent j=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(j);
                        }


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("otp",otp.getText().toString());
                return parms;
            }
        };

        queue.add(request);

    }


}
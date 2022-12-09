package com.paramagro.param;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    Button Login,getOtpbtn;
    EditText mobno,otp;
    boolean passwordvisible;

    SharedPreferences sharedPreferences_user;

    //create a share preferences name and keys name
    //shareprefference of parammitra registration
    private static final String SHARED_PREF_NAME_USER="myprefuser";
    private static final String KEY_NAME_USER="uName";
    private static final String KEY_MOBILE_USER="umobile";
    private static final String Key_USERID="uid";
    private static final String Key_USER_TYPE="utype";
    private static final String Key_PARAMSATHIID_USER="uparamsathiid";
    private static final String Key_USER_ADDRESS="uparamsathiaddress";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login=findViewById(R.id.loginuser);
        mobno=findViewById(R.id.phn);//initialize phno
        otp=findViewById(R.id.otp);///initialize otp
        getOtpbtn=findViewById(R.id.getotp_mitra);///initialize password


        sharedPreferences_user =getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);

        //mobile number on textchanged validation
        mobno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(validateMobile(mobno.getText().toString())){

                    Login.setEnabled(true);

                }else {

                    mobno.setError("Invalid Mobile Number");
                    Login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getOtpbtn.setOnClickListener(new View.OnClickListener() {//click on login button
            @Override
            public void onClick(View v) {

               getOtpData();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {//click on login button
            @Override
            public void onClick(View v) {

               verifyOtpData();

            }
        });


    }


    private void getData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/userlogin.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");
                    String uid=jsonObject.getString("us_id");
                    String utype=jsonObject.getString("user_type");
                    String umob=jsonObject.getString("us_mobile");
                    String uname=jsonObject.getString("us_name");
                    String urefid=jsonObject.getString("referid");


                    Log.d("uname",uname);
                    if(msg.equals("Login Success")){

                        //put data on shared preference
                        SharedPreferences.Editor editoru = sharedPreferences_user.edit();
                        editoru.putString(KEY_MOBILE_USER, umob);
                        editoru.putString(KEY_NAME_USER,uname);
                        editoru.putString(Key_USERID,uid);
                        editoru.putString(Key_PARAMSATHIID_USER,urefid);
                        editoru.putString(Key_USER_TYPE,utype);

//                        Saving values to editor
                        editoru.commit();
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        if(utype.equals("2")){
                            Toast.makeText(LoginActivity.this, "welcome paramsathi", Toast.LENGTH_SHORT).show();
                        }else if(utype.equals("3")){
                            Toast.makeText(LoginActivity.this, "welcome parammitra", Toast.LENGTH_SHORT).show();
                        }
                        //goto Mainactivity
                        Intent i=new Intent(getApplicationContext(),ParamsathiMainActivity.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(LoginActivity.this, "Incorrect mobile no & password", Toast.LENGTH_SHORT).show();
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
                parms.put("us_mobile",mobno.getText().toString());

                return parms;
            }
        };

        queue.add(request);

    }




    private void getOtpData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/userlogin.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");

                    if(msg.equals("SMS Request Is Initiated")){

                        otp.setVisibility(View.VISIBLE);
                        getOtpbtn.setVisibility(View.GONE);
                        Login.setVisibility(View.VISIBLE);

                    }else {
                        Toast.makeText(getApplicationContext(), "Wrong Mobile Number", Toast.LENGTH_SHORT).show();
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
                parms.put("us_mobile",mobno.getText().toString());

                return parms;
            }
        };

        queue.add(request);

    }

    private void verifyOtpData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/user_verifyOtp.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");

                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                    Log.d("message",msg);
                    if(msg.equals(" Otp Verified Successfully")) {

                        JSONArray jsonArray=jsonObject.getJSONArray("paramuser_details");
                        for(int i=0;i<= jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String uid=jsonObject1.getString("us_id");
                            String usunid=jsonObject1.getString("us_un_id");
                            String utype=jsonObject1.getString("user_type");
                            String umob=jsonObject1.getString("us_mobile");
                            String uadd=jsonObject1.getString("us_address");

                            Log.d("number", umob);

                            String uname=jsonObject1.getString("us_name");
                            String urefid=jsonObject1.getString("referid");


                            //put data on shared preference
                            SharedPreferences.Editor editoru = sharedPreferences_user.edit();
                            editoru.putString(KEY_MOBILE_USER, umob);
                            editoru.putString(KEY_NAME_USER, uname);
                            editoru.putString(Key_USERID, uid);
                            editoru.putString(Key_PARAMSATHIID_USER, urefid);
                            editoru.putString(Key_USER_TYPE, utype);
                            editoru.putString(Key_USER_ADDRESS, uadd);


//                        Saving values to editor
                            editoru.commit();
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();

                            if (utype.equals("2")) {
                                Toast.makeText(LoginActivity.this, "Welcome Paramsathi", Toast.LENGTH_SHORT).show();
                            } else if (utype.equals("3")) {
                                Toast.makeText(LoginActivity.this, "Welcome Parammitra", Toast.LENGTH_SHORT).show();
                            }
                            //goto Mainactivity
                            Intent k = new Intent(getApplicationContext(), ParamsathiMainActivity.class);
                            startActivity(k);

                        }

                        }else {
                            Toast.makeText(LoginActivity.this, "Incorrect Mobile no & Password", Toast.LENGTH_SHORT).show();
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



    //validation of mob no//
    private boolean validateMobile(String number){//validation of mob no
        if(number.length()==0){//validation of mob no
            mobno.requestFocus();//validation of mob no
            mobno.setError("field cannot be empty");//validation of mob no
            return false;//validation of mob no
        }else if(!number.matches("^[6-9][0-9]{9}$")) {//validation of mob no
            mobno.requestFocus();//validation of mob no
            mobno.setError("Invalid Mobile No");//validation of mob no
            return false;//validation of mob no
        }//validation of mob no
        else {//validation of mob no
            return true;//validation of mob no
        }//validation of mob no
    }


}
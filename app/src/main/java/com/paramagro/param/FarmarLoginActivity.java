package com.paramagro.param;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class FarmarLoginActivity extends AppCompatActivity {

    Button Login,getOtp;
    EditText fmobno,fpassword,otp;
    boolean passwordvisible;

    SharedPreferences sharedPreferences;
    //SharedPreferences sp;


    //create a share preferences name and keys name
    private static final String SHARED_PREF_NAME_FARMAR="mypref";
    private static final String KEY_NAME_F="FName";
    private static final String KEY_MOBILE_F="mobile";
    private static final String Key_CUSTID_F="custid";
    private static final String Key_PARAMSATHIID_F="farmerid";
    private static final String Key_FARMER_ADDRESS="farmeraddress";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmar_login);

        Login=findViewById(R.id.loginfarm);
        fmobno=findViewById(R.id.farmphn);//initialize phno
        fpassword=findViewById(R.id.farmpass);///initialize password
        getOtp=findViewById(R.id.otpbtn);
        otp=findViewById(R.id.otptxt);


       sharedPreferences =getSharedPreferences(SHARED_PREF_NAME_FARMAR, Context.MODE_PRIVATE);
/*
        //visibility of password
        fpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=fpassword.getRight()-fpassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=fpassword.getSelectionEnd();
                        if(passwordvisible){
                            ///set drawable image here
                            fpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            //for hide password
                            fpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible=false;
                        }else{
                            //set Drawable image here
                            fpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            //for show password
                            fpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible=true;
                        }
                        fpassword.setSelection(selection);
                    }
                }
                return false;
            }
        });

 */

        getOtp.setOnClickListener(new View.OnClickListener() {//click on login button
            @Override
            public void onClick(View v) {

                boolean check = validateMobile(fmobno.getText().toString());//validation of mob//
                //checking validation//
                if (check == true) {
                    getData();

                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {//click on login button
            @Override
            public void onClick(View v) {


                    verifyOtpData();

            }
        });

    }



//function getData() is used here for fetching data from api//
    private void getData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/farmer_login.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");

                    if(msg.equals("SMS Request Is Initiated")){

                        otp.setVisibility(View.VISIBLE);
                        getOtp.setVisibility(View.GONE);
                        Login.setVisibility(View.VISIBLE);

                    }else {
                        Toast.makeText(FarmarLoginActivity.this, "Wrong Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(FarmarLoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("cust_mobile",fmobno.getText().toString());

                return parms;
            }
        };

        queue.add(request);

    }

    private void verifyOtpData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/farmer_verifyOtp.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");


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
                            String uadd=jsonObject1.getString("cust_address");

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_MOBILE_F, fnumber);
                            editor.putString(KEY_NAME_F,fname);
                            editor.putString(Key_CUSTID_F,fid);
                            editor.putString(Key_PARAMSATHIID_F,psathiId);
                            editor.putString(Key_FARMER_ADDRESS, uadd);

                            //Saving values to editor
                            editor.commit();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            //goto Mainactivity
                            Intent j=new Intent(getApplicationContext(),FarmerMainActivity.class);
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


    //validation of mob no//
    private boolean validateMobile(String number){//validation of mob no
        if(number.length()==0){//validation of mob no
            fmobno.requestFocus();//validation of mob no
            fmobno.setError("field cannot be empty");//validation of mob no
            return false;//validation of mob no
        }else if(!number.matches("^[6-9][0-9]{9}$")) {//validation of mob no
            fmobno.requestFocus();//validation of mob no
            fmobno.setError("Invalid Mobile No");//validation of mob no
            return false;//validation of mob no
        }//validation of mob no
        else {//validation of mob no
            return true;//validation of mob no
        }//validation of mob no
    }

}
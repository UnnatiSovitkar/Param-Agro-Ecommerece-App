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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterSathiMitraActivity extends AppCompatActivity {
    TextView name;
//    RadioGroup rgb;
//    RadioButton rb;
    EditText mobno,address,custName,password;
    Button register;
    Toolbar toolbar;
    String refid,userType,id;

    SharedPreferences sharedPreferences_user;

    private static final String SHARED_PREF_NAME_USER="myprefuser";
    private static final String KEY_NAME_USER="uName";

    private static final String Key_USERID="uid";

    private static final String Key_PARAMSATHIID_USER="uparamsathiid";
    private static final String Key_USER_TYPE="utype";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sathi_mitra);
        name=findViewById(R.id.user_name);
        mobno=findViewById(R.id.cust_mobn);
        custName=findViewById(R.id.new_custname);
        address=findViewById(R.id.cust_add);
        register=findViewById(R.id.registeruser_f);

        sharedPreferences_user =getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        refid=sharedPreferences_user.getString(Key_PARAMSATHIID_USER,null);
        userType=sharedPreferences_user.getString(Key_USER_TYPE,null);
        id=sharedPreferences_user.getString(Key_USERID,null);

        name.setText(sharedPreferences_user.getString(KEY_NAME_USER,null));
//        String reff_id=sharedPreferences_user.getString(Key_USERID,null);


        Log.d("userid",refid);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*
                 *this is used for saparate fetching of farmer list
                 *and parammitra list .if param sathi is logined in then parammitra list
                 * will display else farmer list will be displayed.
                 *
                 * here if user type is 2 then parammitra list will be displayed if type is 3
                 * then farmer list will be displayed.
                 */

                //2:paramsathi and 3:parammitra

                if(userType.equals("2")){
                    Log.d("id",userType);
                    getParamMitraData();

                }
                else if(userType.equals("3")){

                    getFarmerData();

                }


/*
                if (!(name.getText().toString().equals("") ||
                        mobno.getText().toString().equals("") ||
//                        password.getText().toString().equals("") ||
                        address.getText().toString().equals(""))) {


//                    getData();
//                int sid=rgb.getCheckedRadioButtonId();
//                rb=(RadioButton)findViewById(sid);
//                if(sid==-1){
//                    Toast.makeText(RegisterSathiMitraActivity.this, "please select gender", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(RegisterSathiMitraActivity.this, rb.getText().toString(), Toast.LENGTH_SHORT).show();
//                }
                    Intent intent = new Intent(getApplicationContext(), ParamsathiMainActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(RegisterSathiMitraActivity.this, "Fill All the Fields", Toast.LENGTH_SHORT).show();
                }

 */


            }
        });


        //toolbar with back button

        toolbar=findViewById(R.id.toolbarfeedback_f);
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

    private void getParamMitraData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/param_mitra_create_account.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");
                    if(msg.equals("SMS Request Is Initiated")){

                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), ParamsathiMainActivity.class);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("referid",id);
                parms.put("user_type",userType);

                parms.put("us_name",custName.getText().toString());
                parms.put("us_mobile",mobno.getText().toString());
//                parms.put("cust_email",id);
                parms.put("us_username",address.getText().toString());

                parms.put("us_address",address.getText().toString());
                parms.put("us_status","1");





                return parms;
            }
        };

        queue.add(request);

    }


    private void getFarmerData() {

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

                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), ParamsathiMainActivity.class);
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
                parms.put("cust_name",custName.getText().toString());
                parms.put("cust_mobile",mobno.getText().toString());
                parms.put("cust_address",address.getText().toString());
                parms.put("parammitrid",id);//userid
                parms.put("cust_status","1");


                return parms;
            }
        };

        queue.add(request);

    }


}
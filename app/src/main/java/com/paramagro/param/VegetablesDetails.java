package com.paramagro.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paramagro.RecyclerAdapter.PikMahitiDetailListAdapter;
import com.paramagro.RecyclerModelClass.PikmahitiDetailModel;
import com.example.param.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VegetablesDetails extends AppCompatActivity {
    Toolbar toolbar;
    TextView name,description;
    ImageView img;
    String item_id;
    RequestQueue requestQueue;

    RecyclerView recyclerView;


    PikMahitiDetailListAdapter adapter;
    List<PikmahitiDetailModel> piklist=new ArrayList<>();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetables_details);

        name=findViewById(R.id.titleitem);
//        description=findViewById(R.id.descitem);
//        img=findViewById(R.id.imgitem);



        recyclerView=findViewById(R.id.pikdetail_subitems);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);//product recycler view
        recyclerView.setHasFixedSize(true);



        adapter=new PikMahitiDetailListAdapter(this,piklist);
        recyclerView.setAdapter(adapter);


        //toolbar with back button

        toolbar=findViewById(R.id.toolbarfeedback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        item_id=getIntent().getExtras().getString("id");

        Log.d("id",item_id);

        name.setText(getIntent().getExtras().getString("name"));
//        description.setText(getIntent().getExtras().getString("desc"));

        getData();

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
        String url1="https://comzent.in/paramdash/apis/farmers/get_pickinfo.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ClassDetails",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    JSONArray jsonArray=respObj.getJSONArray("todayhw");

                    Log.d("class details",jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("pfam_id");
                        String name = jsonObject.getString("pfam_name");
                        String desc = jsonObject.getString("pfam_desc");
                        String img = jsonObject.getString("pick_photos");
                        String dateTime = jsonObject.getString("pfam_cdate");

                        PikmahitiDetailModel pikModel = new PikmahitiDetailModel(id,name,desc,img,dateTime);
                        piklist.add(pikModel);

                    }
                    Log.d("ClassName",respObj.getString("class_name"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter=new PikMahitiDetailListAdapter(getApplicationContext(),piklist);
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


                params.put("cat_id",item_id);



                return params;
            }
        };

        queue.add(request);
    }

}
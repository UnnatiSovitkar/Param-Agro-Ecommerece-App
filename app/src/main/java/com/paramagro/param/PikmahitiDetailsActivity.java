package com.paramagro.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.paramagro.RecyclerAdapter.PikMahitiListAdapter;
import com.paramagro.RecyclerModelClass.PikmahitiModel;
import com.example.param.R;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PikmahitiDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    SearchView searchView;

    RecyclerView recyclerv;

    RequestQueue requestQueue;//defining request for volley get


    PikMahitiListAdapter adapter;
    List<PikmahitiModel> piklist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pikmahiti_details);


        //searchbar//
        searchView=findViewById(R.id.search_v);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterlist(newText);
                return true;
            }


        });///search view//
        //toolbar with back button

        toolbar=findViewById(R.id.toolbar_feedback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerv=findViewById(R.id.recycler_veg);


        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        recyclerv.setLayoutManager(gridLayoutManager);
        recyclerv.setHasFixedSize(true);



        adapter=new PikMahitiListAdapter(getApplicationContext(),piklist);
        recyclerv.setAdapter(adapter);

        getDataPikMahiti();


    }

    ///search tab///

    private void filterlist(String newText) {

        List<PikmahitiModel> filteredList=new ArrayList<>();

        for(PikmahitiModel list: piklist){
            if(list.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(list);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
        else{

            adapter.setFilteredList(filteredList);
        }
    }

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);



    }

    private void getDataPikMahiti() {

        requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/get_category.php";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray=response.getJSONArray("todayhw");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        String id = jsonObject.getString("cat_id");
                        String name = jsonObject.getString("cat_name");
                        String desc = jsonObject.getString("cat_description");

                        String img = jsonObject.getString("cat_img");

                        CleanerProperties props = new CleanerProperties();//removing html tags
                        props.setPruneTags("script");//removing html tags
                        String result = new HtmlCleaner(props).clean(desc).getText().toString();//removing html tags

                        PikmahitiModel pikModel = new PikmahitiModel(id,name,result,img);
                        piklist.add(pikModel);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                adapter=new PikMahitiListAdapter(getApplicationContext(),piklist);
                recyclerv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
    //end of api fetch data

}
package com.paramagro.param.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.paramagro.RecyclerAdapter.PikMahitiListAdapter;
import com.paramagro.RecyclerModelClass.PikmahitiModel;
import com.example.param.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PikVyvsthapanFragment extends Fragment {

    SearchView searchView;
    RequestQueue requestQueue;

    RecyclerView mrecyclerv;

    PikMahitiListAdapter adapter;
    List<PikmahitiModel> Mymodellist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pikvyvasthapan_fragment,container,false);

        //searchbar//
        searchView=view.findViewById(R.id.searchv_layout);
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


        mrecyclerv=view.findViewById(R.id.recyclerveg_layout);
        Mymodellist=new ArrayList<>();

//        Mymodellist.add(new PikmahitiModel("कॉलीफ्लावर",R.drawable.cauli));
//        Mymodellist.add(new PikmahitiModel("कारले",R.drawable.bitterg));
//        Mymodellist.add(new PikmahitiModel("कापूस",R.drawable.cotton));
//        Mymodellist.add(new PikmahitiModel("कोबी",R.drawable.cabage));
//        Mymodellist.add(new PikmahitiModel("मका",R.drawable.corn));
//        Mymodellist.add(new PikmahitiModel("भेंडी",R.drawable.ladyfin));
//        Mymodellist.add(new PikmahitiModel("कांदा",R.drawable.onion));
//        Mymodellist.add(new PikmahitiModel("वांगी",R.drawable.brinjal));

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
        mrecyclerv.setLayoutManager(gridLayoutManager);
        mrecyclerv.setHasFixedSize(true);

        adapter=new PikMahitiListAdapter(getContext(),Mymodellist);
        mrecyclerv.setAdapter(adapter);
        getDataPikMahiti();
        return view;
    }

    ///search tab///

    private void filterlist(String newText) {

        List<PikmahitiModel> filteredList=new ArrayList<>();

        for(PikmahitiModel list: Mymodellist){
            if(list.getName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(list);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{

            adapter.setFilteredList(filteredList);
        }
    }

    private void getDataPikMahiti() {

        requestQueue= Volley.newRequestQueue(getContext());
        String url="https://comzent.in/paramdash/apis/farmers/get_category.php";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray=response.getJSONArray("todayhw");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String prId = jsonObject.getString("cat_id");

                        String proNam = jsonObject.getString("cat_name");
                        String proDes = jsonObject.getString("cat_description");
                        String prodImg = jsonObject.getString("cat_img");


                        PikmahitiModel pikModel = new PikmahitiModel(prId,proNam,proDes,prodImg);
                        Mymodellist.add(pikModel);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                adapter=new PikMahitiListAdapter(getContext(),Mymodellist);
                mrecyclerv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
    //end of api fetch data
}

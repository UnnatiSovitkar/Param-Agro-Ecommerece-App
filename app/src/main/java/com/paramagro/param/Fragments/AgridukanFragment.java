package com.paramagro.param.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
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
import com.paramagro.RecyclerAdapter.ShoppingItemAdapter;
import com.paramagro.RecyclerModelClass.ProductdModel;
import com.example.param.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AgridukanFragment extends Fragment {

    ShoppingItemAdapter adapter;//defining adpater
    RequestQueue requestQueue;//defining request for volley get

    SearchView searchView;//defining searchview
    List<ProductdModel> itemlist=new ArrayList<>();//initializing model arraylist

    RecyclerView mrecyclerv;//defining recyclerview

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.agridukan_fragment,container,false);

        mrecyclerv=view.findViewById(R.id.recycleritems);//initializing recycler view
        ProgressDialog progressDialog=new ProgressDialog(getContext());//progress dialouge initialization

        //progress dialogue
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        //giving delay of 3 sec to progress dialogue
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.dismiss();

            }
        },1800);


//        //searchbar//
        searchView=view.findViewById(R.id.searchdukan);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newTextS) {
                filterlistS(newTextS);
                return true;
            }


       });///search view//
        //gridlayout manager used for dispaying data in grid view
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        mrecyclerv.setLayoutManager(gridLayoutManager);
        mrecyclerv.setHasFixedSize(true);
//        adapter=new ShoppingItemAdapter(getContext(),Myitemlist);
//        mrecyclerv.setAdapter(adapter);
        adapter=new ShoppingItemAdapter(getActivity(),itemlist);
        mrecyclerv.setAdapter(adapter);
        getData();//function used for volley json data fetch
        return view;
    }

     ///search tab///
    private void filterlistS(String newTextS) {

        List<ProductdModel> filteredListS=new ArrayList<>();

        for(ProductdModel listS: itemlist){
            if(listS.getProname().toLowerCase().contains(newTextS.toLowerCase())){
                filteredListS.add(listS);
            }
        }
        if (filteredListS.isEmpty()){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{

            adapter.setFilteredListS(filteredListS);
        }
    }

    //function for fetching gson data by volley lib
    private void getData() {

        requestQueue= Volley.newRequestQueue(getActivity());
        String url="https://comzent.in/paramdash/apis/farmers/get_products.php";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray=response.getJSONArray("proinfo");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String prId = jsonObject.getString("pro_id");
                        String psid = jsonObject.getString("ps_id");
                        String proNam = jsonObject.getString("pro_name");
                        String proDes = jsonObject.getString("pro_desc");
                        String pssize = jsonObject.getString("ps_size");
                        String prodImg = jsonObject.getString("pro_img");
                        String prodMrp = jsonObject.getString("ps_mrp_price");
                        String prodSellp = jsonObject.getString("ps_sell_price");
                        String disc = jsonObject.getString("ps_discount");


                        ProductdModel pModel = new ProductdModel(prId,psid,proNam,proDes,pssize,prodImg,prodMrp,prodSellp,disc);
                        itemlist.add(pModel);
                     }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                adapter=new ShoppingItemAdapter(getActivity(),itemlist);
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

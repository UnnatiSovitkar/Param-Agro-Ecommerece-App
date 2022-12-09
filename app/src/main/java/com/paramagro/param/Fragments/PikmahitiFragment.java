package com.paramagro.param.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.paramagro.RecyclerAdapter.PikMahitiListAdapter;
import com.paramagro.RecyclerAdapter.ShoppingItemAdapter;
import com.paramagro.RecyclerModelClass.PikmahitiModel;
import com.paramagro.RecyclerModelClass.ProductdModel;
import com.paramagro.param.PikmahitiDetailsActivity;
import com.example.param.R;
import com.paramagro.param.ReferAndEarn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PikmahitiFragment extends Fragment {

    ViewFlipper flipper;
    LinearLayout pikpaha;

    ShoppingItemAdapter adapter;//defining adpater
    PikMahitiListAdapter pikadapter;

    RequestQueue requestQueue;//defining request for volley get

    SearchView searchView;//defining searchview
    List<ProductdModel> itemlist=new ArrayList<>();//initializing model arraylist
    List<PikmahitiModel> piklist=new ArrayList<>();//initializing model arraylist


    RecyclerView mrecyclerv;//defining recyclerview
    RecyclerView rv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pikmahiti_fragment,container,false);
        CardView cardView=view.findViewById(R.id.refandearn);//reffer and earn
        ///vegetable details


        pikpaha=view.findViewById(R.id.pikepahalayout);


        //auto sliding images
        flipper=view.findViewById(R.id.flipper);
        int imgarray[]={R.drawable.plant,R.drawable.agriculture,R.drawable.farming};

        for(int i=0;i<imgarray.length;i++)
            showimage(imgarray[i]);

        //sliding image end//

        pikpaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getContext(), PikmahitiDetailsActivity.class);
                startActivity(i);
            }
        });

//        //refer and earn activity
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), ReferAndEarn.class);
                startActivity(i);
            }
        });



        /////agridukan


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


//
        //gridlayout manager used for dispaying data in grid view
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        mrecyclerv.setLayoutManager(gridLayoutManager);//product recycler view
        mrecyclerv.setHasFixedSize(true);
//        adapter=new ShoppingItemAdapter(getContext(),Myitemlist);
//        mrecyclerv.setAdapter(adapter);
        adapter=new ShoppingItemAdapter(getActivity(),itemlist);
        mrecyclerv.setAdapter(adapter);
        getData();//function used for volley json data fetch


        ////product detail adpater and recyclerview


        rv=view.findViewById(R.id.recycler_pik);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
//        adapter=new ShoppingItemAdapter(getContext(),Myitemlist);
//        mrecyclerv.setAdapter(adapter);
        pikadapter=new PikMahitiListAdapter(getActivity(),piklist);
        rv.setAdapter(pikadapter);
        getDataPikMahiti();//function used for volley json data fetch


        return view;
    }

    private void showimage(int img) {

        ImageView imageView=new ImageView(getContext());
        imageView.setBackgroundResource(img);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
        flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);

    }



    ///agridukan
/*
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

 */

    //function for fetching gson data by volley lib for product dukan
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

   ////fetching api for pikmahiti//

    private void getDataPikMahiti() {

        requestQueue= Volley.newRequestQueue(getActivity());
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




                        PikmahitiModel pikModel = new PikmahitiModel(id,name,desc,img);
                        piklist.add(pikModel);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                pikadapter=new PikMahitiListAdapter(getActivity(),piklist);
                rv.setAdapter(pikadapter);
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

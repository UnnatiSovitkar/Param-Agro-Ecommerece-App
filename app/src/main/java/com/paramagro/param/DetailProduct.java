package com.paramagro.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paramagro.RecyclerAdapter.ProdSizeListAdapter;
import com.paramagro.RecyclerModelClass.ProdSizeModel;
import com.example.param.R;
import com.paramagro.param.interfaces.EventListnerInterface;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailProduct extends AppCompatActivity implements EventListnerInterface {
    Button increment,decrement,addCart,gotoCart;
    TextView val,detailT,desc,P_price,kgunit,mrp_price,discount;
    ImageView img,img2,img3,img4,img5,img6,img7,shreimg;
    Toolbar toolbar;
    String prodid,psid,custid,qty,status,id_p,id_f;
    RecyclerView recyclerView;


    ProdSizeListAdapter adapter;
    List<ProdSizeModel> list=new ArrayList<>();

    //parammitra shareprefference
    SharedPreferences sharedPreferences_user;
    private static final String SHARED_PREF_NAME_USER="myprefuser";
    private static final String Key_USERID="uid";

    //farmer sharepreffernce

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME_FARMAR="mypref";
    private static final String Key_CUSTID_F="custid";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        increment=findViewById(R.id.inr);//defining incr button
        decrement=findViewById(R.id.dcr);//defining dcr button
        val=findViewById(R.id.value);//defining value textview
        img=findViewById(R.id.imgproduct);//defining bigger imageview
        img2=findViewById(R.id.i1);//defining small imges
        img3=findViewById(R.id.i2);//defining small imges
        img4=findViewById(R.id.i3);//defining small imges
        img5=findViewById(R.id.i4);//defining small imges
        img6=findViewById(R.id.i5);//defining small imges
        img7=findViewById(R.id.i6);//defining small imges
        detailT=findViewById(R.id.title_deatilpro);//defining title textview
        desc=findViewById(R.id.proddesc);//defining desc textview
        P_price=findViewById(R.id.price);//defining price textview
        addCart=findViewById(R.id.addtocart);//defining price textview
        gotoCart=findViewById(R.id.gotocart);//defining price textview
        kgunit=findViewById(R.id.kgunit);//defining price textview
        mrp_price=findViewById(R.id.mrp);//defining mrp textview
        discount=findViewById(R.id.disc);//defining disc textview
        recyclerView=(RecyclerView) findViewById(R.id.recycler_prod_size);
        shreimg=findViewById(R.id.imgshre);


        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);//product recycler view
        adapter=new ProdSizeListAdapter(getApplicationContext(),list,DetailProduct.this);
        recyclerView.setAdapter(adapter);



        shreimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Demo Share");
                    String sharemsg=getIntent().getExtras().getString("Title")+
                            "\n\n"+getIntent().getExtras().getString("description")+
                            "\n\n"+"\u20B9"+getIntent().getExtras().getString("sellpr");
                    intent.putExtra(Intent.EXTRA_TEXT,sharemsg);
                    startActivity(Intent.createChooser(intent,"share by"));

                }catch (Exception e){
                    Toast.makeText(DetailProduct.this, "Error occured", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //parammitra share refference
        sharedPreferences_user =getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        id_p=sharedPreferences_user.getString(Key_USERID,null);

        //farmer shareprefference
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME_FARMAR, Context.MODE_PRIVATE);
        id_f=sharedPreferences.getString(Key_CUSTID_F,null);


        if(id_p!=null){
            custid=id_p;
        }
        if(id_f!=null){
            custid=id_f;
        }

        detailT.setText(getIntent().getExtras().getString("Title"));//setting data coming from adapter
        desc.setText(getIntent().getExtras().getString("description"));//setting data coming from adapter
        P_price.setText("\u20B9"+getIntent().getExtras().getString("sellpr")+"/-");//setting data coming from adapter
        kgunit.setText(getIntent().getExtras().getString("psize"));//setting data coming from adapter

        prodid=getIntent().getExtras().getString("id");
        psid=getIntent().getExtras().getString("psid");



        SpannableString spannableString=new SpannableString("\u20B9"+getIntent().getExtras().getString("mrp")+"/-");
        Log.d("snap",spannableString.toString());
        StrikethroughSpan strikethroughSpan=new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mrp_price.setText(spannableString);//setting data coming from adapter
        discount.setText("%"+getIntent().getExtras().getString("discount")+" Off");//setting data coming from adapter



//        newT.setText(getIntent().getExtras().getString("description"));
        Glide.with(this)
                .load(getIntent().getExtras().getString("newsimg"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(R.drawable.noimagefound)
                .into(img);

        Glide.with(this)
                .load(getIntent().getExtras().getString("newsimg"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(R.drawable.noimagefound)
                .into(img2);
        Glide.with(this)
                .load(getIntent().getExtras().getString("newsimg"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(R.drawable.noimagefound)
                .into(img3);
        Glide.with(this)
                .load(getIntent().getExtras().getString("newsimg"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(R.drawable.noimagefound)
                .into(img4);
        Glide.with(this)
                .load(getIntent().getExtras().getString("newsimg"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(R.drawable.noimagefound)
                .into(img5);
        Glide.with(this)
                .load(getIntent().getExtras().getString("newsimg"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(R.drawable.noimagefound)
                .into(img6);

        Glide.with(this)
                .load(getIntent().getExtras().getString("newsimg"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(R.drawable.noimagefound)
                .into(img7);

        //on clicking image 2 ,set img2 in image view
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getApplicationContext())
                        .load(getIntent().getExtras().getString("newsimg"))
                        .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                        .error(R.drawable.noimagefound)
                        .into(img);            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getApplicationContext())
                        .load(getIntent().getExtras().getString("newsimg"))
                        .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                        .error(R.drawable.noimagefound)
                        .into(img);            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getApplicationContext())
                        .load(getIntent().getExtras().getString("newsimg"))
                        .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                        .error(R.drawable.noimagefound)
                        .into(img);            }
        });

        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getApplicationContext())
                        .load(getIntent().getExtras().getString("newsimg"))
                        .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                        .error(R.drawable.noimagefound)
                        .into(img);            }
        });

        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getApplicationContext())
                        .load(getIntent().getExtras().getString("newsimg"))
                        .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                        .error(R.drawable.noimagefound)
                        .into(img);            }
        });

        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getApplicationContext())
                        .load(getIntent().getExtras().getString("newsimg"))
                        .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                        .error(R.drawable.noimagefound)
                        .into(img);            }
        });

        ////increament quantity by clicking on this button///
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemval=Integer.parseInt(val.getText().toString());
                itemval=itemval+1;
                val.setText(String.valueOf(itemval));
            }
        });

        ///decreament quantity by clicking on this button///
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemval=Integer.parseInt(val.getText().toString());
                if(!(itemval<=1)){
                itemval=itemval-1;
                val.setText(String.valueOf(itemval));
                }else {
                    Toast.makeText(DetailProduct.this, "select quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
        gotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(id_p==null && id_f==null){
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    /*
                    Fragment fragment=new ScanFragment();
                    FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.framecontainer,fragment).commit();


                     */
                    Toast.makeText(getApplicationContext(), "For Shopping Please Login First", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("farmerid",custid);
                    Intent c=new Intent(getApplicationContext(),AddToCartActivity.class);
                    c.putExtra("custid",custid);//transfer of data through intent
                    startActivity(c);

                }

            }
        });
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_p==null && id_f==null){
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "For Shopping Please Login First", Toast.LENGTH_SHORT).show();
                }else {
                    addToCart();
                }

            }
        });


        //toolbar with back button

        toolbar=findViewById(R.id.toolbarfeedback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getProdSize();




    }

    //cart menu showing
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }

        //go to cart
        if(item.getItemId()==R.id.cart){
            if(id_p==null && id_f==null){

                Toast.makeText(getApplicationContext(), "Please do login", Toast.LENGTH_SHORT).show();
            }else {


                Intent c=new Intent(getApplicationContext(),AddToCartActivity.class);
                c.putExtra("custid",custid);//transfer of data through intent
                startActivity(c);

            }


        }
        return super.onOptionsItemSelected(item);


    }

//addto cart otems api fetch

    private void addToCart() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/add_product_to_cart.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");

                    Log.d("msage",msg);
                    Toast.makeText(DetailProduct.this, msg, Toast.LENGTH_SHORT).show();
                    if(msg.equals("Login Success")) {

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
                parms.put("pro_id",prodid);
                parms.put("ps_id",psid);
                parms.put("cust_id",custid);
                parms.put("pro_qty",val.getText().toString());
                parms.put("cart_pro_status","1");

                return parms;
            }
        };

        queue.add(request);

    }


    private void getProdSize() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://comzent.in/paramdash/apis/farmers/get_product_size.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("proinfomessage");

                    Log.d("msage",msg);

                    JSONArray jsonArray=jsonObject.getJSONArray("proinfo");
                        for(int i=0;i<= jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String pid = jsonObject1.getString("pro_id");
                            String mrp = jsonObject1.getString("ps_mrp_price");
                            String sell = jsonObject1.getString("ps_sell_price");
                            String disc = jsonObject1.getString("ps_discount");
                            String psize = jsonObject1.getString("ps_size");
                            Log.d("psize", psize);

                            ProdSizeModel prodmodel = new ProdSizeModel(pid,mrp,sell,disc,psize);
                            list.add(prodmodel);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                adapter=new ProdSizeListAdapter(getApplicationContext(),list,DetailProduct.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error.getMessage()", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("pro_id",prodid);

                return parms;
            }
        };

        queue.add(request);

    }


    @Override
    public void onItemClick(int position) {

        P_price.setText("\u20B9"+list.get(position).getSell()+"/-");

        SpannableString spannableString=new SpannableString("\u20B9"+list.get(position).getMrp()+"/-");
        Log.d("snap",spannableString.toString());
        StrikethroughSpan strikethroughSpan=new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mrp_price.setText(spannableString+"/-");
        discount.setText("%"+list.get(position).getDisc()+" Off");
        kgunit.setText(list.get(position).getPrsize());

        Toast.makeText(this, list.get(position).getPrsize(), Toast.LENGTH_SHORT).show();
    }
}
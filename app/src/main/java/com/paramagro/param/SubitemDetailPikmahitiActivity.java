package com.paramagro.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.param.R;

public class SubitemDetailPikmahitiActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView title,desc,timedate;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subitem_detail_pikmahiti);


        title = findViewById(R.id.item_title);
        desc = findViewById(R.id.item_desc);
        img=findViewById(R.id.item_img);
        timedate=findViewById(R.id.item_date);


        //toolbar with back button

        toolbar=findViewById(R.id.toolbar_sub_items);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String item_id=getIntent().getExtras().getString("id");

        title.setText(getIntent().getExtras().getString("title"));
        desc.setText(getIntent().getExtras().getString("desc"));
        timedate.setText(getIntent().getExtras().getString("date"));



        Glide.with(getApplicationContext())
                .load(getIntent().getExtras().getString("img"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(img);
    }

    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
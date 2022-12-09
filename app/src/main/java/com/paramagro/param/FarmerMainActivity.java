package com.paramagro.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paramagro.param.Fragments.AgridukanFragment;
import com.paramagro.param.Fragments.PikVyvsthapanFragment;
import com.paramagro.param.Fragments.PikmahitiFragment;
import com.paramagro.param.Fragments.ScanFragment;
import com.example.param.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class FarmerMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    DrawerLayout drawer;
    SharedPreferences sharedPreferences_user;
    SharedPreferences sharedPreferences;//for header name and phno
    String id_f;
    //farmer Share prefference
    //farmar deatails for header draver info ->name and ph no
    private static final String SHARED_PREF_NAME_FARMAR="mypref";
    private static final String KEY_NAME_F="FName";
    private static final String Key_CUSTID_F="custid";
    private static final String KEY_MOBILE_F="mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_main);


        NavigationView navigationView=findViewById(R.id.nav_viewfarmer);

        //fARMER share prefference details for header name and mob no
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME_FARMAR, Context.MODE_PRIVATE);


        // profile name in navigation drawer
        View headerView=navigationView.getHeaderView(0);
        TextView Name=headerView.findViewById(R.id.farmerhname);
        TextView MobNo=headerView.findViewById(R.id.farmerhphn);
        ImageView frwd=headerView.findViewById(R.id.view_profile_farmer);



        //go to profile
        frwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ProfileFarmerActivity.class);
                startActivity(intent);
            }
        });


        //setting name and phno to header textview//
        Name.setText(sharedPreferences.getString(KEY_NAME_F,"Name"));
        MobNo.setText(sharedPreferences.getString(KEY_MOBILE_F,"Mobile Number"));

        //bottom navigation

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navfarmer);
        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerfarmer,new PikmahitiFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.bottompikfarmer);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfragment=null;

                switch (item.getItemId()){
                    case R.id.bottompikfarmer:
                        selectedfragment=new PikmahitiFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerfarmer,selectedfragment).commit();

                        break;
                    case R.id.bottomvyavastapanfarmer:

                        selectedfragment=new PikVyvsthapanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerfarmer,selectedfragment).commit();

                        break ;
                    case R.id.bottomdukanfarmer:
                        selectedfragment=new AgridukanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerfarmer,selectedfragment).commit();

                        break;
                    case R.id.bottomscanfarmer:
                        selectedfragment=new ScanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerfarmer,selectedfragment).commit();

                        break;
                }

                return true;
            }
        });

        //top navigation

        toolbar = findViewById(R.id.toolBarfarmer);
        drawer = findViewById(R.id.drawerlayoutfarmer);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                drawer.closeDrawer(GravityCompat.START);

                Fragment selectedfragment=null;


                switch (id){
//                    case R.id.sideprofile:
//
////                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_LONG).show();
////                        selectedfragment=new Dashboard4Fragment();
////                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
//                        selectedfragment=new ScanFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
//
//                        break;

                    case R.id.sidereffarmer:
//                        selectedfragment=new Dashboard7Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        Intent i=new Intent(getApplicationContext(),ReferAndEarn.class);
                        startActivity(i);
                        break;

                    case R.id.sideorderfarmer:
//                        selectedfragment=new Dashboard7Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        Intent j=new Intent(getApplicationContext(),ActivityAllOrders.class);
                        startActivity(j);

                    case R.id.sidepikmahitifarmer:
//                        Toast.makeText(MainActivity.this, "My Address", Toast.LENGTH_LONG).show();
//                        selectedfragment=new Dashboard8Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        selectedfragment=new PikmahitiFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerfarmer,selectedfragment).commit();

                        break;
                    case R.id.sidevyavastapanfarmer:

                        selectedfragment=new PikVyvsthapanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerfarmer,selectedfragment).commit();
                        break ;

                    case R.id.sideagrodukanfarmer:
//                        Toast.makeText(MainActivity.this, "Saved For Later", Toast.LENGTH_LONG).show();
//                        selectedfragment=new Dashboard8Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        selectedfragment=new AgridukanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerfarmer,selectedfragment).commit();

                        break;

                    case R.id.sideagrohelpfarmer:
//                        selectedfragment=new Dashboard7Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        Intent k=new Intent(getApplicationContext(),ActivityHelp.class);
                        startActivity(k);
                        break;

                    case R.id.sidelogoutfarmer:
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        break;

                    default:
                        return true;
                }
                return true;
            }
        });


        //farmer shareprefference
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME_FARMAR, Context.MODE_PRIVATE);
        id_f=sharedPreferences.getString(Key_CUSTID_F,null);

    }
    //cart menu showing
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //go to cart
        if(item.getItemId()==R.id.cart){

            if(id_f==null){

                Toast.makeText(getApplicationContext(), "Please do login", Toast.LENGTH_SHORT).show();
            }else {


                Intent c=new Intent(getApplicationContext(),AddToCartActivity.class);
                c.putExtra("custid",id_f);//transfer of data through intent
                startActivity(c);

            }


        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();

    }
}
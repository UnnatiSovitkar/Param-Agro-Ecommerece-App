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
import android.view.MenuItem;
import android.view.View;

import com.paramagro.param.Fragments.AgridukanFragment;
import com.paramagro.param.Fragments.PikVyvsthapanFragment;
import com.paramagro.param.Fragments.PikmahitiFragment;
import com.paramagro.param.Fragments.ScanFragment;
import com.example.param.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    DrawerLayout drawer;
    SharedPreferences sharedPreferences_user;
    SharedPreferences sharedPreferences;//for header name and phno

    //farmer Share prefference
    //farmar deatails for header draver info ->name and ph no
    private static final String SHARED_PREF_NAME_FARMAR="mypref";
    private static final String KEY_NAME_F="FName";
    private static final String KEY_MOBILE_F="mobile";

    //paramsathiMitra detail for checking login//
    private static final String SHARED_PREF_NAME_USER="myprefuser";
    private static final String KEY_MOBILE_USER="umobile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView=findViewById(R.id.nav_view);


        //this is for parammitra/sathi shareprefference
        sharedPreferences_user =getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);

        //fARMER share prefference details for header name and mob no
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME_FARMAR, Context.MODE_PRIVATE);


        //mob no of paramsathi/parammitra
        String mobnop=sharedPreferences_user.getString(KEY_MOBILE_USER,null);
        //mob no of farmer
        String mobnof=sharedPreferences.getString(KEY_MOBILE_F,null);


        //paramsathi mobile no//
        if((mobnop!=null)){
            Intent intent=new Intent(getApplicationContext(), ParamsathiMainActivity.class);
            startActivity(intent);

        }
        //farmer mobile no//
        if((mobnof!=null)){
            Intent intent=new Intent(getApplicationContext(), FarmerMainActivity.class);
            startActivity(intent);

        }

        //bottom navigation

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,new PikmahitiFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.bottompik);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfragment=null;

                switch (item.getItemId()){
                    case R.id.bottompik:
                        selectedfragment=new PikmahitiFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();

                        break;
                    case R.id.bottomvyavastapan:

                        selectedfragment=new PikVyvsthapanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();

                        break ;
                    case R.id.bottomdukan:
                        selectedfragment=new AgridukanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();

                        break;
                    case R.id.bottomlogin:
                        selectedfragment=new ScanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();

                        break;
                }

                return true;
            }
        });

        //top navigation

        toolbar = findViewById(R.id.toolBar);
        drawer = findViewById(R.id.drawerlayout);
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

                    case R.id.sideref:
//                        selectedfragment=new Dashboard7Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        Intent i=new Intent(getApplicationContext(),ReferAndEarn.class);
                        startActivity(i);
                        break;

//                    case R.id.sideorder:
////                        selectedfragment=new Dashboard7Fragment();
////                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
//                        Intent j=new Intent(getApplicationContext(),ActivityAllOrders.class);
//                        startActivity(j);

                    case R.id.sidepikmahiti:
//                        Toast.makeText(MainActivity.this, "My Address", Toast.LENGTH_LONG).show();
//                        selectedfragment=new Dashboard8Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        selectedfragment=new PikmahitiFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();

                        break;
                    case R.id.sidevyavastapan:

                       selectedfragment=new PikVyvsthapanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                         break ;

                    case R.id.sideagrodukan:
//                        Toast.makeText(MainActivity.this, "Saved For Later", Toast.LENGTH_LONG).show();
//                        selectedfragment=new Dashboard8Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        selectedfragment=new AgridukanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();

                        break;

                    case R.id.sideagrohelp:
//                        selectedfragment=new Dashboard7Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        Intent k=new Intent(getApplicationContext(),ActivityHelp.class);
                        startActivity(k);
                    default:
                        return true;
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
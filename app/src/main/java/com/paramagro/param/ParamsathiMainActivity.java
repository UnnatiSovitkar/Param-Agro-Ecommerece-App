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
import com.paramagro.param.Fragments.ParamsathiProfileFragment;
import com.paramagro.param.Fragments.Paramsathi_Home_Fragment;
import com.paramagro.param.Fragments.PikVyvsthapanFragment;
import com.paramagro.param.Fragments.PikmahitiFragment;
import com.example.param.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class ParamsathiMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    DrawerLayout drawer;

    SharedPreferences sharedPreferences_user;
    String id_p;

    private static final String SHARED_PREF_NAME_USER="myprefuser";
    private static final String KEY_NAME_USER="uName";
    private static final String KEY_MOBILE_USER="umobile";
    private static final String Key_USERID="uid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramsathi_main);

        sharedPreferences_user =getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);

        NavigationView navigationView=findViewById(R.id.nav_view_paramsathi);

        // profile name in navigation drawer
        View headerView=navigationView.getHeaderView(0);
        TextView Namestd=headerView.findViewById(R.id.sathimitrahname);
        TextView MobNo=headerView.findViewById(R.id.sathimitrahphn);
        ImageView frwd=headerView.findViewById(R.id.view_profile_mitra);

        frwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),ProfileSathiActivity.class);
                startActivity(intent);            }
        });

        Namestd.setText(sharedPreferences_user.getString(KEY_NAME_USER,null));
        MobNo.setText(sharedPreferences_user.getString(KEY_MOBILE_USER,null));


        //bottom navigation

        bottomNavigationView=findViewById(R.id.bottom_nav_paramsathi);
        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerparamsathi,new Paramsathi_Home_Fragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.bottomhomesathi);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfragment=null;

                switch (item.getItemId()){
                    case R.id.bottomhomesathi:
                        selectedfragment=new Paramsathi_Home_Fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerparamsathi,selectedfragment).commit();

                        break;
                    case R.id.bottomvyavastapan_sathi:
                        selectedfragment=new PikVyvsthapanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerparamsathi,selectedfragment).commit();

                         break ;
                    case R.id.bottomdukansathi:
                        selectedfragment=new AgridukanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerparamsathi,selectedfragment).commit();

                        break;
                    case R.id.bottomscansathi:
                        selectedfragment=new ParamsathiProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerparamsathi,selectedfragment).commit();

                        break;
                }

                return true;
            }
        });

        //top navigation

        toolbar = findViewById(R.id.toolBarparamsathi);
        drawer = findViewById(R.id.drawerlayoutparamsathi);
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
//                    case R.id.sideprofilesathi:
//
////                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_LONG).show();
////                        selectedfragment=new Dashboard4Fragment();
////                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
//                        selectedfragment=new ParamsathiProfileFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerparamsathi,selectedfragment).commit();
//
//                        break;

                    case R.id.siderefsathi:
//                        selectedfragment=new Dashboard7Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        Intent i=new Intent(getApplicationContext(),ReferAndEarn.class);
                        startActivity(i);
                        break;

                    case R.id.sideordersathi:
//                        selectedfragment=new Dashboard7Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        Intent j=new Intent(getApplicationContext(),ActivityAllOrderSathi.class);
                        startActivity(j);
                        break;

                    case R.id.sidepikmahitisathi:
//                        Toast.makeText(MainActivity.this, "My Address", Toast.LENGTH_LONG).show();
//                        selectedfragment=new Dashboard8Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        selectedfragment=new PikmahitiFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerparamsathi,selectedfragment).commit();

                        break;
                    case R.id.sidevyavastapan:
                         selectedfragment=new PikVyvsthapanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerparamsathi,selectedfragment).commit();

                        break;
                    case R.id.sideagrodukansathi:
//                        Toast.makeText(MainActivity.this, "Saved For Later", Toast.LENGTH_LONG).show();
//                        selectedfragment=new Dashboard8Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        selectedfragment=new AgridukanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainerparamsathi,selectedfragment).commit();

                        break;

                    case R.id.sideagrohelpsathi:
//                        selectedfragment=new Dashboard7Fragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        Intent k=new Intent(getApplicationContext(),ActivityHelp.class);
                        startActivity(k);
                        break;

                    case R.id.sidelogoutsathi:
                        SharedPreferences.Editor editor=sharedPreferences_user.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.sideregsathi:
                        i=new Intent(getApplicationContext(), RegisterSathiMitraActivity.class);
                        startActivity(i);
                        break;

                    case R.id.sidechecksathi:
                        i=new Intent(getApplicationContext(), CheckMitraSathi.class);
                        startActivity(i);

                        break;

                    default:
                        return true;
                }
                return true;
            }
        });

        //farmer shareprefference
        sharedPreferences_user =getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        id_p=sharedPreferences_user.getString(Key_USERID,null);


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
            if(id_p==null){

                Toast.makeText(getApplicationContext(), "Please do login", Toast.LENGTH_SHORT).show();
            }else {


                Intent c=new Intent(getApplicationContext(),AddToCartActivity.class);
                c.putExtra("custid",id_p);//transfer of data through intent
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
package com.paramagro.param.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paramagro.param.LoginActivity;
import com.paramagro.param.MainActivity;
import com.example.param.R;
import com.paramagro.param.RegistrationActivity;

public class ScanFragment extends Fragment {
    Button farmar,parammitra,logout;
    TextView name,phno,reg_no,u_type;
    SharedPreferences sharedPreferences,sharedPreferences_user;
    LinearLayout l1,l2;

    ///shareprefference data for users//
    private static final String Key_USER_TYPE="utype";
    private static final String SHARED_PREF_NAME_USER="myprefuser";
    private static final String KEY_NAME_USER="uName";
    private static final String Key_USERID="uid";
    private static final String KEY_MOBILE_USER="umobile";


    //shareprefference data for famers//
    private static final String KEY_MOBILE="mobile";
    private static final String Key_CUSTID_F="custid";
    private static final String KEY_NAME="FName";
    private static final String SHARED_PREF_NAME_FARMER="mypref";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.scan_fragment,container,false);

        farmar=view.findViewById(R.id.farmerlogin);
        parammitra=view.findViewById(R.id.parammitralogin);
        l1=view.findViewById(R.id.linlayout1);
        l2=view.findViewById(R.id.linlayout2);
        name=view.findViewById(R.id.profile_name);
        phno=view.findViewById(R.id.profile_mobn);
        reg_no=view.findViewById(R.id.profile_regno);
        u_type=view.findViewById(R.id.profile_type);
        logout=view.findViewById(R.id.profile_logout);

        sharedPreferences =getContext().getSharedPreferences(SHARED_PREF_NAME_FARMER, Context.MODE_PRIVATE);
        sharedPreferences_user =getContext().getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
//        if(!(sharedPreferences.equals(null))){
//
//        }
        //sharepreference for user type "2"for Paramsathi and "3"for Parammitra
        String user_type=sharedPreferences_user.getString(Key_USER_TYPE,null);

        //mob no of paramsathi/parammitra
        String mobno=sharedPreferences_user.getString(KEY_NAME_USER,null);
        //mob no of farmer
        String mobn = sharedPreferences.getString(KEY_MOBILE, null);
        //name and mobile no of farmer

        name.setText("नाव: "+sharedPreferences.getString(KEY_NAME, null));
        phno.setText("फोन नंबर: "+sharedPreferences.getString(KEY_MOBILE, null));
        reg_no.setText("Reg. No: "+sharedPreferences.getString(Key_CUSTID_F, null));
        u_type.setText("प्रकार: शेतकरी");
        //name and mobile no of parammitra /paramsathi

        if((mobn!=null)||(mobno!=null)){
            ///this is for farmar//
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.GONE);
            //if data is available then dont show login layout...

        }else {

            l1.setVisibility(View.GONE);
            l2.setVisibility(View.VISIBLE);

        }

        farmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getContext(), RegistrationActivity.class);
                startActivity(i);
            }
        });

        parammitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent j=new Intent(getContext(), LoginActivity.class);
                startActivity(j);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
//                SharedPreferences.Editor editoru=sharedPreferences_user.edit();
//                editoru.clear();
//                editoru.commit();
                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

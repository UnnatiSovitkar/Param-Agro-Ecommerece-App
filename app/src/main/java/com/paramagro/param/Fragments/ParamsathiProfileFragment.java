package com.paramagro.param.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paramagro.param.MainActivity;
import com.example.param.R;

public class ParamsathiProfileFragment extends Fragment {
    Button logout;
    TextView name,phno,reg_no,u_type;
    SharedPreferences sharedPreferences_user;


    ///shareprefference data for users//
    private static final String Key_USER_TYPE="utype";
    private static final String SHARED_PREF_NAME_USER="myprefuser";
    private static final String KEY_NAME_USER="uName";
    private static final String Key_USERID="uid";
    private static final String KEY_MOBILE_USER="umobile";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.paramsathi_profile_fragment,container,false);

        name=view.findViewById(R.id.profile_name_sathi);
        phno=view.findViewById(R.id.profile_mobn_sathi);
        reg_no=view.findViewById(R.id.profile_regno_sathi);
        u_type=view.findViewById(R.id.profile_type_sathi);
        logout=view.findViewById(R.id.profile_logout_sathi);

        sharedPreferences_user =getContext().getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);
        //sharepreference for user type "2"for Paramsathi and "3"for Parammitra
        String user_type=sharedPreferences_user.getString(Key_USER_TYPE,null);

        //mob no of paramsathi/parammitra
        String mobno=sharedPreferences_user.getString(KEY_NAME_USER,null);

         name.setText("नाव: "+sharedPreferences_user.getString(KEY_NAME_USER,null));
            phno.setText("फोन नंबर: "+sharedPreferences_user.getString(KEY_MOBILE_USER,null));
            reg_no.setText("Reg. No: "+sharedPreferences_user.getString(Key_USERID,null));
            if(user_type.equals("2")){
                u_type.setText("प्रकार: परमसाथी");

            }else if (user_type.equals("3")){
                u_type.setText("प्रकार: परममित्र");

            }



//        if(!(nameu.getText().toString()=="")){
//            Toast.makeText(getContext(), "farmer", Toast.LENGTH_SHORT).show();
//        }else if (!(name.getText().toString()=="")){
//            if(user_type.equals("2")){
//                Toast.makeText(getContext(), "Welcome Paramsathi", Toast.LENGTH_SHORT).show();
//            }else if (user_type.equals("3")){
//                Toast.makeText(getContext(), "Welcome ParamMitra", Toast.LENGTH_SHORT).show();
//            }
////            Toast.makeText(getContext(), "parammitra", Toast.LENGTH_SHORT).show();
//        }


        //for checking of already login or not if already login visibility change//

//         phno.setText(sharedPreferences_user.getString(Key_USER_TYPE, null));
//        if(user_type.equals("2")){
////            name.setText(sharedPreferences_user.getString(KEY_NAME_USER, null));
////            phno.setText(sharedPreferences_user.getString(KEY_MOBILE_USER, null));
//            Toast.makeText(getContext(), "Welcome parammitra", Toast.LENGTH_SHORT).show();
//
//        }
//        else if (user_type.equals("3")){
////            name.setText(sharedPreferences_user.getString(KEY_NAME_USER, null));
////            phno.setText(sharedPreferences_user.getString(KEY_MOBILE_USER, null));
//            Toast.makeText(getContext(), "Welcome parammitra", Toast.LENGTH_SHORT).show();
//        }else{
//
//
//        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences.Editor editoru=sharedPreferences_user.edit();
                editoru.clear();
                editoru.commit();
               Intent intent=new Intent(getContext(), MainActivity.class);
               startActivity(intent);
            }
        });
        return view;
    }
}

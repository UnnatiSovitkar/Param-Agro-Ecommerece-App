package com.paramagro.param.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.paramagro.param.ActivityHelp;
import com.paramagro.param.CheckMitraSathi;
import com.example.param.R;
import com.paramagro.param.RegisterSathiMitraActivity;

public class Paramsathi_Home_Fragment extends Fragment implements View.OnClickListener {

    ViewFlipper flipper;
    CardView reg,check,whatsapp,help;
    TextView reg_type,check_type;
    SharedPreferences sharedPreferences_user;

    private static final String Key_USER_TYPE="utype";
    private static final String SHARED_PREF_NAME_USER="myprefuser";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.paramsathi_home_fragment, container, false);
        //auto sliding images
        flipper=view.findViewById(R.id.flipper);
        reg=view.findViewById(R.id.regparam);
        check=view.findViewById(R.id.check);
        help=view.findViewById(R.id.help);
        reg_type=view.findViewById(R.id.t1);
        check_type=view.findViewById(R.id.t2);


        reg.setOnClickListener(this);
        check.setOnClickListener(this);
        help.setOnClickListener(this);

        sharedPreferences_user =getContext().getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);

        String utype=sharedPreferences_user.getString(Key_USER_TYPE,null);
//        Toast.makeText(getContext(), utype, Toast.LENGTH_SHORT).show();

        if(utype.equals("2")){//paramsathi
//            Toast.makeText(getContext(), "paramsathi", Toast.LENGTH_SHORT).show();
            reg_type.setText("Register ParamMitra");
            check_type.setText("Check ParamMitra");
        }else if(utype.equals("3")){//parammitra

//            Toast.makeText(getContext(), "paramMitra", Toast.LENGTH_SHORT).show();
            reg_type.setText("Register Farmer");
            check_type.setText("Check Farmer");

        }

        int imgarray[]={R.drawable.plant,R.drawable.agriculture,R.drawable.farming};

        for(int i=0;i<imgarray.length;i++)
            showimage(imgarray[i]);

        //sliding image end//
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

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.regparam:

                i=new Intent(getContext(), RegisterSathiMitraActivity.class);
                startActivity(i);
                break;

            case R.id.check:
                i=new Intent(getContext(), CheckMitraSathi.class);
                startActivity(i);
                break;

            case R.id.help:
                i=new Intent(getContext(), ActivityHelp.class);
                startActivity(i);
                break;
        }
    }
}

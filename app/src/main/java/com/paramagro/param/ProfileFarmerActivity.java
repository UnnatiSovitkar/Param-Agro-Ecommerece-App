package com.paramagro.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.param.R;

public class ProfileFarmerActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView set_img,pick_img;
    AlertDialog.Builder builder;
    SharedPreferences sharedPreferences;
    //shareprefference data for famers//
    private static final String KEY_MOBILE="mobile";
    private static final String Key_CUSTID_F="custid";
    private static final String KEY_NAME="FName";
    private static final String SHARED_PREF_NAME_FARMAR="mypref";
    TextView txtname,txtphone,txttype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_farmer);

        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME_FARMAR, Context.MODE_PRIVATE);

        //initialization

        txtname=findViewById(R.id.namefarmer);
        txtphone=findViewById(R.id.mobfarmer);
        txttype=findViewById(R.id.sectionfarmer);
        set_img=findViewById(R.id.profileimgfarmer);
        pick_img=findViewById(R.id.upload_dp_farmer);

        txtname.setText(sharedPreferences.getString(KEY_NAME,null));
        txtphone.setText(sharedPreferences.getString(KEY_MOBILE,null));
        txttype.setText("Farmer");

        pick_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });
        //toolbar with back button

        toolbar=findViewById(R.id.toolbarprofilefarmer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);


    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(icamera, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                    }
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "select picture"),2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
                set_img.setImageBitmap(bitmap);
//                getFileDataFromDrawable(bitmap);

            } else if (requestCode == 2) {
                if (data.getData() != null) {

                    final Uri imageUri = data.getData();
                    try {
                        //getting bitmap object from uri
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        set_img.setImageBitmap(bitmap);
//                        getFileDataFromDrawable(bitmap);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


}
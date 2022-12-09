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

public class ProfileSathiActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView set_img,pick_img;
    AlertDialog.Builder builder;
    TextView txtname,txtphone,txttype;
    String utype;
    SharedPreferences sharedPreferences_user;

    //shareprefference of parammitra registration
    private static final String SHARED_PREF_NAME_USER="myprefuser";
    private static final String KEY_NAME_USER="uName";
    private static final String KEY_MOBILE_USER="umobile";
    private static final String Key_USER_TYPE="utype";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_sathi);

        sharedPreferences_user =getSharedPreferences(SHARED_PREF_NAME_USER, Context.MODE_PRIVATE);

        //initialization

        txtname=findViewById(R.id.namesathi);
        txtphone=findViewById(R.id.mobsathi);
        txttype=findViewById(R.id.sectionsathi);
        set_img=findViewById(R.id.profileimgsathi);
        pick_img=findViewById(R.id.upload_dp);

        txtname.setText(sharedPreferences_user.getString(KEY_NAME_USER,null));
        txtphone.setText(sharedPreferences_user.getString(KEY_MOBILE_USER,null));


        utype=sharedPreferences_user.getString(Key_USER_TYPE,null);
        if(utype.equals("2")){
            txttype.setText("Paramsathi");
        }else if(utype.equals("3")){
            txttype.setText("Parammitra");
        }
        pick_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });

        //toolbar with back button
        toolbar=findViewById(R.id.toolbarprofilesathi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}




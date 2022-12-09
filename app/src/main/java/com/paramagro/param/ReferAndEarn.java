package com.paramagro.param;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.paramagro.RecyclerAdapter.ContactListAdapter;
import com.paramagro.RecyclerModelClass.ContactListModel;
import com.example.param.R;

import java.util.ArrayList;
import java.util.List;


public class ReferAndEarn extends AppCompatActivity{
    Toolbar toolbar;
    private static final int RESULT_PICK_CONTACT=1;
    Cursor cursor;
    ListView listView;
    Button button;
    Intent data2;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private ListView lstNames;

    ContactListAdapter adapter;
    List<ContactListModel> list=new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_earn);


        recyclerView=findViewById(R.id.recyclerinvite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new ContactListAdapter(this, list);
        recyclerView.setAdapter(adapter);



        // set OnClickListener() to the button

                // calling of getContacts()
                showContacts();

//
//        Intent in=new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
//        startActivityForResult(in,RESULT_PICK_CONTACT);

        //toolbar with back button

        toolbar=findViewById(R.id.toolbarfeedback);
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
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {


            getContactNames();

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
/*
    private List<String> getContactNames() {

        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                int con_name=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cursor.getString(con_name);
                int phoneIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phono=cursor.getString(phoneIndex);
                ContactListModel listModel = new ContactListModel(name, phono);
                list.add(listModel);
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

        return contacts;
    }


 */
    private void getContactNames() {

        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                int con_name=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(con_name);
                int phoneIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                String phono=cursor.getString(phoneIndex);
                ContactListModel listModel = new ContactListModel(name, phono);
                list.add(listModel);
                adapter=new ContactListAdapter(getApplicationContext(),list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                contacts.add(name);


            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

    }/*
    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Failed to pick contact", Toast.LENGTH_SHORT).show();
        }
    }
    private void contactPicked(Intent data){
        Cursor cursor=null;
        try {
            String phono=null;
            String contactname=null;
            Uri uri=data.getData();
            cursor=getContentResolver().query(uri,null,null,null,null);
            cursor.moveToFirst();
            int phoneIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int conname=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phono=cursor.getString(phoneIndex);
            contactname=cursor.getString(conname);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


 */
}
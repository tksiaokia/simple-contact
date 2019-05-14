package com.sean.simplecontact.activitys;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sean.simplecontact.R;
import com.sean.simplecontact.adapters.ContactAdapter;
import com.sean.simplecontact.databinding.ActivityMainBinding;
import com.sean.simplecontact.models.Contact;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    ContactAdapter adapter;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        setupAdapter();
        binding.toolbar.setTitle("Contacts");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add){
            simpleToast("Add Clicked");
            return true;
        }else if(item.getItemId() == R.id.action_search){
            simpleToast("Search Clicked");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupAdapter() {
        adapter = new ContactAdapter(this,new ContactAdapter.ContactAdapterCallback() {
            @Override
            public void onItemClicked(Contact contact) {

            }

        });
        binding.recycleView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.recycleView.setLayoutManager(mLayoutManager);

        //pump data
        adapter.setContacts(getContactList());
    }
    private ArrayList<Contact> getContactList(){
        String jsonString = getContactJsonString();
        Type type = TypeToken.getParameterized(ArrayList.class, Contact.class).getType();
        return new Gson().fromJson(jsonString,type);
    }

    private String getContactJsonString() {
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        return new Scanner(inputStream).useDelimiter("\\A").next();
    }
    //simple toast
    private void simpleToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}

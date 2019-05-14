package com.sean.simplecontact.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class MainActivity extends BaseActivity {

    ContactAdapter adapter;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        setupAdapter();
        binding.toolbar.setTitle("Contacts");
        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateAdapterWithJsonData();
                binding.swipeContainer.setRefreshing(false);
            }
        });

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
        adapter = new ContactAdapter(this, new ContactAdapter.ContactAdapterCallback() {
            @Override
            public void onItemClicked(Contact contact) {
                navigateToDetailScreen(contact);
            }

        });
        binding.recycleView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.recycleView.setLayoutManager(mLayoutManager);

        //pump data
        updateAdapterWithJsonData();
    }
    private void updateAdapterWithJsonData(){
            adapter.setContacts(getContactList());
    }
    private void navigateToDetailScreen(Contact contact){
        Intent intent = new Intent(getBaseContext(), ContactDetailActivity.class);
        intent.putExtra(ContactDetailActivity.CONTACT_DATA, new Gson().toJson(contact));
        startActivityForResult(intent,1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && requestCode == ContactDetailActivity.UPDATED_CONTACT_RESULT_CODE){
            if(data != null){
                String json = data.getStringExtra(ContactDetailActivity.CONTACT_DATA);
                Contact contact = new Gson().fromJson(json,Contact.class);
                adapter.updateContactList(contact);
            }
        }
    }


}

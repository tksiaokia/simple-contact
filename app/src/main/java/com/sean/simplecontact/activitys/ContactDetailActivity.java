package com.sean.simplecontact.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sean.simplecontact.R;
import com.sean.simplecontact.databinding.ActivityDetailBinding;
import com.sean.simplecontact.databinding.ActivityMainBinding;
import com.sean.simplecontact.models.Contact;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactDetailActivity extends BaseActivity {
    public static String CONTACT_DATA = "CONTACT_DATA";
    public static int UPDATED_CONTACT_RESULT_CODE = 1;
    ActivityDetailBinding binding;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("Contact Detail");

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //intent data
        contact = getContactFromIntent();

        if (contact != null) {//it shall not null
            bindUI(contact);
        }
    }

    private Contact getContactFromIntent() {
        String json = getIntent().getStringExtra(CONTACT_DATA);
        if (json != null && !json.isEmpty()) {
            return new Gson().fromJson(json, Contact.class);
        }
        return null;
    }

    private void bindUI(Contact contact) {
        binding.txtFirstName.setText(contact.getFirstName());
        binding.txtLastName.setText(contact.getLastName());
        binding.txtEmail.setText(contact.getEmail());
        binding.txtPhone.setText(contact.getPhone());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        } else if (item.getItemId() == R.id.action_save) {
            onSaveContact();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSaveContact() {
        //validation
        String firstName = binding.txtFirstName.getText().toString();
        String lastName = binding.txtLastName.getText().toString();
        String email = binding.txtEmail.getText().toString();
        String phone = binding.txtPhone.getText().toString();

        if (firstName.isEmpty()) {
            simpleToast("First Name can't be empty");
            return;
        }

        if (lastName.isEmpty()) {
            simpleToast("Last Name can't be empty");
            return;
        }

        //can apply checking on email and phone for format too

        //update object
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(email);
        contact.setPhone(phone);

        //set result and finish
        Intent intent = new Intent();
        intent.putExtra(CONTACT_DATA, new Gson().toJson(contact));
        setResult(UPDATED_CONTACT_RESULT_CODE, intent);
        finish();

    }
}

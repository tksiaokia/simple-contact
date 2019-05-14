package com.sean.simplecontact.activitys;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    ProgressDialog dialog;

    //simple toast
    public void simpleToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}
package com.auth0.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    public void Buyer(View view) {
        Intent i =new Intent(this,AuthActivity.class);
        i.putExtra("UserType","Dealer");
        startActivity(i);
        finish();
    }

    public void Seller(View view) {
        Intent i =new Intent(this,AuthActivity.class);
        i.putExtra("UserType","User");
        startActivity(i);
        finish();
    }
}
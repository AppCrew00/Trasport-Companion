package com.auth0.sample.seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.auth0.sample.R;
import com.auth0.sample.classes.SellerInfo;
import com.auth0.sample.databinding.ActivitySellerItemSelectorBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SellerItemSelector extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> items = new ArrayList<>();
    private ActivitySellerItemSelectorBinding binding;
    private SellerInfo sellerInfo;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerItemSelectorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sellerInfo = (SellerInfo) getIntent().getSerializableExtra("info");

        binding.baloo.setOnClickListener(this::onClick);
        binding.sariya.setOnClickListener(this::onClick);
        binding.maurang.setOnClickListener(this::onClick);
        binding.cement.setOnClickListener(this::onClick);
        binding.done.setOnClickListener(this::onClick);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Seller");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.baloo:

                if (binding.baloo.isChecked()) {
                    items.add("baloo");
                } else {
                    items.remove("baloo");
                }
                break;

            case R.id.sariya:

                if (binding.sariya.isChecked()) {
                    items.add("sariya");
                } else {
                    items.remove("sariya");
                }
                break;

            case R.id.maurang:

                if (binding.maurang.isChecked()) {
                    items.add("maurang");
                } else {
                    items.remove("maurang");
                }
                break;

            case R.id.cement:

                if (binding.cement.isChecked()) {
                    items.add("cement");
                } else {
                    items.remove("cement");
                }

                break;

            case R.id.done:

                sellerInfo.setItems(items);
                databaseReference.push().setValue(sellerInfo);
                Intent intent = new Intent(SellerItemSelector.this,SellerActivity.class);
                startActivity(intent);

                break;


        }
    }
}
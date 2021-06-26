package com.auth0.sample.seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.auth0.sample.R;
import com.auth0.sample.databinding.ActivitySellerItemSelectorBinding;

import java.util.ArrayList;
import java.util.List;

public class SellerItemSelector extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> items = new ArrayList<>();
    private ActivitySellerItemSelectorBinding binding;
    private SellerInfo sellerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerItemSelectorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sellerInfo = (SellerInfo) getIntent().getSerializableExtra("item");

        binding.baloo.setOnClickListener(this::onClick);
        binding.sariya.setOnClickListener(this::onClick);
        binding.maurang.setOnClickListener(this::onClick);
        binding.cement.setOnClickListener(this::onClick);
        binding.done.setOnClickListener(this::onClick);
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
                Intent intent = new Intent(SellerItemSelector.this,SellerMainActivity.class);
                startActivity(intent);

                break;


        }
    }
}
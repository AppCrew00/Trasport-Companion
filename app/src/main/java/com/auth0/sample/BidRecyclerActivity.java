package com.auth0.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.auth0.sample.classes.Bid;
import com.auth0.sample.classes.Job;
import com.auth0.sample.databinding.BidRecyclerViewBinding;

import java.util.List;

public class BidRecyclerActivity extends AppCompatActivity {

    private List<Bid> bidList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_recycler);

        Job job = (Job) getIntent().getSerializableExtra("bid");

        bidList = job.getLst();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        BidRecyclerAdapter bidRecyclerAdapter = new BidRecyclerAdapter(this,bidList );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bidRecyclerAdapter);

    }
}
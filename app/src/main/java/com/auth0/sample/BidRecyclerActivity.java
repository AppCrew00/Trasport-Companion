package com.auth0.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.auth0.sample.classes.Bid;
import com.auth0.sample.classes.Job;
import com.auth0.sample.databinding.BidRecyclerViewBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class BidRecyclerActivity extends AppCompatActivity {

    private List<Bid> bidList;
    private String key;
    private BidRecyclerAdapter bidRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_recycler);
        key = getIntent().getStringExtra("bid");

        findTheComplainChild("");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        bidRecyclerAdapter = new BidRecyclerAdapter(this,bidList );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bidRecyclerAdapter);

    }

    private void findTheComplainChild(String id){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Jobs").child(key).child("list");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        collectMapData((Map<String,Object>) dataSnapshot.getValue(),id);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    private String collectMapData(Map<String, Object> value,String emal) {
        for (Map.Entry<String, Object> entry : value.entrySet()){
            String key=entry.getKey().toString();
            Bid singleUser = (Bid) entry.getValue();
            bidList.add(singleUser);
            update();
        }
        return "dinNotFound";
    }

    public void update ()
    {
        bidRecyclerAdapter.notifyDataSetChanged();
    }
}
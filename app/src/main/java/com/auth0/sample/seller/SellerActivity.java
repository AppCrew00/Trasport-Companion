package com.auth0.sample.seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.auth0.sample.Job;
import com.auth0.sample.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SellerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private List<Job> jobList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs");
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                Job job = snapshot.getValue(Job.class);
                jobList.add(job);
                update();

            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);


        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        viewPager = findViewById(R.id.pager);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerSeller viewPagerSeller = new ViewPagerSeller(getSupportFragmentManager(),tabLayout.getTabCount(),this,jobList);
        viewPager.setAdapter(viewPagerSeller);
    }

    public void update ()
    {
        ViewPagerSeller viewPagerSeller = new ViewPagerSeller(getSupportFragmentManager(),tabLayout.getTabCount(),this,jobList);
        viewPager.setAdapter(viewPagerSeller);
    }
}
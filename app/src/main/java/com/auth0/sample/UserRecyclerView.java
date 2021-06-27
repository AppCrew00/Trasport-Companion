package com.auth0.sample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.auth0.sample.classes.Job;
import com.auth0.sample.databinding.ActivityUserRecyclerViewBinding;
import com.auth0.sample.seller.JobAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerView extends AppCompatActivity implements View.OnClickListener,JobAdapter.clickMe {

    private ActivityUserRecyclerViewBinding binding;
    private List<Job> jobList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private SharedPreferences sharedPreferences;
    private JobAdapter jobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserRecyclerViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        jobAdapter = new JobAdapter(this , jobList,this::perform);
        binding.recyclerView.setAdapter(jobAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences("EmailVar", MODE_PRIVATE);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Jobs");
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                Job job = snapshot.getValue(Job.class);
                if ( job.getEmail().equalsIgnoreCase(sharedPreferences.getString("Email","")) )
                {
                    jobList.add(job);
                    jobAdapter.notifyDataSetChanged();
                }

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
        binding.newJob.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {

        switch ( v.getId() )
        {
            case R.id.new_job:
                Intent intent = new Intent(this , UserMainActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void perform(int a) {

        Intent intent = new Intent( this , SingleViewActivity.class );
        intent.putExtra("caller","user");
        intent.putExtra("Object",jobList.get(a));
        startActivity(intent);

    }
}
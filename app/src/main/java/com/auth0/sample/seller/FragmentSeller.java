package com.auth0.sample.seller;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auth0.sample.classes.Job;
import com.auth0.sample.R;

import java.util.List;

public class FragmentSeller extends Fragment implements JobAdapter.clickMe{

    private List<Job> jobList;
    private Context context;

    public FragmentSeller() {
        // Required empty public constructor
    }

    public FragmentSeller (Context context , List<Job> jobList)
    {
        this.context = context;
        this.jobList = jobList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_seller, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        JobAdapter jobAdapter = new JobAdapter(context,jobList,this::perform);
        recyclerView.setAdapter(jobAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    @Override
    public void perform(int a) {


    }
}
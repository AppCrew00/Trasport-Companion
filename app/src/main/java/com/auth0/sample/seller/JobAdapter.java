package com.auth0.sample.seller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auth0.sample.Job;
import com.auth0.sample.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Job> jobList;
    private clickMe clickme;


    public JobAdapter ( Context context , List<Job> jobList , clickMe clickme )
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.jobList = jobList;
        this.clickme = clickme;
    }

    @NonNull
    @NotNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recycler_view,parent,false);
        return new JobViewHolder(view , clickme);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull JobAdapter.JobViewHolder holder, int position) {

        holder.jobtitle.setText(jobList.get(position).getJob_title());
        holder.city_state.setText(jobList.get(position).getJob_title());
        holder.price.setText(jobList.get(position).getPayment());
        holder.weight.setText(jobList.get(position).getWeight());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView price , jobtitle ,weight , city_state;

        public JobViewHolder(@NonNull @NotNull View itemView , clickMe clickme) {
            super(itemView);

            price = itemView.findViewById(R.id.price);
            city_state = itemView.findViewById(R.id.location_city_state);
            weight = itemView.findViewById(R.id.weight);
            jobtitle = itemView.findViewById(R.id.job_title);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            clickme.perform(getAdapterPosition());
        }
    }

    public interface clickMe {
        void perform ( int a );
    }
}

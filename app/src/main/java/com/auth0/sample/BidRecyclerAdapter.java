package com.auth0.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auth0.sample.classes.Bid;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BidRecyclerAdapter  extends RecyclerView.Adapter<BidRecyclerAdapter.BidViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Bid> bids;


    public BidRecyclerAdapter ( Context cotext , List<Bid> bids )
    {
        this.layoutInflater = LayoutInflater.from(cotext);
        this.bids = bids;
    }


    @NonNull
    @NotNull
    @Override
    public BidViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.bid_recycler_view,parent,false);
        return new BidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BidRecyclerAdapter.BidViewHolder holder, int position) {

        if ( position%2 == 1)
            holder.linearLayout.setBackgroundResource(R.drawable.purple_box);

        holder.name.setText(bids.get(position).getName());
        holder.phone.setText(bids.get(position).getPhoneNo());
        holder.time.setText(bids.get(position).getTime());
        holder.money.setText(bids.get(position).getMoney());
    }

    @Override
    public int getItemCount() {
        return bids.size();
    }

    public class BidViewHolder extends RecyclerView.ViewHolder {

        private TextView name , phone , money , time ;
        private LinearLayout linearLayout;

        public BidViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            time = itemView.findViewById(R.id.time);
            money = itemView.findViewById(R.id.money);
            linearLayout = itemView.findViewById(R.id.linear_layout);
        }
    }
}

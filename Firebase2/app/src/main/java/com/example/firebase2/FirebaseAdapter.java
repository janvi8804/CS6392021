package com.example.firebase2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirebaseAdapter extends RecyclerView.Adapter<FirebaseAdapter.ExampleViewHolder> {
    private ArrayList<Items> mExampleList;

    public void setItem(ArrayList<Items> mList) {
        this.mExampleList =mList;
        notifyDataSetChanged();
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView firstname;
        public TextView lastname;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            firstname = itemView.findViewById(R.id.firstname);
            lastname = itemView.findViewById(R.id.lastname);
        }
    }



    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Items currentItem = mExampleList.get(position);
        holder.firstname.setText(currentItem.getFirstname());
        holder.lastname.setText(currentItem.getLastname());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}

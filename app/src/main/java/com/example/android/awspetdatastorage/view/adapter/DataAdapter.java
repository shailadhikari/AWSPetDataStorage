package com.example.android.awspetdatastorage.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.amplify.generated.graphql.ListPetsQuery;
import com.example.android.awspetdatastorage.R;

import java.util.ArrayList;
import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {

    private List<ListPetsQuery.Item> mData = new ArrayList<ListPetsQuery.Item>();
    private LayoutInflater mLayoutInflater;

    public DataAdapter(Context context){
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItems(List<ListPetsQuery.Item> items){
        mData = items;
    }
}

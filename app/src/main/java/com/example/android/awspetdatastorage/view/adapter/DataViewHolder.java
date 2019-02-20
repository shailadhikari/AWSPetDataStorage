package com.example.android.awspetdatastorage.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.amazonaws.amplify.generated.graphql.ListPetsQuery;
import com.example.android.awspetdatastorage.R;


public class DataViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView desc;

    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.txt_name);
        desc = (TextView) itemView.findViewById(R.id.txt_description);
    }

    void bindData(ListPetsQuery.Item item){
        name.setText(item.name());
        desc.setText(item.description());
    }

}

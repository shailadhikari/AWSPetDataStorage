package com.example.android.awspetdatastorage.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.amazonaws.amplify.generated.graphql.ListPetsQuery;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.android.awspetdatastorage.R;
import com.example.android.awspetdatastorage.client.ClientFactory;
import com.example.android.awspetdatastorage.view.adapter.DataAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;


public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DataAdapter mAdapter;


    private List<ListPetsQuery.Item> mPets;
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fetching the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //setting up a linear layout manager for the recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set up the adapter to the recycler view
        mAdapter = new DataAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        ClientFactory.init(this);

        FloatingActionButton btnAddPet = findViewById(R.id.btn_addPet);
        btnAddPet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent addPetIntent = new Intent(MainActivity.this, AddPetActivity.class);
                MainActivity.this.startActivity(addPetIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //query list items when we return to screen
        query();
    }

    public void query(){
        ClientFactory.appSyncClient().query(ListPetsQuery.builder().build())
                            .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                            .enqueue(queryCallback);
    }

    private GraphQLCall.Callback<ListPetsQuery.Data> queryCallback = new GraphQLCall.Callback<ListPetsQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<ListPetsQuery.Data> response) {
            mPets = new ArrayList<>(response.data().listPets().items());

            Log.i(TAG, "Retrieved list items: " + mPets.toString());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setItems(mPets);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e(TAG, e.toString());
        }
    };
}

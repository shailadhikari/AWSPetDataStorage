package com.example.android.awspetdatastorage.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.CreatePetMutation;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.android.awspetdatastorage.R;
import com.example.android.awspetdatastorage.client.ClientFactory;

import javax.annotation.Nonnull;

import type.CreatePetInput;

public class AddPetActivity extends AppCompatActivity {

    private static final String TAG= AddPetActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        Button addButton = findViewById(R.id.btn_save);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save(){
        final String name = ((EditText)findViewById(R.id.editTxt_name)).getText().toString();
        final String desc = ((EditText)findViewById(R.id.editText_description)).getText().toString();

        CreatePetInput input = CreatePetInput.builder().name(name).description(desc).build();
        CreatePetMutation petMutation = CreatePetMutation.builder().input(input).build();
        ClientFactory.appSyncClient().mutate(petMutation).enqueue(mutateCallback);
    }

    //Mutation callback
    private GraphQLCall.Callback<CreatePetMutation.Data> mutateCallback = new GraphQLCall.Callback<CreatePetMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreatePetMutation.Data> response) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AddPetActivity.this, "Added pet", Toast.LENGTH_LONG).show();
                    AddPetActivity.this.finish();
                }
            });
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("", "Failed to perform AddPetMutation", e);
            Toast.makeText(AddPetActivity.this, "Failed to add pet", Toast.LENGTH_SHORT).show();
            AddPetActivity.this.finish();
        }
    };
}

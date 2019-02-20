package com.example.android.awspetdatastorage.client;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.sigv4.CognitoUserPoolsAuthProvider;

public class ClientFactory {

    private static volatile AWSAppSyncClient client;

    public static synchronized void init(final Context ctx){
        if(client == null){
            final AWSConfiguration config = new AWSConfiguration(ctx);
            client = AWSAppSyncClient.builder().context(ctx)
                                .awsConfiguration(config)
                                .cognitoUserPoolsAuthProvider(new CognitoUserPoolsAuthProvider() {
                                    @Override
                                    public String getLatestAuthToken() {
                                        try{
                                            return AWSMobileClient.getInstance().getTokens().getIdToken().getTokenString();
                                        } catch (Exception e) {
                                            Log.e("APPSYNC_ERROR", e.getLocalizedMessage());
                                            return e.getLocalizedMessage();
                                        }
                                    }
                                }).build();
        }
    }

    public static synchronized AWSAppSyncClient appSyncClient(){
        return client;
    }

}

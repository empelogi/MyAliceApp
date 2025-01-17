package com.empelo.myaliceapp.ui.notifications;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> credentials;

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {

        credentials = new MutableLiveData<>();

        mText = new MutableLiveData<>();
        mText.setValue("This is the credentials fragment");

        AndroidNetworking.get("http://192.168.1.8:8031/credentials")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("_DATA", response.toString());

                        JSONArray textArray = null;
                        try {
                            textArray = response.getJSONArray("results");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < textArray.length(); i++) {
                            try {
                                Log.d("text", textArray.getString(i));
                                JSONObject connections = textArray.getJSONObject(i);
                                JSONObject attrs = connections.getJSONObject("attrs");

                                List<String> credList = new ArrayList<>();
                                credList.add("Schema ID : " + connections.getString("schema_id") + "\n");
                                credList.add("Credential Definition ID: "+ connections.getString("cred_def_id") + "\n");
                                credList.add("Full Name : " + attrs.getString("full_name") + "\n");
                                credList.add("Date of Birth : "  + attrs.getString("birth_date") + "\n");
                                credList.add("Identity Card Number : " + attrs.getString("id_card") + "\n");
                                credentials.setValue(credList.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getCredentials() {
        return credentials;
    }
}

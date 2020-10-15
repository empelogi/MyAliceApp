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

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> credentials;

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {

        credentials = new MutableLiveData<>();

        mText = new MutableLiveData<>();
        mText.setValue("This is credentials fragment");

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
                                credentials.setValue(textArray.getString(i));
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

package com.empelo.myaliceapp.ui.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.empelo.myaliceapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> status;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        status = new MutableLiveData<>();
        mText.setValue("This is the home page");

        AndroidNetworking.get("http://192.168.1.8:8031/status")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("_DATA", response.toString());

                        JSONObject textArray = null;
                        try {
                            textArray = response.getJSONObject("conductor");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            String state = textArray.getString("task_active");
                            if ("1".equals(state)) {
                                status.setValue("Connected");
                            } else status.setValue("Disconnected");
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    public LiveData<String> getStatus() {
        return status;
    }


}
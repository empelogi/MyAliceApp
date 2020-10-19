package com.empelo.myaliceapp.ui.proofrequests;

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
import java.util.List;

public class ProofViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> textforProof;

    public ProofViewModel() {
        mText = new MutableLiveData<>();
        textforProof = new MutableLiveData<>();
        mText.setValue("This is the proof request fragment");

        AndroidNetworking.get("http://192.168.1.8:8031/present-proof/records")
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
                            //textforProof.setValue(textArray.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < textArray.length(); i++) {
                            try {
                                Log.d("text", textArray.getString(i));
//                                JSONObject connections = textArray.getJSONObject(i);
//                                String date = connections.getString("created_at");
                                textforProof.setValue(textArray.getString(i));
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

//        textforProof.setValue("Nothing to display yet");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getProof() {
        return textforProof;
    }
}


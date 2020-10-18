package com.empelo.myaliceapp.ui.dashboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> textforInv;
    private MutableLiveData<String> textfromAPI;


    public DashboardViewModel() throws JSONException {
        mText = new MutableLiveData<>();
        mText.setValue("This is your connections page");

        textforInv = new MutableLiveData<>();
        textfromAPI = new MutableLiveData<>();

        //making a call to an API
        AndroidNetworking.get("http://192.168.1.8:8031/connections")
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
                                String state = connections.getString("state");
                                List<String> lol = new ArrayList<>();
                                if(state.equals("active")){
                                    String from = connections.getString("their_label");
                                    String did = connections.getString("their_did");
                                    lol.add("Received from: " + from);
                                    lol.add("Sender DID: " + did);
                                    textforInv.setValue(lol.toString());
                                }else if(state.equals("invitation")){
//                                    String perf = textArray.getString(i);
//                                    textfromAPI.setValue(perf);
                                    String key = connections.getString("invitation_key");
                                    String date = connections.getString("created_at");
                                    lol.add("Invitation key: "+ key);
                                    lol.add("Date: "+ date);
                                    lol.add("\n");
                                    textfromAPI.setValue(lol.toString());
                                }
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

        textforInv.setValue("Nothing to display yet");

    }


    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getInvText() {
        return textforInv;
    }

    public LiveData<String> getTextFromApi() {
        return textfromAPI;
    }

}
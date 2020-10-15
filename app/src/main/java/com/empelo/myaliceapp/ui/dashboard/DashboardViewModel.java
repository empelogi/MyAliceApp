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

public class DashboardViewModel extends ViewModel {

    private String invasion;
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
                //.addPathParameter("pageNumber", "0")
                //.addQueryParameter("limit", "3")
                //.addHeaders("token", "1234")
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        String perf = textArray.toString();
                        //setting the value to the MutableLiveData<String>
                        //so that we can print in the text view that we have on our screen
                        textfromAPI.setValue(perf);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

        textforInv.setValue("Nothing to display yet");

        //post the accepted invitation from faber
//        JSONObject obj = new JSONObject(invasion);
//        AndroidNetworking.post("http://192.168.1.8:8020/connections/receive-invitation")
//                .addJSONObjectBody(obj) // posting json
//                .setTag("test")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONArray(new JSONArrayRequestListener() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // do anything with response
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                    }
//                });
//        Log.d("_DATA", obj.toString());

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

    public void setInvation(String invation){
        this.invasion = invation;
    }
}
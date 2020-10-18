package com.empelo.myaliceapp.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.empelo.myaliceapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final TextView textView = root.findViewById(R.id.text_dashboard);
        final TextView textView1 = root.findViewById(R.id.showActive);
        final Button btnCreateInv = root.findViewById(R.id.CreateBtn);
        final TextView showPending = root.findViewById(R.id.showPending);
        final TextView acceptInv = root.findViewById(R.id.invitationObject);
        final Button btnAccept = root.findViewById(R.id.acceptBtn);
        final TextView AcceptInv = root.findViewById(R.id.acceptInvObject);

        //finding the text view in the fragment by its id
        final TextView textView2 = root.findViewById(R.id.acceptInvObject);

        btnCreateInv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                JSONObject obj = null;
                AndroidNetworking.post("http://192.168.1.8:8031/connections/create-invitation")
                        .addJSONObjectBody(obj) // posting json
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {
                                // do anything with response
                                Log.d("_LOL", response.toString());
                                System.out.println(response);
                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                            }
                        });


            }         });


        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        dashboardViewModel.getInvText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView1.setText(s);
            }
        });



        //displaying the message that is from the call to the API
        dashboardViewModel.getTextFromApi().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showPending.setText(s);
            }
        });


        btnAccept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String input = acceptInv.getText().toString();
                //dashboardViewModel.setInvation(input);
                System.out.println(input);

                JSONObject obj = null;
                try {
                    obj = new JSONObject(input);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AndroidNetworking.post("http://192.168.1.8:8031/connections/receive-invitation")
                .addJSONObjectBody(obj) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
        Log.d("_DATA", obj.toString());

            }
        });


        return root;
    }
}

package com.empelo.myaliceapp.ui.dashboard;

import android.os.Bundle;
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

import com.empelo.myaliceapp.R;

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

        //finding the text view in the fragment by its id
        final TextView textView2 = root.findViewById(R.id.acceptInvObject);

//        btnCreateInv.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Perform action on click
//
//                //displaying the message that is from the call to the API
//                dashboardViewModel.getTextFromApi().observe(getViewLifecycleOwner(), new Observer<String>() {
//                    @Override
//                    public void onChanged(String s) {
//                        textView2.setText(s);
//                    }
//                });
//
//            }         });


        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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
                dashboardViewModel.setInvation(input);
                System.out.println(input);

            }
        });


        return root;
    }
}

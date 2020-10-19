package com.empelo.myaliceapp.ui.proofrequests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.empelo.myaliceapp.R;

public class ProofFragment extends Fragment {
    private ProofViewModel proofViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        proofViewModel =
                ViewModelProviders.of(this).get(ProofViewModel.class);
        View root = inflater.inflate(R.layout.fragment_proof, container, false);
        final TextView textView = root.findViewById(R.id.text_proof);
        final TextView tvProof = root.findViewById(R.id.tvProof);
        proofViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        proofViewModel.getProof().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvProof.setText(s);
            }
        });
        return root;
    }
}

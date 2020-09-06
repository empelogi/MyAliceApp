package com.empelo.myaliceapp.ui.proofrequests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProofViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProofViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is proof request fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}


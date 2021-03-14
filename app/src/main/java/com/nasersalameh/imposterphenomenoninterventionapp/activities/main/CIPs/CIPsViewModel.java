package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.CIPs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CIPsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CIPsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is CIPs fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
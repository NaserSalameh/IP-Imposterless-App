package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.abilities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AbilitiesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AbilitiesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is abilities fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
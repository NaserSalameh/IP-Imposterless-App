package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.reflections;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReflectionsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReflectionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Reflections fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
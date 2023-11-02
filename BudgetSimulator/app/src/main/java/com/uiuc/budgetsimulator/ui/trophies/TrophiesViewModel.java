package com.uiuc.budgetsimulator.ui.trophies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrophiesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TrophiesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is trophies fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
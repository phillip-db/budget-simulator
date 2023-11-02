package com.uiuc.budgetsimulator.ui.financial_plan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FinancialPlanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FinancialPlanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is financial plan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.uiuc.budgetsimulator.ui.home;

public interface UpdateValuesListener {
    void updateHealth(int newValue);
    void updateGrade(int newValue);
    void updateMoney(int newValue);
    // Add other methods for updating other values if needed
    void updateDay();

    void updateWeek();
}


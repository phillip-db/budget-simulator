package com.uiuc.budgetsimulator.ui.home;

public interface UpdateValuesListener {
    void updateHealth(int newValue);
    void updateGrade(int newValue);
    void updateMoney(int newValue, Scenarios.Scenario.Category category);
    // Add other methods for updating other values if needed
    void updateDay();

    void updateWeek();
    void updatePerson();
    void endGame();
}


package com.uiuc.budgetsimulator.ui.reports;

import java.io.Serializable;

public class ReportData implements Serializable {
    private final int weekNumber;
    // Add other data we track for weekly reports

    public ReportData(int weekNumber)
    {
        this.weekNumber = weekNumber;
    }

    public int getWeekNumber() {
        return weekNumber;
    }
}
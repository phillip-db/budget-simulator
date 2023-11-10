package com.uiuc.budgetsimulator.ui.reports;

import java.util.ArrayList;

public class ReportsConstants {
    public static ArrayList<ReportData> getReportData()
    {
        ArrayList<ReportData> reportsList = new ArrayList<ReportData>();
        ReportData testWeek1 = new ReportData(1);
        reportsList.add(testWeek1);
        ReportData testWeek2 = new ReportData(2);
        reportsList.add(testWeek2);

        return reportsList;
    }
}

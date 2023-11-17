package com.uiuc.budgetsimulator;

import androidx.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.uiuc.budgetsimulator.ui.reports.ReportData;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Simulation {
  private String gameSimID;
  private ArrayList<ReportData> reports;

  public Simulation(@NonNull String gameSimID, ArrayList<ReportData> reports) {
    this.gameSimID = gameSimID;
    this.reports = reports;
  }

  public String getGameSimID() {
    return gameSimID;
  }

  public ArrayList<ReportData> getReports() {
    return reports;
  }

  public void addReport(ReportData report)
  {
    reports.add(report);
  }

  public static Simulation findSimByID(ArrayList<Simulation> simulations, String gameSimID) {
    for (Simulation sim : simulations) {
      if (sim.getGameSimID().equals(gameSimID)) {
        return sim;
      }
    }
    return null;
  }
}

package com.uiuc.budgetsimulator;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uiuc.budgetsimulator.ui.reports.ReportData;

public class Utils {

  public static final String REPORTS_SAVE_FILE = "saved_reports.json";

  public static <T> T fromJSON(Type typeToken, InputStream file) {
    T obj = null;
    try {
      Gson gson = new Gson();
      Reader reader = new InputStreamReader(file);
      obj = gson.fromJson(reader, typeToken);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return obj;
  }

  public static int parseTextViewInt(View view) {
    String s = ((TextView) view).getText().toString();
    if (s.charAt(s.length() - 1) == '%') {
      s = s.substring(0, s.length() - 1);
    } else if (s.charAt(0) == '$') {
      s = s.substring(1);
    }
    return Integer.parseInt(s);
  }

  public static void appendReport(String gameSimID, ReportData reportData, Context context) {
    try {
      InputStream is = context.openFileInput(Utils.REPORTS_SAVE_FILE);
      ArrayList<Simulation> simulations = Utils.fromJSON(new TypeToken<ArrayList<Simulation>>() {
      }.getType(), is);
      is.close();
      Simulation sim = Simulation.findSimByID(simulations, gameSimID);

      sim.addReport(reportData);
      Gson gson = new Gson();
      String json = gson.toJson(simulations);

      FileOutputStream fos = context.openFileOutput(REPORTS_SAVE_FILE, MODE_PRIVATE);
      fos = context.openFileOutput(REPORTS_SAVE_FILE, MODE_PRIVATE);
      fos.write(json.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
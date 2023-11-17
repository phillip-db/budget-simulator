package com.uiuc.budgetsimulator.ui.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.uiuc.budgetsimulator.MainActivity;
import com.uiuc.budgetsimulator.R;
import com.uiuc.budgetsimulator.Simulation;
import com.uiuc.budgetsimulator.Utils;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReportsFragment extends Fragment {
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_reports, container, false);
  }

  @Override
  public void onViewCreated(View view,
                            Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ArrayList<ReportData> reportsList = new ArrayList<>();
    try {
      InputStream is = getContext().openFileInput(Utils.REPORTS_SAVE_FILE);
      ArrayList<Simulation> simulations = Utils.fromJSON(new TypeToken<ArrayList<Simulation>>() {
      }.getType(), is);
      is.close();
      Simulation sim = Simulation.findSimByID(simulations, MainActivity.getGameSimId());

      if (sim != null) reportsList = sim.getReports();
    } catch (Exception e) {
      e.printStackTrace();
    }

    ReportsAdapter reportsAdapter = new ReportsAdapter(reportsList);
    RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(reportsAdapter);

    TextView emptyView = view.findViewById(R.id.empty_view);

    if (reportsList.isEmpty()) {
      recyclerView.setVisibility(View.GONE);
      emptyView.setVisibility(View.VISIBLE);
    } else {
      recyclerView.setVisibility(View.VISIBLE);
      emptyView.setVisibility(View.GONE);
    }
  }
}
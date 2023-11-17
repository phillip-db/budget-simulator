package com.uiuc.budgetsimulator.ui.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.reflect.TypeToken;
import com.uiuc.budgetsimulator.MainActivity;
import com.uiuc.budgetsimulator.R;
import com.uiuc.budgetsimulator.Simulation;
import com.uiuc.budgetsimulator.Utils;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReportsFragment extends Fragment implements View.OnClickListener {

  private RecyclerView recyclerView;
  private ArrayList<ReportData> reportsList = new ArrayList<>();
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_reports, container, false);
  }

  @Override
  public void onViewCreated(View view,
                            Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

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
    reportsAdapter.setOnClickListener((position, report) -> {
      recyclerView.setVisibility(View.GONE);
      LinearLayout ll = view.findViewById(R.id.report_summary);
      ll.setVisibility(View.VISIBLE);
    });

    recyclerView = view.findViewById(R.id.recyclerView);
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

  @Override
  public void onClick(View v) {
    switch (v.getId())
    {
      case R.id.weekText:
        recyclerView.setVisibility(View.GONE);
        LinearLayout ll = v.findViewById(R.id.report_summary);
        ll.setVisibility(View.VISIBLE);
    }
  }
}
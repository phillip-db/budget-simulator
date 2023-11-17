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
import com.uiuc.budgetsimulator.ui.financial_plan.FinancialPlanFragment;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class ReportsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ReportData> reportsList = new ArrayList<>();
    private LinearLayout ll;

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

        LinearLayout ll = view.findViewById(R.id.report_summary);

        ReportsAdapter reportsAdapter = new ReportsAdapter(reportsList);
        reportsAdapter.setOnClickListener((position, report) -> {
            recyclerView.setVisibility(View.GONE);
            TextView title = view.findViewById(R.id.reports_page_title);
            title.setText("Week #" + report.getWeekNumber());
            ll.setVisibility(View.VISIBLE);

            loadSummary(view, position, report);
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(reportsAdapter);

        TextView emptyView = view.findViewById(R.id.empty_view);

        MaterialButton cont = view.findViewById(R.id.report_continue);
        cont.setOnClickListener(v -> {
            ll.setVisibility(View.GONE);
            TextView title = view.findViewById(R.id.reports_page_title);
            title.setText(R.string.weekly_reports);
            recyclerView.setVisibility(View.VISIBLE);
        });

        if (reportsList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    private void loadSummary(View view, int position, ReportData report) {
        // Change spent/earned to be based on values in weekly report
        // Use avg health, find a way to get prev week values if they exist (use position?)
        // Generic placeholder text to replace placeholder
        ((TextView) view.findViewById(R.id.money_spent)).setText(String.format(Locale.ENGLISH, "$%d Spent", MainActivity.weekly_spending));
        ((TextView) view.findViewById(R.id.money_earned)).setText(String.format(Locale.ENGLISH, "$%d Earned", MainActivity.weekly_earnings));
        ((TextView) view.findViewById(R.id.total_savings))
                .setText(
                        String.format(Locale.ENGLISH,
                                "Total Savings: $#->$%d",
                                report.getMoney()));
        ((TextView) view.findViewById(R.id.grade_change))
                .setText(
                        String.format(Locale.ENGLISH,
                                "Grade: #->%d",
                                report.getGrade()));
        ((TextView) view.findViewById(R.id.health_change))
                .setText(
                        String.format(Locale.ENGLISH,
                                "Health: #->%d",
                                report.getHealth()));
    }
}
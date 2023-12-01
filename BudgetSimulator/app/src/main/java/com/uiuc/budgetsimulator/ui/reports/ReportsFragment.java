package com.uiuc.budgetsimulator.ui.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

    private enum GoalSavings {
        FAILED, JUST_UNDER, MET, JUST_OVER, SURPASSED;

        private static final double[] percentBracket = {0.85, 1.0, 1.15, 1.3};

        public static GoalSavings evalGoalSaving(int netSavings, int weeklyGoal) {
            double percentage = (double) netSavings / (double) weeklyGoal;
            for (int i = 0; i < percentBracket.length; i++) {
                double pb = percentBracket[i];
                if (percentage < pb) return GoalSavings.values()[i];
            }
            return GoalSavings.SURPASSED;
        }
    }

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

        if (reportsList.size() <= 1) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity()
                .getSupportFragmentManager()
                .popBackStack ("fullReport", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void loadSummary(View view, int position, ReportData report) {
        // Change spent/earned to be based on values in weekly report
        // Use avg health, find a way to get prev week values if they exist (use position?)
        // Generic placeholder text to replace placeholder
        String tipsString = "";
        int netSavings = (report.getWeeklyEarning() - report.getWeeklySpending());
        GoalSavings eval = GoalSavings.evalGoalSaving(netSavings, FinancialPlanFragment.getGoal());
        switch (eval) {
            case FAILED:
                tipsString = "Cut down unnecessary expenses and look for more income opportunities.";
                break;
            case JUST_UNDER:
                tipsString = "Consider making some minor changes to meet next week's goal.";
                break;
            case MET:
                tipsString = "Well done! You managed to meet your weekly savings goal.";
                break;
            case JUST_OVER:
                tipsString = "Keep at it and make sure to keep your other stats up!";
                break;
            case SURPASSED:
                tipsString = "Wow! You're on your way to financial mastery.";
                break;
        }
        Utils.setTextViewText(view, R.id.summary_tips, tipsString);

        Utils.setTextViewText(view, R.id.summary_feedback, String.format(Locale.ENGLISH, "$%d/$%d Saved", netSavings, FinancialPlanFragment.getGoal()));

        Utils.setTextViewText(view, R.id.money_spent, String.format(Locale.ENGLISH, "$%d\nSpent", report.getWeeklySpending()));
        Utils.setTextViewText(view, R.id.money_earned, String.format(Locale.ENGLISH, "$%d\nEarned", report.getWeeklyEarning()));

        ReportData prevReport = reportsList.get(position - 1);
        Utils.setTextViewText(view, R.id.total_savings, String.format(Locale.ENGLISH,
                "Total Savings: $%d->$%d",
                prevReport.getMoney(), report.getMoney()));
        Utils.setTextViewText(view, R.id.grade_change, String.format(Locale.ENGLISH,
                "Grade: %d->%d",
                prevReport.getGrade(), report.getGrade()));
        Utils.setTextViewText(view, R.id.health_change, String.format(Locale.ENGLISH,
                "Health: %d->%d",
                prevReport.getHealth(), report.getHealth()));

        MaterialButton fullReport = view.findViewById(R.id.full_report);
        fullReport.setOnClickListener(v -> {
            Fragment fullFragment = new ReportSummaryFragment(report);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fullFragment)
                    .addToBackStack("fullReport").commit();
        });
    }
}
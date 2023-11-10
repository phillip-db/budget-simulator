package com.uiuc.budgetsimulator.ui.reports;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uiuc.budgetsimulator.R;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Locale;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsViewHolder> {
    private ArrayList<ReportData> reportList;

    public ReportsAdapter(ArrayList<ReportData> reports) {
        this.reportList = reports;
    }

    @NonNull
    @Override
    public ReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_card, parent, false);
        return new ReportsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsViewHolder holder, int position) {
        ReportData currentReport = reportList.get(position);
        holder.weekText.setText(String.format(Locale.ENGLISH, "Week #%d", currentReport.getWeekNumber()));
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }
}

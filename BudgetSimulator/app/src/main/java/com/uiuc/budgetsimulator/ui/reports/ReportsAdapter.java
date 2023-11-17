package com.uiuc.budgetsimulator.ui.reports;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.uiuc.budgetsimulator.R;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Locale;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsViewHolder> {
    private ArrayList<ReportData> reportList;
    private OnClickListener onClickListener;

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
        holder.weekText.setOnClickListener(v -> {
            onClickListener.onClick(position, currentReport);
        });
        if (currentReport.getWeekNumber() == 0) holder.hideItem();
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public ReportData getItem(int position) {
        return reportList.get(position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, ReportData report);
    }
}

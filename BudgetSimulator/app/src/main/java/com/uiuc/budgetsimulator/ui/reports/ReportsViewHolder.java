package com.uiuc.budgetsimulator.ui.reports;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.uiuc.budgetsimulator.R;

public class ReportsViewHolder extends RecyclerView.ViewHolder {
    MaterialButton weekText;
    View view;

    public ReportsViewHolder(@NonNull View itemView) {
        super(itemView);
        weekText = itemView.findViewById(R.id.weekText);
        view = itemView;
    }
}

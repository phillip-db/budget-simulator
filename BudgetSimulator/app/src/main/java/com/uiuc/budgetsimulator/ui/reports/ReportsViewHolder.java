package com.uiuc.budgetsimulator.ui.reports;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.uiuc.budgetsimulator.R;

public class ReportsViewHolder extends RecyclerView.ViewHolder {
    MaterialButton weekText;
    MaterialButton savingsText;
    LinearLayout card;
    View view;

    public ReportsViewHolder(@NonNull View itemView) {
        super(itemView);
        weekText = itemView.findViewById(R.id.weekText);
        savingsText = itemView.findViewById(R.id.savingsText);
        card = itemView.findViewById(R.id.card_clickable);
        view = itemView;
    }

    public void hideItem() {
      view.setVisibility(View.GONE);
      view.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
    }
}

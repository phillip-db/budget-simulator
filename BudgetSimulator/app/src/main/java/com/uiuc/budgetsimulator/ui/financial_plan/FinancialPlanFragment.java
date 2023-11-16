package com.uiuc.budgetsimulator.ui.financial_plan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;

import com.uiuc.budgetsimulator.R;

public class FinancialPlanFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_financial_plan, container, false);

        // Find the views by their IDs
        TextView textFinancialPlan = root.findViewById(R.id.text_financial_plan);
        EditText jobIncomeTextBox = root.findViewById(R.id.jobIncomeTextBox);
        EditText allowanceTextBox = root.findViewById(R.id.allowanceTextBox);
        jobIncomeTextBox.setEnabled(false);
        allowanceTextBox.setEnabled(false);

        // For Fixed Expenses section
        EditText rentTextBox = root.findViewById(R.id.rentTextBox);
        rentTextBox.setEnabled(false);
        EditText groceriesTextBox = root.findViewById(R.id.groceriesTextBox);

        // For Variable Expenses section
        EditText eatingOutTextBox = root.findViewById(R.id.eatingOutTextBox);
        EditText entertainmentTextBox = root.findViewById(R.id.entertainmentTextBox);

        // Set pre-filled values (if needed)
        jobIncomeTextBox.setText("600");
        allowanceTextBox.setText("1000");
        rentTextBox.setText("500");
        groceriesTextBox.setText("200");
        eatingOutTextBox.setText("150");
        entertainmentTextBox.setText("50");

        return root;
    }
}

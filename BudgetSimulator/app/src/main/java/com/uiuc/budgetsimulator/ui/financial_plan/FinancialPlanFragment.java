package com.uiuc.budgetsimulator.ui.financial_plan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.uiuc.budgetsimulator.MainActivity;
import com.uiuc.budgetsimulator.R;

public class FinancialPlanFragment extends Fragment {

//    public static int userGoalValue;
    SharedPreferences sharedPreferences;
    EditText groceriesTextBox, eatingOutTextBox, entertainmentTextBox, goalTextBox;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_GOAL = "goal";
    private static final String KEY_GROCERIES = "groceries";

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
        groceriesTextBox = root.findViewById(R.id.groceriesTextBox);

        // For Variable Expenses section
        eatingOutTextBox = root.findViewById(R.id.eatingOutTextBox);
        entertainmentTextBox = root.findViewById(R.id.entertainmentTextBox);
        goalTextBox = root.findViewById(R.id.budgetingGoalTextBox);

        // Set pre-filled values
        jobIncomeTextBox.setText("600");
        allowanceTextBox.setText("400");
        rentTextBox.setText("500");
        groceriesTextBox.setText("200");
        eatingOutTextBox.setText("150");
        entertainmentTextBox.setText("50");

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadSavedValues();

        // Add TextWatcher to goalTextBox
        goalTextBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sharedPreferences.edit().putString(KEY_GOAL, s.toString()).apply();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // You can add code here if needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.userGoalValue = Integer.parseInt(goalTextBox.getText().toString());
            }
        });

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();

        // Save values to SharedPreferences when leaving the fragment
        saveValues();
    }

    private void saveValues() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_GOAL, goalTextBox.getText().toString());
        editor.putString(KEY_GROCERIES, groceriesTextBox.getText().toString());
        editor.apply();
    }

    private void loadSavedValues() {
        goalTextBox.setText(sharedPreferences.getString(KEY_GOAL, ""));
        groceriesTextBox.setText(sharedPreferences.getString(KEY_GROCERIES, ""));
    }
}

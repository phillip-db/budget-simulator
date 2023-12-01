package com.uiuc.budgetsimulator.ui.financial_plan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.uiuc.budgetsimulator.MainActivity;
import com.uiuc.budgetsimulator.R;

public class FinancialPlanFragment extends Fragment {

//    public static int userGoalValue;
    static SharedPreferences sharedPreferences;
    EditText groceriesTextBox, eatingOutTextBox, entertainmentTextBox, goalTextBox;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_GOAL = "goal";
    private static final String KEY_GROCERIES = "groceries";
    private static final String KEY_EATING_OUT = "eatingOut";

    private static final String KEY_ENTERTAINMENT = "entertainment";

    private static final int defaultGoal = 100;

    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_financial_plan, container, false);

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
                sharedPreferences.edit().putString(KEY_GROCERIES, s.toString()).apply();
                sharedPreferences.edit().putString(KEY_ENTERTAINMENT, s.toString()).apply();
                sharedPreferences.edit().putString(KEY_EATING_OUT, s.toString()).apply();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // You can add code here if needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.userGoalValue = Integer.parseInt(goalTextBox.getText().toString());
                if (MainActivity.financial_goal_achieved == false) {
                    Toast toast = Toast.makeText(getContext(), "Trophy Achieved: Financial Goal", Toast.LENGTH_SHORT);
                    toast.show();
                }
                MainActivity.financial_goal_achieved = true;
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.tutorial_plan == false) {
                    startTutorial(R.string.help_7);
                    MainActivity.tutorial_plan = true;
                }
            }
        },100);

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
        editor.putString(KEY_ENTERTAINMENT, entertainmentTextBox.getText().toString());
        editor.putString(KEY_EATING_OUT, eatingOutTextBox.getText().toString());
        editor.apply();
    }

    private void loadSavedValues() {
        goalTextBox.setText(sharedPreferences.getString(KEY_GOAL, ""));
        groceriesTextBox.setText(sharedPreferences.getString(KEY_GROCERIES, ""));
        eatingOutTextBox.setText(sharedPreferences.getString(KEY_EATING_OUT,""));
        entertainmentTextBox.setText(sharedPreferences.getString(KEY_ENTERTAINMENT,""));
    }

    public static int getGoal()
    {
      if (sharedPreferences == null) return defaultGoal;
      String s = sharedPreferences.getString(KEY_GOAL, null);
      if (s == null) return defaultGoal;
      return Integer.parseInt(s);
    }

    public void startTutorial(int string_help) {
        LayoutInflater inflater = getLayoutInflater();
        View popUpView = inflater.inflate(R.layout.fragment_help, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
        popupWindow.showAtLocation(this.getView(), Gravity.CENTER, 0, 0);

        TextView help_text = popUpView.findViewById(R.id.help_text);
        help_text.setText(string_help);
        MainActivity.help_page = 7;

        Button next_button = popUpView.findViewById(R.id.help_next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.help_page != 8) {
                    MainActivity.help_page++;
                    help_text.setText(MainActivity.help_pages[MainActivity.help_page]);
                } else {
                    MainActivity.tutorial_intro = true;
                    popupWindow.dismiss();
                }
            }
        });
        Button back_button = popUpView.findViewById(R.id.help_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.help_page != 7) {
                    MainActivity.help_page--;
                    help_text.setText(MainActivity.help_pages[MainActivity.help_page]);
                }
            }
        });
    }
}

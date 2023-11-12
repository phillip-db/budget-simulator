package com.uiuc.budgetsimulator.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ScenarioDialog extends DialogFragment {

    private static final String ARG_SCENARIO = "scenario";
    public static ScenarioDialog newInstance(Scenarios.Scenario scenario) {
        ScenarioDialog fragment = new ScenarioDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SCENARIO, scenario);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Scenarios.Scenario scenario = getArguments().getParcelable((ARG_SCENARIO));
        Scenarios.Scenario.Choice[] choices = scenario.choices;
        String[] choicesStrings = new String[choices.length];
        for (int i = 0; i < choices.length; i++) {
            choicesStrings[i] = choices[i].choice;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose!")
                .setSingleChoiceItems(choicesStrings, 0, (dialog, which) -> {})
                .setPositiveButton("Submit", (dialog, which) -> {} );
        return builder.create();
    }

    public static String TAG = "scenario_dialog";
}
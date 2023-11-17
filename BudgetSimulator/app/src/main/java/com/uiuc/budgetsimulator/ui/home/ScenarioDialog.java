package com.uiuc.budgetsimulator.ui.home;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.snackbar.Snackbar;

public class ScenarioDialog extends DialogFragment {

    public interface ScenarioDialogListener {
        public void onDialogPositiveClick();
    }

    public ScenarioDialogListener listener;

    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface.
        try {
            // Instantiate the NoticeDialogListener so you can send events to
            // the host.
            listener = (ScenarioDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface. Throw exception.
            throw new ClassCastException(getActivity().toString()
                    + " must implement ScenarioDialogListener");
        }
    }
    public boolean lastScenario = false;

    public void setLastScenario(boolean lastScenario) {
        this.lastScenario = lastScenario;
    }

    private int selectedChoiceIndex = 0;

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
        UpdateValuesListener updateValuesListener;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (getActivity() instanceof UpdateValuesListener) {
            updateValuesListener = (UpdateValuesListener) getActivity();
        } else {
            updateValuesListener = null;
        }
        builder.setTitle(scenario.event)
                .setSingleChoiceItems(choicesStrings, 0, (dialog, which) -> {
                    selectedChoiceIndex = which;
                })
                .setPositiveButton("Submit", (dialog, which) -> {
                    if (updateValuesListener != null) {
                        Scenarios.Scenario.Choice selectedChoice = choices[selectedChoiceIndex];
                        updateValuesListener.updateHealth(selectedChoice.healthOutcome);
                        updateValuesListener.updateGrade(selectedChoice.gradeOutcome);
                        updateValuesListener.updateMoney(selectedChoice.moneyOutcome);
                        if (lastScenario == true) {
                            updateValuesListener.updateDay();
                            listener.onDialogPositiveClick();
                        }
                        String message = "";
                        if(selectedChoice.healthOutcome != 0) {
                            message = message + "health";
                            if (selectedChoice.healthOutcome > 0) {
                                message = message + "+" + selectedChoice.healthOutcome + " ";
                            } else {
                                message = message + selectedChoice.healthOutcome + " ";
                            }
                        }
                        if(selectedChoice.gradeOutcome != 0) {
                            message = message + "grade";
                            if (selectedChoice.gradeOutcome > 0) {
                                message = message + "+" + selectedChoice.gradeOutcome + " ";
                            } else {
                                message = message + selectedChoice.gradeOutcome + " ";
                            }
                        }
                        if(selectedChoice.moneyOutcome != 0) {
                            message = message + "money";
                            if (selectedChoice.moneyOutcome > 0) {
                                message = message + "+" + selectedChoice.moneyOutcome + " ";
                            } else {
                                message = message + selectedChoice.moneyOutcome + " ";
                            }
                        }
                        showChange(message);
                    }
                } );
        AlertDialog toReturn = builder.create();
        toReturn.getWindow().setDimAmount(0);
        return toReturn;
    }
    private void showChange(String message) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }


    public static String TAG = "scenario_dialog";
}
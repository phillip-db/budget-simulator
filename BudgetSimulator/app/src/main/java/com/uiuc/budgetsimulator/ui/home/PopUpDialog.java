package com.uiuc.budgetsimulator.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.uiuc.budgetsimulator.R;

public class PopUpDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] choices = {"Choice", "choice2", "chocie 3"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose!")
                .setSingleChoiceItems(choices, 0, (dialog, which) -> {})
                .setPositiveButton("Submit", (dialog, which) -> {} );
        return builder.create();
    }

    public static String TAG = "PopUpDialog";
}
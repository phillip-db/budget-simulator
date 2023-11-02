package com.uiuc.budgetsimulator.ui.financial_plan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.uiuc.budgetsimulator.R;

public class FinancialPlanFragment extends Fragment {

    private FinancialPlanViewModel financialPlanViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        financialPlanViewModel =
                new ViewModelProvider(this).get(FinancialPlanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_financial_plan, container, false);
        final TextView textView = root.findViewById(R.id.text_financial_plan);
        financialPlanViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
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
import android.widget.EditText;
import android.widget.TextView;
import com.uiuc.budgetsimulator.R;


public class FinancialPlanFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_financial_plan, container, false);

        // Find the views by their IDs
        TextView textFinancialPlan = root.findViewById(R.id.text_financial_plan);
        EditText editText1 = root.findViewById(R.id.editText1);
        EditText editText2 = root.findViewById(R.id.editText2);
//        EditText editText3 = root.findViewById(R.id.editText3);
//        EditText editText4 = root.findViewById(R.id.editText4);
//        EditText editText5 = root.findViewById(R.id.editText5);

        // Set pre-filled values (if needed)
        editText1.setText("123.45");
        editText2.setText("678.90");
//        editText3.setText("45.67");
//        editText4.setText("890.12");
//        editText5.setText("123.45");

        return root;
    }
}

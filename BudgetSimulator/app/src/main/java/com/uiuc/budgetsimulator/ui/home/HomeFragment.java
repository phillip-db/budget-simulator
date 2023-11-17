package com.uiuc.budgetsimulator.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.uiuc.budgetsimulator.R;
import com.uiuc.budgetsimulator.Utils;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HomeFragment extends Fragment implements ScenarioDialog.ScenarioDialogListener {

    private HomeViewModel homeViewModel;
    private Button button;

    //public Set<Integer> selectedScenarios = new HashSet<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        button = root.findViewById(R.id.start_day_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.GONE);
                startScenarios();
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    public void startScenarios() {
        Random random = new Random();
        //do random number of scenarios from 3 to 5
        InputStream inputStream =  getResources().openRawResource(R.raw.scenarios);
        Scenarios sunday = Utils.fromJSON(Scenarios.class, inputStream);
        int randomNumScenarios = random.nextInt(3) + 3;
        int numScenarios = sunday.scenarios.length;
        int countScenarios = 0;
        for (int i = 0; i < randomNumScenarios; i++) {
            Set<Integer> selectedScenarios = new HashSet<>();
            int randomScenario;
            do {
                randomScenario = random.nextInt(numScenarios);
                //countScenarios++;
            } while (selectedScenarios.contains(randomScenario)/* && countScenarios < numScenarios*/);
            selectedScenarios.add(randomScenario);
            if (i == 0) {
                openDialog(sunday.scenarios[randomScenario], true);
            } else {
                openDialog(sunday.scenarios[randomScenario], false);
            }
        }
    }
    public void openDialog(Scenarios.Scenario scenario, boolean lastScenario) {
        ScenarioDialog scenarioDialog = ScenarioDialog.newInstance(scenario);
        scenarioDialog.setTargetFragment(this, 0);
        scenarioDialog.setCancelable(false);
        scenarioDialog.setLastScenario(lastScenario);
        scenarioDialog.show(this.getParentFragmentManager(), "scenario_dialog");
    }


    @Override
    public void onDialogPositiveClick() {
        button.setVisibility(View.VISIBLE);
    }
}
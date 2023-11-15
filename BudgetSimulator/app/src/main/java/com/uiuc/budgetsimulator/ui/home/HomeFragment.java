package com.uiuc.budgetsimulator.ui.home;

import static com.uiuc.budgetsimulator.ui.home.Scenarios.readScenariosFromFile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.uiuc.budgetsimulator.R;

import java.io.InputStream;
import java.util.Random;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button button;

    private String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private int day_id = 0;
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
                //move day forward? INDICATE IT
                //
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
        Scenarios sunday = readScenariosFromFile(inputStream);
        int randomNumber = random.nextInt(sunday.scenarios.length - 2) + 3;
        for (int i = 0; i < randomNumber; i++) {
            openDialog(sunday.scenarios[i]);
            //some sort of way to acquire the dialog choice
        }
    }
    public void openDialog(Scenarios.Scenario scenario) {
        ScenarioDialog scenarioDialog = ScenarioDialog.newInstance(scenario);
        scenarioDialog.setTargetFragment(this, 0);
        scenarioDialog.setCancelable(false);
        scenarioDialog.setCompleted(true);
        //if last scenario is completed, show button and also updateDay
        scenarioDialog.show(this.getParentFragmentManager(), "scenario_dialog");
    }


}
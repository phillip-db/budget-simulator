package com.uiuc.budgetsimulator.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.uiuc.budgetsimulator.MainActivity;
import com.uiuc.budgetsimulator.R;
import com.uiuc.budgetsimulator.Utils;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HomeFragment extends Fragment implements ScenarioDialog.ScenarioDialogListener {

    private HomeViewModel homeViewModel;
    private Button button;
    View root;

    //public Set<Integer> selectedScenarios = new HashSet<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.tutorial_intro == false)
                    startTutorial(R.string.help_0);
            }
        },100);

        return root;
    }

    public void startScenarios() {
        if (MainActivity.tutorial_popup == false)
            popUpTutorial();

        Random random = new Random();
        //do random number of scenarios from 3 to 5
        InputStream inputStream =  getResources().openRawResource(R.raw.scenarios);
        Scenarios sunday = Utils.fromJSON(Scenarios.class, inputStream);
        int randomNumScenarios = random.nextInt(3) + 3;
//        randomNumScenarios = 1;
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

    // TUTORIAL DAY:
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
        MainActivity.help_page = 0;
        Button back_button = popUpView.findViewById(R.id.help_back_button);

        Button next_button = popUpView.findViewById(R.id.help_next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_button.setVisibility(View.VISIBLE);
                if (MainActivity.help_page == 2) {
                    next_button.setText("GOT IT");
                }
                if (MainActivity.help_page != 3) {
                    MainActivity.help_page++;
                    help_text.setText(MainActivity.help_pages[MainActivity.help_page]);
                } else {
                    MainActivity.tutorial_intro = true;
                    popupWindow.dismiss();
                }
            }
        });
        back_button.setVisibility(View.INVISIBLE);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next_button.setText("Next");
                if (MainActivity.help_page != 0) {
                    MainActivity.help_page--;
                    help_text.setText(MainActivity.help_pages[MainActivity.help_page]);
                }
                if (MainActivity.help_page == 0){
                    back_button.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void popUpTutorial() {
        LayoutInflater inflater = getLayoutInflater();
        View popUpView = inflater.inflate(R.layout.fragment_help, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
        popupWindow.showAtLocation(this.getView(), Gravity.CENTER, 0, 0);

        TextView help_text = popUpView.findViewById(R.id.help_text);
        help_text.setText(R.string.help_4);

        Button next_button = popUpView.findViewById(R.id.help_next_button);
        next_button.setText("Got it");
        Button back_button = popUpView.findViewById(R.id.help_back_button);
        back_button.setVisibility(View.INVISIBLE);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.tutorial_popup = true;
                popupWindow.dismiss();
            }
        });
    }

}
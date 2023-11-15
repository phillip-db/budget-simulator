package com.uiuc.budgetsimulator;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uiuc.budgetsimulator.ui.home.UpdateValuesListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements UpdateValuesListener {
    private String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private int day_id = 0;

    private String[] weeks = {"Week 1", "Week 2", "Week 3", "Week 4", "Week 5", "Week 6", "Week 7"};
    private int week_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_financial_plans, R.id.navigation_weekly_reports, R.id.navigation_trophies, R.id.navigation_help)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }

    public static String adjustFactors(TextView textView, int adjustment) {
        String s = (String)textView.getText();
        if (s.charAt(s.length() - 1) == '%') {
            int newFactor = Math.min(100, adjustment + Integer.parseInt(s.substring(0, s.length() - 1)));
            return newFactor + "%";
        } else {
            return "$" + (adjustment + Integer.parseInt(s.substring(1)));
        }
    }

    @Override
    public void updateHealth(int newValue) {
        TextView healthTextView = findViewById(R.id.health);
        healthTextView.setText(adjustFactors(healthTextView, newValue));
    }

    @Override
    public void updateGrade(int newValue) {
        TextView gradeTextView = findViewById(R.id.grade);
        gradeTextView.setText(adjustFactors(gradeTextView, newValue));
    }

    @Override
    public void updateMoney(int newValue) {
        TextView moneyTextView = findViewById(R.id.money);
        moneyTextView.setText(adjustFactors(moneyTextView, newValue));
    }

    @Override
    public void updateDay() {
        TextView textview = findViewById(R.id.day_of_week);
        if (day_id == 6) {
            day_id = 0;
            updateWeek();
        } else {
            day_id += 1;
        }
        textview.setText(days[day_id]);
    }

    @Override
    public void updateWeek() {
        TextView textview = findViewById(R.id.week);
        week_id += 1;
        textview.setText(weeks[week_id]);
    }
}
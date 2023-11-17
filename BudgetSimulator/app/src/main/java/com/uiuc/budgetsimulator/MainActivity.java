package com.uiuc.budgetsimulator;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.uiuc.budgetsimulator.ui.home.UpdateValuesListener;
import com.uiuc.budgetsimulator.ui.reports.ReportData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UpdateValuesListener {
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
        private static final Day[] vals = values();
        private static final String[] days = {"Sun", "Mon", "Tue", "Wed", "Thurs", "Fri", "Sat"};

        public Day next() {
            return vals[(this.ordinal() + 1) % vals.length];
        }

        public String getDayString()
        {
            return days[this.ordinal()];
        }
    }
    private Day day_id = Day.SUNDAY;

    private static String gameSimId;

    private final String[] weeks = {"Week 1", "Week 2", "Week 3"};
    public static int week_id = 0;

    public static boolean game_end = false;

    public static int health_val = 100;
    public static int grade_val = 100;
    public static int weekly_earnings = 0;
    public static int weekly_spending = 0;

    public static int userGoalValue;

    // TROPHIES
    public static boolean streak_achieved = false;
    public static boolean saver_achieved = false;
    public static boolean scraping_achieved = false;
    public static boolean studious_achieved = false;
    public static boolean happy_healthy_achieved = false;
    public static boolean financial_goal_achieved = false;

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

        // Figure out some way to change this later when switching simulations/starting new simulation
        gameSimId = "test_sim";


        // Create week 0 for testing
        ReportData test_week = new ReportData(0, 100, 100, 1000);
        ArrayList<ReportData> reports = new ArrayList<ReportData>();
        reports.add(test_week);
        Simulation testSim = new Simulation(gameSimId, reports);
        ArrayList<Simulation> sims = new ArrayList<Simulation>();
        sims.add(testSim);

        Gson gson = new Gson();
        String jsonString = gson.toJson(sims);
        System.out.println(sims);

        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(Utils.REPORTS_SAVE_FILE, MODE_PRIVATE);
            fos.write(jsonString.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String adjustFactors(TextView textView, int adjustment) {
        String s = (String)textView.getText();
        if (s.charAt(s.length() - 1) == '%') {
            int newFactor = Math.min(100, adjustment + Utils.parseTextViewInt(textView));
            if (newFactor < 0) {
                newFactor = 0;
            }
            return newFactor + "%";
        } else {
            return "$" + (adjustment + Utils.parseTextViewInt(textView));
        }
    }

    public static String getGameSimId() {
      return gameSimId;
    }

    @Override
    public void updateHealth(int newValue) {
        TextView healthTextView = findViewById(R.id.health);
        healthTextView.setText(adjustFactors(healthTextView, newValue));
        health_val = Utils.parseTextViewInt(healthTextView);
        if (health_val <= 50) {
            scraping_achieved = true;
            generateToast("Trophy Achieved: Scraping By");
        }
    }


    @Override
    public void updateGrade(int newValue) {
        TextView gradeTextView = findViewById(R.id.grade);
        gradeTextView.setText(adjustFactors(gradeTextView, newValue));
        grade_val = Utils.parseTextViewInt(gradeTextView);
    }

    @Override
    public void updateMoney(int newValue) {
        if (newValue > 0)
            weekly_earnings += newValue;
        else
            weekly_spending -= newValue;
        TextView moneyTextView = findViewById(R.id.money);
        moneyTextView.setText(adjustFactors(moneyTextView, newValue));
    }

    @Override
    public void updateDay() {
        TextView textview = findViewById(R.id.day_of_week);
        if (day_id == Day.SATURDAY) {
            updateWeek();
        }
        day_id = day_id.next();
        textview.setText(day_id.getDayString());
        updatePerson();
    }

    @Override
    public void updatePerson() {
        TextView healthView = findViewById(R.id.health);
        TextView gradeView = findViewById(R.id.grade);
        if (Utils.parseTextViewInt(healthView) <= 50 || Utils.parseTextViewInt(gradeView) <= 50 ) {
            ImageView persona = findViewById(R.id.persona);
            persona.setImageResource(R.drawable.persona_really_sad);
        } else if (Utils.parseTextViewInt(healthView) <= 70 || Utils.parseTextViewInt(gradeView) <= 70 ) {
            ImageView persona = findViewById(R.id.persona);
            persona.setImageResource(R.drawable.persona_sad);
        }
    }

    @Override
    public void updateWeek() {
        if (week_id == 2) {
            game_end = true;
            if (grade_val >= 90) {
                studious_achieved = true;
                generateToast("Trophy Achieved: How Studious");
            }
            if (health_val >= 90) {
                happy_healthy_achieved = true;
                generateToast("Trophy Achieved: Happy & Healthy");
            }
        } else {
            if (weekly_earnings - weekly_spending >= userGoalValue) {
                saver_achieved = true;
                generateToast("Trophy Achieved: Amazing Saver");
            }

            TextView textview = findViewById(R.id.week);
            week_id += 1;
            weekly_earnings = 0;
            weekly_spending = 0;
            textview.setText(weeks[week_id]);
            Utils.appendReport(gameSimId, generateReport(week_id), getApplicationContext());

            if (week_id >= 1) {
                streak_achieved = true;
                generateToast("Trophy Achieved: 7 Day Streak");
            }
        }
    }

    private void generateToast(CharSequence text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this /* MyActivity */, text, duration);
        toast.show();
    }

    private ReportData generateReport(int weekNumber) {
      int health = Utils.parseTextViewInt(findViewById(R.id.health));
      int grade = Utils.parseTextViewInt(findViewById(R.id.grade));
      int money = Utils.parseTextViewInt(findViewById(R.id.money));

      return new ReportData(weekNumber, money, health, grade);
    }
}
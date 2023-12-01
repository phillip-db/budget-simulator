package com.uiuc.budgetsimulator;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.uiuc.budgetsimulator.ui.financial_plan.FinancialPlanFragment;

import com.uiuc.budgetsimulator.ui.home.Scenarios.Scenario.Category;

import com.uiuc.budgetsimulator.ui.home.UpdateValuesListener;
import com.uiuc.budgetsimulator.ui.reports.ReportData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import android.animation.ValueAnimator;

public class MainActivity extends AppCompatActivity implements UpdateValuesListener {
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
        private static final Day[] vals = values();
        private static final String[] days = {"Sun", "Mon", "Tue", "Wed", "Thurs", "Fri", "Sat"};

        public Day next() {
            return vals[(this.ordinal() + 1) % vals.length];
        }

        public String getDayString() {
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

    public static Map<Category, Integer> categorySpending = new EnumMap<>(Category.class);
    public static Map<Category, Integer> categoryEarning = new EnumMap<>(Category.class);

    public static int userGoalValue;

    // TROPHIES
    public static boolean streak_achieved = false;
    public static boolean saver_achieved = false;
    public static boolean scraping_achieved = false;
    public static boolean studious_achieved = false;
    public static boolean happy_healthy_achieved = false;
    public static boolean financial_goal_achieved = false;

    // TUTORIAL DAY
//    public static boolean first_day = true;
    public static boolean tutorial_intro = false;
    public static boolean tutorial_popup = false;
    public static boolean tutorial_trophies = false;
    public static boolean tutorial_plan = false;
    public static boolean tutorial_reports = false;
    public static int help_page;
    public static int[] help_pages = new int[] {
            R.string.help_0, R.string.help_1, R.string.help_2, R.string.help_3, R.string.help_4,
            R.string.help_5, R.string.help_6, R.string.help_7, R.string.help_8, R.string.help_9,
            R.string.help_10, R.string.help_11 };


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
                R.id.navigation_home, R.id.navigation_financial_plans, R.id.navigation_weekly_reports, R.id.navigation_trophies)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Figure out some way to change this later when switching simulations/starting new simulation
        gameSimId = "test_sim";


        // Create initial week 0 for comparisons
        ReportData test_week = new ReportData(0, Utils.parseTextViewInt(findViewById(R.id.money)),
                health_val, grade_val, 0, 0,
                new EnumMap<>(Category.class),
                new EnumMap<>(Category.class));
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

//        createPopUp(R.string.help_0);
    }

    public static String getGameSimId() {
        return gameSimId;
    }

    public void adjustmentAnimation(TextView textView, int adjustment) {
        if (adjustment >= 0) {
            textView.setTextColor(Color.GREEN);
            textView.setText("+" + adjustment);
        } else {
            textView.setTextColor(Color.RED);
            textView.setText("" + adjustment);
        }
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f );
        AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f );
        textView.startAnimation(fadeIn);
        textView.startAnimation(fadeOut);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(500);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(3000+fadeIn.getStartOffset());
    }
    public static int adjustFactors(TextView textView, int adjustment) {
        String s = (String)textView.getText();
        if (s.charAt(s.length() - 1) == '%') {
            int newFactor = Math.min(100, adjustment + Utils.parseTextViewInt(textView));
            if (newFactor < 0) {
                newFactor = 0;
            }
            //return newFactor + "%";
            return newFactor;
        } else {
            //return "$" + (adjustment + Utils.parseTextViewInt(textView));
            return adjustment + Utils.parseTextViewInt(textView);
        }
    }


    @Override
    public void updateHealth(int newValue) {
        TextView healthTextView = findViewById(R.id.health);
        adjustmentAnimation(findViewById(R.id.healthAdjustment), newValue);
        newValue = adjustFactors(healthTextView, newValue);
        updateTopBarTextWithAnimation(newValue, healthTextView);
        health_val = Utils.parseTextViewInt(healthTextView);
        if (health_val <= 50 && !scraping_achieved) {
            generateToast("Trophy Achieved: Scraping By");
            scraping_achieved = true;
        }
    }

    @Override
    public void updateGrade(int newValue) {
        TextView gradeTextView = findViewById(R.id.grade);
        adjustmentAnimation(findViewById(R.id.gradeAdjustment), newValue);
        newValue = adjustFactors(gradeTextView, newValue);
        updateTopBarTextWithAnimation(newValue, gradeTextView);
        grade_val = Utils.parseTextViewInt(gradeTextView);
    }

    @Override
    public void updateMoney(int newValue, Category category) {
        Log.d("DEBUG", String.valueOf(category) + " " + newValue);
        if (newValue > 0) {
            weekly_earnings += newValue;
            categoryEarning.put(category, categoryEarning.getOrDefault(category, 0) + newValue);
        } else if (newValue < 0) {
            weekly_spending -= newValue;
            categorySpending.put(category, categorySpending.getOrDefault(category, 0) - newValue);
        }
        TextView moneyTextView = findViewById(R.id.money);
        adjustmentAnimation(findViewById(R.id.moneyAdjustment), newValue);
        newValue = adjustFactors(moneyTextView, newValue);
        updateTopBarTextWithAnimation(newValue, moneyTextView);
    }

    private void updateTopBarTextWithAnimation(int newValue, TextView textView) {
        int oldValue = Utils.parseTextViewInt(textView);
        ValueAnimator animator = ValueAnimator.ofInt(Utils.parseTextViewInt(textView), newValue);
        animator.setDuration(1000); // Set the duration of the animation (in milliseconds)

        animator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            String text;
            if (textView.getText().toString().endsWith("%")) {
                text = animatedValue + "%";
            } else {
                text = "$" + animatedValue;
            }
            textView.setText(text);
        });

        animator.start();
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), Color.WHITE, Color.RED);
        colorAnimation.setDuration(500);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((Integer)animator.getAnimatedValue());
            }

        });
        ValueAnimator colorAnimation2= ValueAnimator.ofObject(new ArgbEvaluator(), Color.RED, Color.WHITE);
        colorAnimation2.setDuration(2500);

        colorAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((Integer)animator.getAnimatedValue());
            }

        });
        ValueAnimator colorAnimation3 = ValueAnimator.ofObject(new ArgbEvaluator(), Color.WHITE, Color.GREEN);
        colorAnimation3.setDuration(500);

        colorAnimation3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((Integer)animator.getAnimatedValue());
            }

        });
        ValueAnimator colorAnimation4= ValueAnimator.ofObject(new ArgbEvaluator(), Color.GREEN, Color.WHITE);
        colorAnimation4.setDuration(2500);

        colorAnimation4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((Integer)animator.getAnimatedValue());
            }

        });
        if (oldValue > newValue) {
            colorAnimation.start();
            colorAnimation2.start();
        } else {
            colorAnimation3.start();
            colorAnimation4.start();
        }
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
        if (Utils.parseTextViewInt(healthView) <= 60 || Utils.parseTextViewInt(gradeView) <= 60) {
            ImageView persona = findViewById(R.id.persona);
            persona.setImageResource(R.drawable.persona_really_sad);
        } else if (Utils.parseTextViewInt(healthView) <= 80 || Utils.parseTextViewInt(gradeView) <= 80) {
            ImageView persona = findViewById(R.id.persona);
            persona.setImageResource(R.drawable.persona_sad);
        }
    }

    @Override
    public void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Game End! We haven't implemented anything past this yet.");
        AlertDialog end = builder.create();
        end.setCancelable(false);
        end.show();
    }

    @Override
    public void updateWeek() {
        if (week_id == 2) {
            game_end = true;
            if (grade_val >= 90) {
                if (!studious_achieved)
                    generateToast("Trophy Achieved: How Studious");
                studious_achieved = true;
            }
            if (health_val >= 90) {
                if (!happy_healthy_achieved)
                    generateToast("Trophy Achieved: Happy & Healthy");
                happy_healthy_achieved = true;
            }
            endGame();
        } else {
            if (weekly_earnings - weekly_spending >= FinancialPlanFragment.getValueByKey(FinancialPlanFragment.KEY_GOAL)) {
                if (!saver_achieved)
                    generateToast("Trophy Achieved: Amazing Saver");
                saver_achieved = true;
            }

            // TODO: Figure out intervals to apply the financial plan values
            int entertainmentExpense = FinancialPlanFragment.getValueByKey(FinancialPlanFragment.KEY_ENTERTAINMENT);
            updateMoney(-entertainmentExpense, Category.ENTERTAINMENT);
            int foodExpense = FinancialPlanFragment.getValueByKey(FinancialPlanFragment.KEY_EATING_OUT);
            foodExpense += FinancialPlanFragment.getValueByKey(FinancialPlanFragment.KEY_GROCERIES);
            updateMoney(-foodExpense, Category.FOOD);

            // int rentExpense = FinancialPlanFragment.getValueByKey(FinancialPlanFragment.KEY_RENT);
            // updateMoney(-rentExpense, Category.ALLOWANCE);

            int workEarning = FinancialPlanFragment.getValueByKey(FinancialPlanFragment.KEY_JOB_INCOME);
            updateMoney(workEarning, Category.WORK);
            int allowanceEarning = FinancialPlanFragment.getValueByKey(FinancialPlanFragment.KEY_ALLOWANCE);
            updateMoney(allowanceEarning, Category.ALLOWANCE);

            TextView textview = findViewById(R.id.week);
            week_id += 1;
            textview.setText(weeks[week_id]);

            Utils.appendReport(gameSimId, generateReport(week_id), getApplicationContext());

            weekly_earnings = 0;
            weekly_spending = 0;
            if (week_id >= 1) {
                if (!streak_achieved)
                    generateToast("Trophy Achieved: 7 Day Streak");
                streak_achieved = true;
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

        for (Category c : Category.values())
        {
            Log.d("CATEGORY", c.toString());
            MainActivity.categorySpending.putIfAbsent(c, 0);
            MainActivity.categoryEarning.putIfAbsent(c, 0);
        }

        ReportData reportData = new ReportData(weekNumber, money, health, grade, MainActivity.weekly_spending,
            MainActivity.weekly_earnings, MainActivity.categorySpending,
            MainActivity.categoryEarning);

        reportData.setWeeklyGoal(FinancialPlanFragment.getValueByKey(FinancialPlanFragment.KEY_GOAL));

        return reportData;
    }



//    public void createPopUp(int string_help) {
//        View root = R.layout.activity_main;
//        LayoutInflater inflater = getLayoutInflater();
//        View popUpView = inflater.inflate(R.layout.fragment_help, null);
//
//        int width = ViewGroup.LayoutParams.MATCH_PARENT;
//        int height = ViewGroup.LayoutParams.MATCH_PARENT;
//        boolean focusable = true;
//        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
//        popupWindow.showAtLocation(this, Gravity.CENTER, 0, 0);
//
//        TextView help_text = popUpView.findViewById(R.id.help_text);
//        help_text.setText(string_help);
//        help_page = 0;
//
//        Button next_button = popUpView.findViewById(R.id.help_next_button);
//        next_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (help_page != 11) {
//                    help_page++;
//                    help_text.setText(help_pages[help_page]);
//                } else {
//                    Button button = root.findViewById(R.id.start_day_id);
//                    button.setVisibility(View.GONE);
//                    popupWindow.dismiss();
//                }
//            }
//        });
//        Button back_button = popUpView.findViewById(R.id.help_back_button);
//        back_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (help_page != 0) {
//                    help_page--;
//                    help_text.setText(help_pages[help_page]);
//                } else {
//                    Button button = root.findViewById(R.id.start_day_id);
//                    button.setVisibility(View.GONE);
//                    popupWindow.dismiss();
//                }
//            }
//        });
//
//    }

}
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

    @Override
    public void updateHealth(int newValue) {
        TextView healthTextView = findViewById(R.id.health);
        int originalHealthValue = Integer.parseInt(healthTextView.getText().toString());
        int updatedHealthValue = originalHealthValue - newValue;
        healthTextView.setText(String.valueOf(updatedHealthValue));
    }

    @Override
    public void updateGrade(int newValue) {
        TextView gradeTextView = findViewById(R.id.grade);
        int originalGradeValue = Integer.parseInt(gradeTextView.getText().toString());
        int updatedHealthValue = originalGradeValue - newValue;
        gradeTextView.setText(String.valueOf(updatedHealthValue));
    }

    @Override
    public void updateMoney(int newValue) {
        TextView moneyTextView = findViewById(R.id.money);
        int originalHMoneyValue = Integer.parseInt(moneyTextView.getText().toString());
        int updatedHealthValue = originalHMoneyValue - newValue;
        moneyTextView.setText(String.valueOf(updatedHealthValue));
    }
}
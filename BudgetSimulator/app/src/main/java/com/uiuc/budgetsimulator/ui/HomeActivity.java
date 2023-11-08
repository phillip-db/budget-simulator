package com.uiuc.budgetsimulator.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uiuc.budgetsimulator.R;
import com.uiuc.budgetsimulator.SingleChoiceDialogFragment;

public class HomeActivity extends AppCompatActivity implements SingleChoiceDialogFragment.SingleChoiceListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button btnSelectChoice=findViewById(R.id.start_day_id);
//        btnSelectChoice.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                DialogFragment singleChoiceDialog = new SingleChoiceDialogFragment();
//                singleChoiceDialog.setCancelable(false);
//                singleChoiceDialog.show(getSupportFragmentManager(), "single choice dialog");
//            }
//        });
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {

    }
}

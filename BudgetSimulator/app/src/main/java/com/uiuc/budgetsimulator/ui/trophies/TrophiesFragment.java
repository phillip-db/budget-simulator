package com.uiuc.budgetsimulator.ui.trophies;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.uiuc.budgetsimulator.R;

public class TrophiesFragment extends Fragment {

    private TrophiesViewModel trophiesViewModel;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        trophiesViewModel =
                new ViewModelProvider(this).get(TrophiesViewModel.class);
        root = inflater.inflate(R.layout.fragment_trophies, container, false);

        Drawable bw_trophy = getResources().getDrawable(R.drawable.trophy_image_transparent_black_white);


        Button streak_button = root.findViewById(R.id.trophy_7_day_streak);
        streak_button.setCompoundDrawablesWithIntrinsicBounds(null, bw_trophy , null, null);
        streak_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUp(R.string.trophy_seven_day_streak, R.string.trophy_streak_description);
            }
        });

        Button saver_button = root.findViewById(R.id.trophy_amazing_saver);
        saver_button.setCompoundDrawablesWithIntrinsicBounds(null, bw_trophy , null, null);
        saver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUp(R.string.trophy_amazing_saver, R.string.trophy_saver_description);
            }
        });

        Button scraping_button = root.findViewById(R.id.trophy_scraping_by);
        scraping_button.setCompoundDrawablesWithIntrinsicBounds(null, bw_trophy , null, null);
        scraping_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUp(R.string.trophy_scraping_by, R.string.trophy_scraping_description);
            }
        });

        Button studious_button = root.findViewById(R.id.trophy_how_studious);
        studious_button.setCompoundDrawablesWithIntrinsicBounds(null, bw_trophy , null, null);
        studious_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUp(R.string.trophy_how_studious, R.string.trophy_studious_description);
            }
        });

        Button happy_healthy_button = root.findViewById(R.id.trophy_happy_healthy);
        happy_healthy_button.setCompoundDrawablesWithIntrinsicBounds(null, bw_trophy , null, null);
        happy_healthy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUp(R.string.trophy_happy_healthy, R.string.trophy_happy_healthy_description);
            }
        });

        Button financial_plan_button = root.findViewById(R.id.trophy_financial_plan);
        financial_plan_button.setCompoundDrawablesWithIntrinsicBounds(null, bw_trophy , null, null);
        financial_plan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUp(R.string.trophy_financial_plan, R.string.trophy_financial_plan_description);
            }
        });


        return root;
    }

    public void createPopUp(int string_title, int string_description) {
        LayoutInflater inflater = getLayoutInflater();
        View popUpView = inflater.inflate(R.layout.fragment_trophies_popup_description, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
        popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);

        TextView title = popUpView.findViewById(R.id.trophy_description_title);
        TextView description = popUpView.findViewById(R.id.trophy_description);
        title.setText(string_title);
        description.setText(string_description);

        popUpView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
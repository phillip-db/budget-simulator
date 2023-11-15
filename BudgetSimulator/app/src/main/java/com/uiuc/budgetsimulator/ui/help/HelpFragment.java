package com.uiuc.budgetsimulator.ui.help;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.uiuc.budgetsimulator.R;

public class HelpFragment extends Fragment {

    private HelpViewModel helpViewModel;
    View root;
    int help_page;
    int[] help_pages = new int[] {
            R.string.help_0, R.string.help_1, R.string.help_2, R.string.help_3, R.string.help_4,
            R.string.help_5, R.string.help_6, R.string.help_7, R.string.help_8, R.string.help_9,
            R.string.help_10, R.string.help_11 };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        helpViewModel =
                new ViewModelProvider(this).get(HelpViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        createPopUp(R.string.help_0);

        return root;
    }

    public void createPopUp(int string_help) {
        LayoutInflater inflater = getLayoutInflater();
        View popUpView = inflater.inflate(R.layout.fragment_help, null);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);
        popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);

        TextView help_text = popUpView.findViewById(R.id.help_text);
        help_text.setText(string_help);
        help_page = 0;

        Button next_button = popUpView.findViewById(R.id.help_next_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (help_page != 11) {
                    help_page++;
                    help_text.setText(help_pages[help_page]);
                } else {
                    popupWindow.dismiss();
                }
            }
        });
        Button back_button = popUpView.findViewById(R.id.help_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (help_page != 0) {
                    help_page--;
                    help_text.setText(help_pages[help_page]);
                } else {
                    popupWindow.dismiss();
                }
            }
        });


    }

}
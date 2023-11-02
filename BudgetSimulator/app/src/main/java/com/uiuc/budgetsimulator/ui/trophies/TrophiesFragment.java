package com.uiuc.budgetsimulator.ui.trophies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.uiuc.budgetsimulator.R;

public class TrophiesFragment extends Fragment {

    private TrophiesViewModel trophiesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        trophiesViewModel =
                new ViewModelProvider(this).get(TrophiesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_trophies, container, false);
        final TextView textView = root.findViewById(R.id.text_trophies);
        trophiesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
package com.uiuc.budgetsimulator.ui.reports;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.uiuc.budgetsimulator.R;
import com.uiuc.budgetsimulator.ui.home.Scenarios.Scenario.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ReportSummaryFragment extends Fragment {

    private BarChart mChart;

    private ReportData reportData;

    ReportSummaryFragment(ReportData reportData)
    {
        this.reportData = reportData;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_full, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChart = view.findViewById(R.id.category_chart);

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        // Collection<Float> spendingCollection = reportData.getCategorySpending().values().stream().map(Integer::floatValue).collect(Collectors.toCollection(TreeSet::new));
        float[] spending = new float[Category.values().length];
        float[] earning = new float[Category.values().length];
        for (Category c : Category.values())
        {
            spending[c.ordinal()] = reportData.getCategorySpending().getOrDefault(c, 0);
            earning[c.ordinal()] = reportData.getCategoryEarning().getOrDefault(c, 0);
        }


        yVals1.add(new BarEntry(
                0,
                spending,
                getResources().getDrawable(R.drawable.ic_report)));

        yVals1.add(new BarEntry(
                1,
                earning,
                getResources().getDrawable(R.drawable.ic_report)));

        BarDataSet set1;

        if (mChart != null && mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setDrawIcons(false);
            Set<Category> spendingSet = reportData.getCategorySpending().keySet();
            Set<Category> earningSet = reportData.getCategoryEarning().keySet();
            Set<Category> combinedCategories = new HashSet<>(spendingSet);
            combinedCategories.addAll(earningSet);
            String[] labels = Arrays.stream(Category.values()).map(e -> e.name().substring(0,1).toUpperCase() + e.name().substring(1).toLowerCase()).toArray(String[]::new);
            set1.setStackLabels(labels);
            set1.setColors(getColors(labels));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueFormatter(new MyValueFormatter());
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(20);

            mChart.setData(data);
            mChart.setPinchZoom(false);

            mChart.setDrawGridBackground(false);
            mChart.setDrawBarShadow(false);

            mChart.setDrawValueAboveBar(false);
            mChart.setHighlightFullBarEnabled(false);

            // change the position of the y-labels
            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setValueFormatter(new MyValueFormatter());
            leftAxis.setDrawGridLines(false);
            leftAxis.setTextSize(14f);
            leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
            mChart.getAxisRight().setEnabled(false);

            final ArrayList<String> xAxisLabel = new ArrayList<>();
            xAxisLabel.add("Spending");
            xAxisLabel.add("Earning");

            XAxis xLabels = mChart.getXAxis();
            xLabels.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    if (value == 0f || value == 1f)
                        return xAxisLabel.get((int) value);
                    else return "";
                }
            });
            xLabels.setDrawGridLines(false);
            xLabels.setTextSize(14f);
            xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);

            mChart.getDescription().setEnabled(false);

            Legend l = mChart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setFormSize(14f);
            l.setFormToTextSpace(4f);
            l.setXEntrySpace(6f);
            l.setTextSize(20f);
            l.setWordWrapEnabled(true);
        }

        mChart.setFitBars(true);
        mChart.invalidate();
    }

    private ArrayList<Integer> getColors(String[] labels) {
        ArrayList<Integer> cList = new ArrayList<>();
        for (String label : labels){
            Log.d("LABEL", label);
            cList.add(Category.categoryColors[Category.valueOf(label.toUpperCase()).ordinal()]);
        }
        return cList;
    }
}

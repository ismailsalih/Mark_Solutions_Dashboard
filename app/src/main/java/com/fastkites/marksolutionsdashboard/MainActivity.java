package com.fastkites.marksolutionsdashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageButton settingsBtn, notificationBtn;
    ChipGroup choiceChipGroup;
    Chip todayChip, weekChip, monthChip;
    PieChart topBrandChart;
    RecyclerView upcomingChequeRecView, inwardChequeRecView;

    ChequeAdapter upcomingChequeAdapter, inwardChequeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsBtn = findViewById(R.id.settingsBtn);
        notificationBtn = findViewById(R.id.notificationBtn);
        choiceChipGroup = findViewById(R.id.choiceChipGroup);
        todayChip = findViewById(R.id.todayChip);
        weekChip = findViewById(R.id.weekChip);
        monthChip = findViewById(R.id.monthChip);
        topBrandChart = findViewById(R.id.topBrandPieChart);
        upcomingChequeRecView = findViewById(R.id.upcomingChequesRecView);
        inwardChequeRecView = findViewById(R.id.inwardChequesRecView);
        upcomingChequeAdapter = new ChequeAdapter(upcomingChequeList());
        inwardChequeAdapter = new ChequeAdapter(inwardChequeList());

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Settings.class));
            }
        });

        todayChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                todayChip.setChipBackgroundColorResource(R.color.secondary_red);
                todayChip.setChipStrokeColorResource(R.color.white_shade);
                weekChip.setChipBackgroundColorResource(R.color.white_shade);
                weekChip.setChipStrokeColorResource(R.color.secondary_red);
                monthChip.setChipBackgroundColorResource(R.color.white_shade);
                monthChip.setChipStrokeColorResource(R.color.secondary_red);
            }
        });
        weekChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                weekChip.setChipBackgroundColorResource(R.color.secondary_red);
                weekChip.setChipStrokeColorResource(R.color.white_shade);
                todayChip.setChipBackgroundColorResource(R.color.white_shade);
                todayChip.setChipStrokeColorResource(R.color.secondary_red);
                monthChip.setChipBackgroundColorResource(R.color.white_shade);
                monthChip.setChipStrokeColorResource(R.color.secondary_red);
            }
        });
        monthChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                monthChip.setChipBackgroundColorResource(R.color.secondary_red);
                monthChip.setChipStrokeColorResource(R.color.white_shade);
                todayChip.setChipBackgroundColorResource(R.color.white_shade);
                todayChip.setChipStrokeColorResource(R.color.secondary_red);
                weekChip.setChipBackgroundColorResource(R.color.white_shade);
                weekChip.setChipStrokeColorResource(R.color.secondary_red);
            }
        });
        /*choiceChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == todayChip.getId()) {

            } else if (checkedId == weekChip.getId()) {

            } else if (checkedId == monthChip.getId()) {

            }
        });*/

        upcomingChequeRecView.setLayoutManager(new LinearLayoutManager(this));
        upcomingChequeRecView.setAdapter(upcomingChequeAdapter);
        upcomingChequeAdapter.notifyDataSetChanged();

        inwardChequeRecView.setLayoutManager(new LinearLayoutManager(this));
        inwardChequeRecView.setAdapter(inwardChequeAdapter);
        inwardChequeAdapter.notifyDataSetChanged();

        List<String> labels = getLabelsFromDatabase(); // Replace with your actual method

        // Generate a gradient of red shades
        int[] colors = generateRedShades(labels.size());

        // Sample data for the pie chart
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < labels.size(); i++) {
            entries.add(new PieEntry(1f, labels.get(i))); // Use a weight of 1 for each entry
        }

        PieDataSet dataSet = new PieDataSet(entries, "Your Chart Description");
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        topBrandChart.setData(pieData);

        // Configure chart appearance and behavior
        topBrandChart.getDescription().setEnabled(false);
        topBrandChart.setHoleRadius(60f);
        topBrandChart.setHoleColor(Color.TRANSPARENT);
        topBrandChart.setTransparentCircleRadius(50f);
        topBrandChart.setEntryLabelColor(Color.BLACK);
        topBrandChart.setEntryLabelTextSize(12f);

        // Animate the chart
        topBrandChart.animateY(1000);

        Legend legend = topBrandChart.getLegend();
        legend.setEnabled(false);

    }

    private int[] generateRedShades(int count) {
        int[] colors = new int[count];

        // Base color for dark shade of red
        int baseColor = Color.rgb(255, 0, 0);

        // Generate gradient of red shades
        for (int i = 0; i < count; i++) {
            float ratio = (float) i / (float) (count - 1);
            colors[i] = ColorUtils.blendARGB(baseColor, Color.WHITE, ratio);
        }

        return colors;
    }

    // Replace this method with your actual method to fetch data from the database
    private List<String> getLabelsFromDatabase() {
        // Replace with your logic to fetch labels from the database
        // For now, returning a sample list
        List<String> labels = new ArrayList<>();
        labels.add("Label 1");
        labels.add("Label 2");
        labels.add("Label 3");
        labels.add("Label 3");
        labels.add("Label 3");
        labels.add("Label 3");
        labels.add("Label 3");
        return labels;
    }

    public List<ChequeModel> upcomingChequeList() {
        List<ChequeModel> list = new ArrayList<>();
        list.add(new ChequeModel("Fast Kites Technologies", "22-01-2023", "3129078-7482-483", "BOC", "12904921"));
        list.add(new ChequeModel("Fast Kites Technologies", "22-01-2023", "3129078-7482-483", "BOC", "12904921"));
        list.add(new ChequeModel("Fast Kites Technologies", "22-01-2023", "3129078-7482-483", "BOC", "12904921"));
        return list;
    }

    public List<ChequeModel> inwardChequeList() {
        List<ChequeModel> list = new ArrayList<>();
        list.add(new ChequeModel("Fast Kites Technologies", "22-01-2023", "3129078-7482-483", "BOC", "12904921"));
        list.add(new ChequeModel("Fast Kites Technologies", "22-01-2023", "3129078-7482-483", "BOC", "12904921"));
        list.add(new ChequeModel("Fast Kites Technologies", "22-01-2023", "3129078-7482-483", "BOC", "12904921"));
        return list;
    }
}
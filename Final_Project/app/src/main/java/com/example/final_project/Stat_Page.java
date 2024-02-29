package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.example.final_project.Model.TopBooks;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Stat_Page extends AppCompatActivity {

    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_page);
        barChart=findViewById(R.id.bar_chart);
        Book_Database b= Book_Database.getInstance(Stat_Page.this);

        Cursor cursor=b.fetchBooks();
        List<TopBooks> list=new ArrayList<TopBooks>();

        ArrayList<BarEntry> barEntries =new ArrayList<>();
        ArrayList<String> bookNames = new ArrayList<>();

        // Loop through all entries in the cursor
        int i = 0;
        while (!cursor.isAfterLast()) {
            Cursor crc = b.get_sells(cursor.getInt(0));
            if(crc.getCount()>0)
            {
                int counterValue = crc.getInt(1);
                String bookName = cursor.getString(1);
                bookNames.add(bookName);
                // Add entry to bar chart
                barEntries.add(new BarEntry(i, counterValue));
            }
            else
            {
                int counterValue = 0;
                String bookName = cursor.getString(1);
                bookNames.add(bookName);

                // Add entry to bar chart
                barEntries.add(new BarEntry(i, counterValue));
            }
            // Extract "counter" value and book name
            cursor.moveToNext();
            i++;
        }

        // Create a bar data set
        BarDataSet barDataSet = new BarDataSet(barEntries, "Books");
        // Set colors
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        // Hide draw value
        barDataSet.setDrawValues(false);


        // Set bar data
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barData.setValueTextColor(Color.WHITE);


        // Customize x-axis labels
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(bookNames));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setLabelCount(bookNames.size());
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setGridColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);


        //set animation
        barChart.animateY(1000);
        //set description text and color
        barChart.getDescription().setText("Top Books Sold Chart");
        barChart.getDescription().setTextColor(Color.WHITE);
        barChart.getBarData().setValueTextColor(Color.WHITE);
        barChart.setGridBackgroundColor(Color.WHITE);
        barChart.setBorderColor(Color.WHITE);
        barChart.getAxisLeft().setTextColor(Color.WHITE);
        barChart.getAxisLeft().setGridColor(Color.WHITE);
        barChart.getAxisRight().setTextColor(Color.WHITE);
        barChart.getAxisRight().setGridColor(Color.WHITE);
        barChart.getData().setValueTextColor(Color.WHITE);
        barChart.getDescription().setTextColor(Color.WHITE);

    }
}
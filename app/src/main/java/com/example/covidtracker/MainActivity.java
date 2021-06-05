package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidtracker.api.CountryData;
import com.example.covidtracker.api.apiutilities;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private TextView totalconfirm,totalactive,totalrecovered,totaldeaths,totaltests;
    private TextView todayconfirm,todayrecovered,todaydeath,dateTV;

    private List<CountryData> list;
    private PieChart pieChart;
   String country="India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=new ArrayList<>();
        if(getIntent().getStringExtra("country")!=null)
            country=getIntent().getStringExtra("country");



        init();
   TextView cname=findViewById(R.id.cname);
   cname.setText(country);

        cname.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,CountryActivity.class)));

        apiutilities.getapiinterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                list.addAll(response.body());

                for(int i=0;i<list.size();i++)
                {
                    if(list.get(i).getCountry().equals(country))
                    {
int confirm=Integer.parseInt(list.get(i).getCases());
int active=Integer.parseInt(list.get(i).getActive());
int recoverd=Integer.parseInt(list.get(i).getRecovered());
int death=Integer.parseInt(list.get(i).getDeaths());

totalconfirm.setText(NumberFormat.getInstance().format(confirm));
totalactive.setText(NumberFormat.getInstance().format(active));
totalrecovered.setText(NumberFormat.getInstance().format(recoverd));
totaldeaths.setText(NumberFormat.getInstance().format(death));

todaydeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodaydeaths())));
todayconfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodaycases())));
todayrecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayrecovered())));
totaltests.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));

setText(list.get(i).getUpdated());

pieChart.addPieSlice(new PieModel("Confirm",confirm,getResources().getColor(R.color.yellow)));
pieChart.addPieSlice(new PieModel("Active",active,getResources().getColor(R.color.blue_pie)));
pieChart.addPieSlice(new PieModel("Recovered",recoverd,getResources().getColor(R.color.green_pie)));
pieChart.addPieSlice(new PieModel("Deaths",death,getResources().getColor(R.color.red)));

                    }
                }

            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Error  :"  + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setText(String updated) {
        DateFormat format=new SimpleDateFormat("MMM dd,yyyy");
        long miliseconds=Long.parseLong(updated);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(miliseconds);

        dateTV.setText("Updated at " +format.format(calendar.getTime()));
    }

    private  void init()
    {
        totalconfirm=findViewById(R.id.todayconfirm);
        totalactive=findViewById(R.id.totalactive);
        totalrecovered=findViewById(R.id.totalrecovered);
        totaldeaths=findViewById(R.id.totaldeath);
        todayconfirm=findViewById(R.id.todayconfirm);
        totaltests=findViewById(R.id.totaltests);
        todayrecovered=findViewById(R.id.todayrecovered);
        todaydeath=findViewById(R.id.todaydeath);
        pieChart=findViewById(R.id.pieChart);
        dateTV=findViewById(R.id.date);


    }
}
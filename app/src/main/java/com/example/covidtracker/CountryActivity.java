package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covidtracker.api.CountryData;
import com.example.covidtracker.api.apiutilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.covidtracker.R.layout.activity_country;

public class CountryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<CountryData> list;
    private ProgressDialog dialog;
    private EditText searchbar;
    private CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_country);



        recyclerView=findViewById(R.id.countries);
        list=new ArrayList<>();
        searchbar=findViewById(R.id.searchbar);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

         adapter=new CountryAdapter(this,list);
        recyclerView.setAdapter(adapter);



        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        apiutilities.getapiinterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {


                list.addAll(response.body());

                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {


                Toast.makeText(CountryActivity.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());

            }
        });
    }

    private void filter(String text) {
        List<CountryData> filterlist=new ArrayList<>();
        for(CountryData items:list)
        {
            if(items.getCountry().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(items);
            }
        }
   adapter.filterlist(filterlist);
    }
}
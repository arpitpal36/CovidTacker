package com.example.covidtracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covidtracker.api.CountryData;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private Context context;
    private List<CountryData> list;

    public CountryAdapter(Context context, List<CountryData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {

         View view= LayoutInflater.from(context).inflate(R.layout.country_item_layout,parent,false);
        return new CountryViewHolder(view);
    }

    public void filterlist(List<CountryData>  filterlist){
        list=filterlist;
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CountryAdapter.CountryViewHolder holder, int position) {


        CountryData data=list.get(position);
        holder.countrycases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));

        holder.countryname.setText(data.getCountry());
        holder.sno.setText(String.valueOf(position+1));

        Map<String,String> img=data.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.imageView);


        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context,MainActivity.class);
            intent.putExtra("country",data.getCountry());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        private TextView sno,countryname,countrycases;
        private ImageView imageView;

        public CountryViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);



            sno=itemView.findViewById(R.id.sno);
            countryname=itemView.findViewById(R.id.countryname);
            countrycases=itemView.findViewById(R.id.countrycases);
            imageView=itemView.findViewById(R.id.countryimage);


        }
    }
}

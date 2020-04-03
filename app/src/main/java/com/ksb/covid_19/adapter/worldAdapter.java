package com.ksb.covid_19.adapter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ksb.covid_19.R;
import com.ksb.covid_19.model.WorldInfo;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;

public class worldAdapter extends RecyclerView.Adapter<worldAdapter.worldHolder> {

    private ArrayList<WorldInfo> wlist;

    private ArrayList<WorldInfo> oldwList;

    public worldAdapter(ArrayList<WorldInfo> wlist, SharedPreferences sharedPreferences) {
        this.wlist = wlist;

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<WorldInfo>>() {}.getType();
        oldwList = gson.fromJson(sharedPreferences.getString("wold", null),type);

        if (oldwList == null) {
            oldwList = new ArrayList<>();
        }

        Gson gsonn = new Gson();
        sharedPreferences.edit().putString("wold",gsonn.toJson(wlist)).apply();
    }

    @NonNull
    @Override
    public worldHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.world_item,parent,false);

        return new worldHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull worldHolder holder, int position) {


        WorldInfo info = wlist.get(position);
        holder.countryName.setText(info.getContryName());

        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        if(!oldwList.isEmpty())
        {
            WorldInfo oldInfo = oldwList.get(position);

            int tcci = Integer.parseInt(info.getTc())-Integer.parseInt(oldInfo.getTc());
            int active =  Integer.parseInt(info.getActive())-Integer.parseInt(oldInfo.getActive());
            int cured = Integer.parseInt(info.getCured())-Integer.parseInt(oldInfo.getCured());
            int death = Integer.parseInt(info.getDeath())-Integer.parseInt(oldInfo.getDeath());

            if(tcci!=0)
            {
                String ftcci = " (+" + Integer.toString(tcci) + ")";
                holder.tc.setText(myFormat.format(Integer.parseInt(info.getTc())) + ftcci);
                holder.tc.setTextColor(Color.BLUE);
            }
            else{
                holder.tc.setText(myFormat.format(Integer.parseInt(info.getTc())));
            }
            if(active!=0)
            {
                String ftccf = " (+" + Integer.toString(active) + ")";
                holder.active.setText(myFormat.format(Integer.parseInt(info.getActive())) + ftccf);
                holder.active.setTextColor(Color.BLUE);
            }
            else{
                holder.active.setText(myFormat.format(Integer.parseInt(info.getActive())));
            }

            if(cured!=0)
            {
                String fcured = " (+" + Integer.toString(cured) + ")";
                holder.cured.setText(myFormat.format(Integer.parseInt(info.getCured())) + fcured);
                holder.cured.setTextColor(Color.GREEN);
            }
            else{
                holder.cured.setText(myFormat.format(Integer.parseInt(info.getCured())));
            }

            if(death!=0)
            {
                String fdeath = " (+" + Integer.toString(death) + ")";
                holder.death.setText(myFormat.format(Integer.parseInt(info.getDeath())) + fdeath);
                holder.death.setTextColor(Color.RED);
            }
            else{
                holder.death.setText(myFormat.format(Integer.parseInt(info.getDeath())));
            }

        }
        else
        {
            holder.tc.setText(info.getTc());
            holder.active.setText(info.getActive());
            holder.cured.setText(info.getCured());
            holder.death.setText(info.getDeath());
        }


        if(position == wlist.size()-1)
        {
            holder.countryName.setTextSize(22);
            holder.tc.setTextColor(Color.BLUE);
            holder.active.setTextColor(Color.BLUE);
            holder.cured.setTextColor(Color.GREEN);
            holder.death.setTextColor(Color.RED);

            holder.tc.setTextSize(15);
            holder.active.setTextSize(15);
            holder.cured.setTextSize(15);
            holder.death.setTextSize(15);
        }

      //  Log.i("TAG",Integer.toString(wlist.size()-1));



    }

    @Override
    public int getItemCount() {
        return wlist.size();
    }

    public class worldHolder extends RecyclerView.ViewHolder {

        TextView countryName;
        TextView tc;  // Total Confirmed cases (Indian National)
        TextView active;  // Total Confirmed cases ( Foreign National )
        TextView cured; // Cured
        TextView death;
        public worldHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.countryNamee);
            tc = itemView.findViewById(R.id.tc);
            active = itemView.findViewById(R.id.active);
            cured = itemView.findViewById(R.id.curedd);
            death = itemView.findViewById(R.id.deathh);
        }
    }
}

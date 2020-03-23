package com.ksb.covid_19;

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
import com.ksb.covid_19.model.Info;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.recyclerHolder> {

   private ArrayList<Info> list ;
   private SharedPreferences sharedPreferences;
   private ArrayList<Info> oldList;


    public recyclerAdapter(ArrayList<Info> list,SharedPreferences sharedd) {
        this.list = list;
        sharedPreferences = sharedd;

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Info>>() {}.getType();
        oldList = gson.fromJson(sharedPreferences.getString("old", null),type);

        if (oldList == null) {
            oldList = new ArrayList<>();
        }


        Gson gsonn = new Gson();
        sharedPreferences.edit().putString("old",gsonn.toJson(list)).apply();

    }

    @NonNull
    @Override
    public recyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_view,parent,false);

        return new recyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerHolder holder, int position) {

        Info info = list.get(position);
        holder.stateName.setText(info.getStateName());

            if(!oldList.isEmpty())
            {
                Info oldInfo = oldList.get(position);

                int tcci= info.getTcci()-oldInfo.getTcci();
                int tccf =info.getTccf()-oldInfo.getTccf();
                int cured = info.getCured()-oldInfo.getCured();
                int death = info.getDeath()-oldInfo.getDeath();

                if(tcci!=0)
                {
                    String ftcci = " (+" + Integer.toString(tcci) + ")";
                    holder.tcci.setText(Integer.toString(info.getTcci()) + ftcci);
                    holder.tcci.setTextColor(Color.BLUE);
                }
                else{
                    holder.tcci.setText(Integer.toString(info.getTcci()));
                }
                if(tccf!=0)
                {
                    String ftccf = " (+" + Integer.toString(tccf) + ")";
                    holder.tccf.setText(Integer.toString(info.getTccf()) + ftccf);
                    holder.tccf.setTextColor(Color.BLUE);
                }
                else{
                    holder.tccf.setText(Integer.toString(info.getTccf()));
                }

                if(cured!=0)
                {
                    String fcured = " (+" + Integer.toString(cured) + ")";
                    holder.cured.setText(Integer.toString(info.getCured()) + fcured);
                    holder.cured.setTextColor(Color.GREEN);
                }
                else{
                    holder.cured.setText(Integer.toString(info.getCured()));
                }

                if(death!=0)
                {
                    String fdeath = " (+" + Integer.toString(death) + ")";
                    holder.death.setText(Integer.toString(info.getDeath()) + fdeath);
                    holder.death.setTextColor(Color.RED);
                }
                else{
                    holder.death.setText(Integer.toString(info.getDeath()));
                }

            }
            else
            {
                holder.tcci.setText(Integer.toString(info.getTcci()));
                holder.tccf.setText(Integer.toString(info.getTccf()));
                holder.cured.setText(Integer.toString(info.getCured()));
                holder.death.setText(Integer.toString(info.getDeath()));
            }



           if(position == list.size()-1)
           {
               holder.stateName.setTextSize(16);
               holder.tcci.setTextColor(Color.BLUE);
               holder.tccf.setTextColor(Color.BLUE);
               holder.cured.setTextColor(Color.GREEN);
               holder.death.setTextColor(Color.RED);

               holder.tcci.setTextSize(16);
               holder.tccf.setTextSize(16);
               holder.cured.setTextSize(16);
               holder.death.setTextSize(16);
           }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class recyclerHolder extends RecyclerView.ViewHolder {
        TextView stateName;
        TextView tcci;  // Total Confirmed cases (Indian National)
        TextView tccf;  // Total Confirmed cases ( Foreign National )
        TextView cured; // Cured
        TextView death;

        public recyclerHolder(@NonNull View itemView) {
            super(itemView);
            stateName = itemView.findViewById(R.id.stateName);
            tcci = itemView.findViewById(R.id.confirmedin);
            tccf = itemView.findViewById(R.id.confirmedf);
            cured = itemView.findViewById(R.id.cured);
            death = itemView.findViewById(R.id.death);

        }
    }


}

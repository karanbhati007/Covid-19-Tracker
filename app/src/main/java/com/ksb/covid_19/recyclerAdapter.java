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

import com.ksb.covid_19.model.Info;

import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.recyclerHolder> {

   private List<Info> list ;
   private SharedPreferences sharedPreferences;

    public recyclerAdapter(List<Info> list,SharedPreferences sharedd) {
        this.list = list;
        sharedPreferences = sharedd;
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

        String name = info.getStateName();

        String tccii = sharedPreferences.getString(name+"tcci","-1");
        String tccff = sharedPreferences.getString(name+"tccf","-1");
        String curedd = sharedPreferences.getString(name+"cured","-1");
        String deathh = sharedPreferences.getString(name+"death","-1");

        Log.i("TAG",curedd);

        int tcci= info.getTcci()-Integer.parseInt(tccii);
        int tccf =info.getTccf()-Integer.parseInt(tccff);
        int cured = info.getCured()-Integer.parseInt(curedd);
        int death = info.getDeath()-Integer.parseInt(deathh);

        holder.stateName.setText(info.getStateName());
        if(Integer.parseInt(tccii)!=-1 && tcci!=0)
        {
            String ftcci = " (+" + Integer.toString(tcci) + ")";
            holder.tcci.setText(Integer.toString(info.getTcci()) + ftcci);
            holder.tcci.setTextColor(Color.BLUE);
        }
        else{ // tcci == tcci (current)
            holder.tcci.setText(Integer.toString(info.getTcci()));
        }
        if(Integer.parseInt(tccff)!=-1 && tccf!=0)
        {
            String ftccf = " (+" + Integer.toString(tccf) + ")";
            holder.tccf.setText(Integer.toString(info.getTccf()) + ftccf);
            holder.tccf.setTextColor(Color.BLUE);
        }
        else{
            holder.tccf.setText(Integer.toString(info.getTccf()));
        }

        if(Integer.parseInt(curedd)!=-1 && cured!=0)
        {
            String fcured = " (+" + Integer.toString(cured) + ")";
            holder.cured.setText(Integer.toString(info.getCured()) + fcured);
            holder.cured.setTextColor(Color.GREEN);
        }
        else{
            holder.cured.setText(Integer.toString(info.getCured()));
        }

        if(Integer.parseInt(deathh)!=-1 && death!=0)
        {
            String fdeath = " (+" + Integer.toString(death) + ")";
            holder.death.setText(Integer.toString(info.getDeath()) + fdeath);
            holder.death.setTextColor(Color.RED);
        }
        else{
            holder.death.setText(Integer.toString(info.getDeath()));
        }

        sharedPreferences.edit().putString(name+"tcci",Integer.toString(info.getTcci())).apply();
        sharedPreferences.edit().putString(name+"tccf",Integer.toString(info.getTccf())).apply();
        sharedPreferences.edit().putString(name+"cured",Integer.toString(info.getCured())).apply();
        sharedPreferences.edit().putString(name+"death",Integer.toString(info.getDeath())).apply();


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

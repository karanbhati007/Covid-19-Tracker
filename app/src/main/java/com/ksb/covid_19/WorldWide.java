package com.ksb.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.navigation.NavigationView;
import com.ksb.covid_19.model.WorldInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class WorldWide extends AppCompatActivity {

    String dataa;
    ArrayList<WorldInfo> wolrdList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SpinKitView progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_wide);

        navigationBar();

        progressBar = findViewById(R.id.wspin_kit);
        progressBar.setVisibility(View.VISIBLE);

       getData();
        sharedPreferences = this.getSharedPreferences("com.ksb.covid_19",MODE_PRIVATE);
    }

    private void navigationBar() {
        final DrawerLayout drawerLayout = findViewById(R.id.drawerr);
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle action = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(action);
        action.syncState();

        NavigationView navigationView = findViewById(R.id.navi);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.world:
                        Toast.makeText(WorldWide.this, "World Wide !!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.india:
                       startActivity(new Intent(WorldWide.this,MainActivity.class));
                       finish();
                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    private void getData() {
        String url = "https://www.worldometers.info/coronavirus/";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dataa=response;
                parseInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WorldWide.this, "Network Error , Plz Restart the app !!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    private void parseInfo() {
        try {
            Document document = Jsoup.parse(dataa);
            Elements elements = document.select("table").eq(0).select("tbody").select("tr");
            int size = elements.size();

            String contryName; // 0
            String tc;  // Total cases  // 1
            String active; // Active Cases //6
            String cured; // Total Cured/Recovered  // 5
            String death; // Total Death  // 3


            for (int i = 0; i < size; i++) {
                //Log.i("TAG",elements.get(i).select("td").get(0).text());
                Elements country = elements.get(i).select("td");

                contryName = country.get(0).text();
                tc = country.get(1).text().replaceAll(",", "");
                active = country.get(6).text().replaceAll(",", "");
                cured = country.get(5).text().replaceAll(",", "");
                death = country.get(3).text().replaceAll(",", "");

                tc = tc.isEmpty() ? "0" : tc;
                active = active.isEmpty() ? "0" : active;
                cured = cured.isEmpty() ? "0" : cured;
                death = death.isEmpty() ? "0" : death;

                //  cured = Integer.toString(Integer.parseInt(cured)+1);


                wolrdList.add(new WorldInfo(contryName, tc, active, cured, death));
            }

            initrecycler();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Worldometer Website May be Changed !!\nPlz Contact the Developer.", Toast.LENGTH_SHORT).show();
        }


    }

    private void initrecycler() {
        RecyclerView recyclerView = findViewById(R.id.wrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new worldAdapter(wolrdList,sharedPreferences));

        progressBar.setVisibility(View.GONE);
    }
}

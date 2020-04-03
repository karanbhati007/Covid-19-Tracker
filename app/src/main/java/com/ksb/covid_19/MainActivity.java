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
import android.util.Log;
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
import com.ksb.covid_19.adapter.recyclerAdapter;
import com.ksb.covid_19.model.Info;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String dataa;
    ArrayList<Info> listInfo = new ArrayList<>();
    SpinKitView progressBar;
    SharedPreferences sharedPreferences;
    int f=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.spin_kit);
        navigationBar();
        // listInfo.clear();

        progressBar.setVisibility(View.VISIBLE);
        getData();

        // String url = "https://www.mohfw.gov.in/";
        sharedPreferences = this.getSharedPreferences("com.ksb.covid_19", MODE_PRIVATE);

        if(sharedPreferences.getString("isFirst",null)==null)
        {
            Toast.makeText(this, "Data Loaded !!\nPlz Restart The App", Toast.LENGTH_SHORT).show();
            finish();
        }

        sharedPreferences.edit().putString("isFirst", "yo").apply();
    }


    private void parseInfo() {
        try {
            Document document = Jsoup.parse(dataa);

            Elements elements1 = document.select("tbody");   /*eq(9).select("tr");*/ // 1->size-1
            int tBodysize = elements1.size();
            Log.i("TAG111",Integer.toString(tBodysize));
            Elements elements = elements1.eq(tBodysize-1).select("tr");
            int size = elements.size();
           Log.i("TAG",Integer.toString(size));

            for (int i = 0; i <= size-1; i++) {
                Elements stateInfo = elements.get(i).select("td");
                Log.i("Data",stateInfo.text());

                try {
                    String name;
                    int tcci;
                    int tccf;
                    int cured;
                    int death;
                    if (i == size-1) {
                        name = stateInfo.get(0).text();
                     //   System.out.println(stateInfo.get(1).text());
                        tcci = Integer.parseInt(stateInfo.get(1).text().replaceAll("\\D+", ""));
                      //  tccf = Integer.parseInt(stateInfo.get(2).text().replaceAll("\\D+", ""));
                        cured = Integer.parseInt(stateInfo.get(2).text().replaceAll("\\D+", ""));
                        death = Integer.parseInt(stateInfo.get(3).text().replaceAll("\\D+", ""));
                      //  System.out.println(tcci);


                    } else {
                        name = stateInfo.get(1).text();
                        tcci = Integer.parseInt(stateInfo.get(2).text().replaceAll("\\D+", ""));
                      //  tccf = Integer.parseInt(stateInfo.get(3).text().replaceAll("\\D+", ""));
                        cured = Integer.parseInt(stateInfo.get(3).text().replaceAll("\\D+", ""));
                       death = Integer.parseInt(stateInfo.get(4).text().replaceAll("\\D+", ""));

                    }

                    listInfo.add(new Info(name, tcci, cured, death));
                }catch (Exception e)
                {
                    //Log.i("TAG2",Integer.toString(i));

                    if(f==0)
                    Toast.makeText(this, "Health Ministry Website May be Changed !!\nPlz Contact the Developer.", Toast.LENGTH_SHORT).show();
                    f=1;
                    e.printStackTrace();

                }
            }

            initrecycler();

        } catch (Exception e) {
            Toast.makeText(this, "Health Ministry Website May be Changed !!\nPlz Contact the Developer.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }

    // Log.d("TAG",name.substring(0,name.length()-1));
    private void initrecycler() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new recyclerAdapter(listInfo, sharedPreferences));

        progressBar.setVisibility(View.GONE);

    }


    private void getData() {
        String url = "https://www.mohfw.gov.in/";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dataa = response;
                parseInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Network Error , Plz Restart the app !!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        queue.add(request);
    }


    private void navigationBar() {
        final DrawerLayout drawerLayout = findViewById(R.id.drawerr);
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle action = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(action);
        action.syncState();

        NavigationView navigationView = findViewById(R.id.navi);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.world:
                        startActivity(new Intent(MainActivity.this, WorldWide.class));
                        finish();
                        break;
                    case R.id.india:
                        Toast.makeText(MainActivity.this, "India !!", Toast.LENGTH_SHORT).show();
                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

}

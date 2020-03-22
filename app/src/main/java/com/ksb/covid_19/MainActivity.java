package com.ksb.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;
import com.ksb.covid_19.model.Info;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String dataa;
    List<Info> listInfo = new ArrayList<>();
    SpinKitView progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.spin_kit);

        // listInfo.clear();

        progressBar.setVisibility(View.VISIBLE);
        getData();

        // String url = "https://www.mohfw.gov.in/";



        sharedPreferences = this.getSharedPreferences("com.ksb.covid_19",MODE_PRIVATE);
        // sharedPreferences.edit().putString("po","uouo").apply();
        sharedPreferences.edit().putString("isFirst","yo").apply();


    }




    private void parseInfo() {
        try {
              Document document = Jsoup.parse(dataa);

              Elements elements = document.select("tbody").eq(1).select("tr"); // 1->size-1
           int size = elements.size();

              for(int i=0;i<size;i++)
              {
                  Elements stateInfo = elements.get(i).select("td");

                  String name;
                  int tcci;
                  int tccf;
                  int cured;
                  int death;
                  if(i==size-1)
                  {
                      name = stateInfo.get(0).text();
                      tcci = Integer.parseInt(stateInfo.get(1).text());
                      tccf = Integer.parseInt(stateInfo.get(2).text());
                      cured = Integer.parseInt(stateInfo.get(3).text());
                      death = Integer.parseInt(stateInfo.get(4).text());

                    /*name = stateInfo.get(0).text();
                    String tccii="",tccff="",curedd="",deathh="";
                      tccii = stateInfo.get(1).text();
                      tccff =stateInfo.get(2).text();
                      curedd = stateInfo.get(3).text();
                      deathh = stateInfo.get(4).text();

                    tcci = Integer.parseInt(tccii.substring(0,tccii.length()));
                     tccf = Integer.parseInt(tccff.substring(0,tccff.length()));
                     cured = Integer.parseInt(curedd.substring(0,curedd.length()));
                       death = Integer.parseInt(deathh);
*/

                  }
                  else {
                      name = stateInfo.get(1).text();
                       tcci = Integer.parseInt(stateInfo.get(2).text());
                       tccf = Integer.parseInt(stateInfo.get(3).text());
                       cured = Integer.parseInt(stateInfo.get(4).text());
                       death = Integer.parseInt(stateInfo.get(5).text());

                  }


                  listInfo.add(new Info(name,tcci,tccf,cured,death));
              }

              initrecycler();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 // Log.d("TAG",name.substring(0,name.length()-1));
    private void initrecycler() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new recyclerAdapter(listInfo,sharedPreferences));

        progressBar.setVisibility(View.GONE);

    }


    private void getData() {
        String url = "https://www.mohfw.gov.in/";
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
                Toast.makeText(MainActivity.this, "Network Error,Plz Restart the app !!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        queue.add(request);
    }


}

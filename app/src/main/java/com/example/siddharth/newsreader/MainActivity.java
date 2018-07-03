package com.example.siddharth.newsreader;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.siddharth.newsreader.Adapter.ListSourceAdapter;
import com.example.siddharth.newsreader.Comman.Comman;
import com.example.siddharth.newsreader.Interface.NewsService;
import com.example.siddharth.newsreader.Model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    SpotsDialog dialog;
SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init cache
        Paper.init(this);
        //init sevice
        mService= Comman.getNewsService();
        //init View
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadWebsiteSource(true);

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.list_source);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dialog=new SpotsDialog(this);
        LoadWebsiteSource(false);


    }

    private void LoadWebsiteSource(boolean isRefreshed) {

        if(!isRefreshed)
        {
            String cache=Paper.book().read("cache");
            if(cache!=null && !cache.isEmpty())
            {
                WebSite website=new Gson().fromJson(cache,WebSite.class);
                adapter=new ListSourceAdapter(getBaseContext(),website);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
            else
                dialog.show();;
            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter=new ListSourceAdapter(getBaseContext(),response.body());
                    adapter.notifyDataSetChanged();;
                    recyclerView.setAdapter(adapter);



                    Paper.book().write("cache",new Gson().toJson(response.body()));

                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });
        }

        else
        {
            dialog.show();;
            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                    adapter=new ListSourceAdapter(getBaseContext(),response.body());
                    adapter.notifyDataSetChanged();;
                    recyclerView.setAdapter(adapter);



                    Paper.book().write("cache",new Gson().toJson(response.body()));

                    swipeLayout.setRefreshing(false);

                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t) {

                }
            });


        }

    }
}

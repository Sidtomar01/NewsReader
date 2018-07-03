package com.example.siddharth.newsreader;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.siddharth.newsreader.Adapter.ListNewsAdapter;
import com.example.siddharth.newsreader.Comman.Comman;
import com.example.siddharth.newsreader.Interface.NewsService;
import com.example.siddharth.newsreader.Model.Article;
import com.example.siddharth.newsreader.Model.News;
import com.example.siddharth.newsreader.Model.Source;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNews extends AppCompatActivity {


    KenBurnsView kbv;
    SpotsDialog dialog;
    NewsService mService;
    TextView top_author,top_title;
    SwipeRefreshLayout swipeRefreshLayout;
    String source="",sortBy="",WebHotURL="";
    ListNewsAdapter adapter;
    RecyclerView lstNews;

    RecyclerView.LayoutManager layoutManager;
    DiagonalLayout diagonalLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        mService= Comman.getNewsService();
        dialog= new SpotsDialog(this);

       swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(source,true);
            }
        });


        diagonalLayout= (DiagonalLayout) findViewById(R.id.diagonallayout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent detail=new Intent(getBaseContext(),DetailActivity.class);
                detail.putExtra("webURL",WebHotURL);
                startActivity(detail);


            }
        });


        kbv= (KenBurnsView) findViewById(R.id.top_image);
     //   top_author= (TextView) findViewById(R.id.topAuthor);
        top_title= (TextView) findViewById(R.id.toptitle);


        lstNews= (RecyclerView) findViewById(R.id.lst_news);
        lstNews.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        lstNews.setLayoutManager(layoutManager);



        //Imtent

        if(getIntent() !=null)
        {
            source=getIntent().getStringExtra("source");
            sortBy=getIntent().getStringExtra("sortBy");

            if(!source.isEmpty()  && !sortBy.isEmpty())
            {

                loadNews(source,false);
            }



        }




    }

    private void loadNews(String source, boolean isRefreshed) {

        if(!isRefreshed)
        {
            dialog.show();;
            mService.getNewestArticle(Comman.getApiUrl(source,Comman.API_KEY)).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    dialog.dismiss();;
                    //first article
                    Picasso.with(getBaseContext()).load(response.body().getArticles().get(0).getUrlToImage()).into(kbv);

                    top_title.setText(response.body().getArticles().get(0).getTitle());

                  //  top_author.setText(response.body().getArticles().get(0).getAuthor());
                    WebHotURL=response.body().getArticles().get(0).getUrl();


                    //Load remain Article

                    List<Article> removefirstItem=response.body().getArticles();
                    removefirstItem.remove(0);
                    adapter=new ListNewsAdapter(removefirstItem,getBaseContext());
                    adapter.notifyDataSetChanged();
                    lstNews.setAdapter(adapter);


                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });

        }
        else
        {
            dialog.show();;
            mService.getNewestArticle(Comman.getApiUrl(source,Comman.API_KEY)).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    dialog.dismiss();;
                    //first article
                    Picasso.with(getBaseContext()).load(response.body().getArticles().get(0).getUrlToImage()).into(kbv);

                    top_title.setText(response.body().getArticles().get(0).getTitle());

                    top_author.setText(response.body().getArticles().get(0).getAuthor());
                    WebHotURL=response.body().getArticles().get(0).getUrl();


                    //Load remain Article

                    List<Article> removefirstItem=response.body().getArticles();
                    removefirstItem.remove(0);
                    adapter=new ListNewsAdapter(removefirstItem,getBaseContext());
                    adapter.notifyDataSetChanged();
                    lstNews.setAdapter(adapter);


                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });
            swipeRefreshLayout.setRefreshing(false);
        }

    }
}

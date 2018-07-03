package com.example.siddharth.newsreader.Interface;

import com.example.siddharth.newsreader.Model.News;
import com.example.siddharth.newsreader.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Siddharth on 18-12-2017.
 */

public interface NewsService {

    @GET("v1/sources?language=en")
    Call<WebSite> getSources();
    @GET
    Call<News>getNewestArticle (@Url String url);
}

package com.example.siddharth.newsreader.Comman;

import com.example.siddharth.newsreader.Interface.IconBetterIdeaService;
import com.example.siddharth.newsreader.Interface.NewsService;
import com.example.siddharth.newsreader.Model.IconBetterIdea;
import com.example.siddharth.newsreader.Remote.IconBetterIdeaClient;
import com.example.siddharth.newsreader.Remote.RetrofitClient;

/**
 * Created by Siddharth on 18-12-2017.
 */

public class Comman {
    private static final String BASE_URL="https://newsapi.org/";

    public static final String API_KEY="a760512536d548eabd9776fd46475b9b";




    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);


    }
    public static IconBetterIdeaService getIconService()
    {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);


    }


   /// https://newsapi.org/v1/articles?source=the-verge&apiKey=a760512536d548eabd9776fd46475b9b

    public static  String getApiUrl(String source  ,String ApiKey)
    {
        StringBuilder apiUrl=new StringBuilder("https://newsapi.org/v1/articles?source=");
return apiUrl.append(source).append("&apiKey=").append(ApiKey).toString();

    }



}

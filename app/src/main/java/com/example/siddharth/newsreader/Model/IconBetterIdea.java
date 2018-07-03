package com.example.siddharth.newsreader.Model;

import java.util.List;

import java.util.List;

/**
 * Created by Siddharth on 18-12-2017.
 */

public class IconBetterIdea {


    private String url;
    private List<Icon> icons;

    public IconBetterIdea(String url, List<Icon> icons) {
        this.url = url;
        this.icons = icons;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }
}

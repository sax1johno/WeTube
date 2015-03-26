package com.gmail.markdevw.wetube.api;

import com.gmail.markdevw.wetube.api.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 3/26/2015.
 */
public class DataSource {
    private String API_KEY = "AIzaSyDqalWrQoW2KoHoYLoyKl-FhncIQd2C3Rk";
    private List<VideoItem> videos;

    public DataSource(){
        videos = new ArrayList<>();
    }
}

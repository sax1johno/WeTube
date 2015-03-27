package com.gmail.markdevw.wetube.api;

import android.content.Context;
import android.util.Log;

import com.gmail.markdevw.wetube.R;
import com.gmail.markdevw.wetube.api.model.VideoItem;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 3/26/2015.
 */
public class DataSource {
    private String API_KEY = "AIzaSyDqalWrQoW2KoHoYLoyKl-FhncIQd2C3Rk";
    private List<VideoItem> videos;
    private YouTube youtube;
    private YouTube.Search.List query;

    private final long NUMBER_OF_VIDEOS_RETURNED = 20;

    public DataSource(Context context){
        videos = new ArrayList<VideoItem>();
        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) throws IOException {}
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try{
            query = youtube.search().list("id,snippet");
            query.setKey(API_KEY);
            query.setType("video");
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
            query.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
        }catch(IOException e){
            Log.d("YC", "Could not initialize: " + e);
        }
    }

    public List<VideoItem> getVideos(){
        return videos;
    }

    public void searchForVideos(String searchTerms){
        query.setQ(searchTerms);
        try{
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            List<VideoItem> items = new ArrayList<VideoItem>();
            for(SearchResult result:results){
                VideoItem item = new VideoItem();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());
                videos.add(item);
            }
        }catch(IOException e){
            Log.d("YC", "Could not search: "+e);
        }
    }

}

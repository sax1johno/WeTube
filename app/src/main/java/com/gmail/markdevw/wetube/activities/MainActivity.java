package com.gmail.markdevw.wetube.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gmail.markdevw.wetube.R;
import com.gmail.markdevw.wetube.WeTubeApplication;
import com.gmail.markdevw.wetube.adapters.VideoItemAdapter;
import com.gmail.markdevw.wetube.api.model.VideoItem;
import com.gmail.markdevw.wetube.fragments.SearchBarFragment;
import com.gmail.markdevw.wetube.fragments.VideoListFragment;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

/**
 * Created by Mark on 3/24/2015.
 */

public class MainActivity extends ActionBarActivity implements SearchBarFragment.Delegate, VideoListFragment.Delegate, YouTubePlayer.OnInitializedListener{

    Handler handler;
    Toolbar toolbar;
    FrameLayout search;
    FrameLayout list;
    FrameLayout player;
    YouTubePlayerFragment playerFragment;
    YouTubePlayer youTubePlayer;
    String currentVideo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (FrameLayout) findViewById(R.id.fl_activity_search);
        list = (FrameLayout) findViewById(R.id.fl_activity_video_list);
        //player = (FrameLayout) findViewById(R.id.fl_activity_video_player);

        playerFragment = (YouTubePlayerFragment)getFragmentManager()
                .findFragmentById(R.id.youtubeplayerfragment);
        playerFragment.initialize(WeTubeApplication.getSharedDataSource().getAPI_KEY(), this);

        toolbar = (Toolbar) findViewById(R.id.tb_activity_main);
        setSupportActionBar(toolbar);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.fl_activity_search, new SearchBarFragment(), "Search")
                .commit();

        getFragmentManager()
                .beginTransaction()
                .add(R.id.fl_activity_video_list, new VideoListFragment(), "Video")
                .commit();

        handler = new Handler();
    }

    @Override
    public void onSearchButtonClicked(SearchBarFragment searchBarFragment, EditText searchBox) {
        final String search = WeTubeApplication.getSharedDataSource().getCurrentSearch();

        if(search.isEmpty()){
            Toast.makeText(this, "Enter a search keyword first", Toast.LENGTH_LONG).show();
        }else{
            new Thread(){
                public void run(){
                    WeTubeApplication.getSharedDataSource().searchForVideos(search);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Fragment f = getFragmentManager().findFragmentByTag("Video");
                            VideoListFragment vlf = (VideoListFragment)f;
                            vlf.getVideoItemAdapter().notifyDataSetChanged();
                            vlf.getRecyclerView().scrollToPosition(0);
                            toolbar.setTitle("Page: " + WeTubeApplication.getSharedDataSource().getCurrentPage());
                        }
                    });
                }
            }.start();
        }
    }

    @Override
    public void onPrevPageButtonClicked(SearchBarFragment searchBarFragment, EditText searchBox) {
        final String search = WeTubeApplication.getSharedDataSource().getCurrentSearch();

        new Thread(){
            public void run(){
                WeTubeApplication.getSharedDataSource().searchForVideos(search, WeTubeApplication.getSharedDataSource().getPrevPageToken());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Fragment f = getFragmentManager().findFragmentByTag("Video");
                        VideoListFragment vlf = (VideoListFragment)f;
                        vlf.getVideoItemAdapter().notifyDataSetChanged();
                        vlf.getRecyclerView().scrollToPosition(0);
                        toolbar.setTitle("Page: " + WeTubeApplication.getSharedDataSource().getCurrentPage());
                    }
                });
            }
        }.start();
    }

    @Override
    public void onNextPageButtonClicked(SearchBarFragment searchBarFragment, EditText searchBox) {
        final String search = WeTubeApplication.getSharedDataSource().getCurrentSearch();

        new Thread(){
            public void run(){
                WeTubeApplication.getSharedDataSource().searchForVideos(search, WeTubeApplication.getSharedDataSource().getNextPageToken());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Fragment f = getFragmentManager().findFragmentByTag("Video");
                        VideoListFragment vlf = (VideoListFragment)f;
                        vlf.getVideoItemAdapter().notifyDataSetChanged();
                        vlf.getRecyclerView().scrollToPosition(0);
                        toolbar.setTitle("Page: " + WeTubeApplication.getSharedDataSource().getCurrentPage());
                    }
                });
            }
        }.start();
    }

    @Override
    public void onVideoItemClicked(VideoItemAdapter itemAdapter, VideoItem videoItem) {
        currentVideo = videoItem.getId();

        search.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
        youTubePlayer.loadVideo(videoItem.getId());
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        this.youTubePlayer = youTubePlayer;
        this.youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }
}

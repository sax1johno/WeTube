package com.gmail.markdevw.wetube.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gmail.markdevw.wetube.R;
import com.gmail.markdevw.wetube.WeTubeApplication;
import com.gmail.markdevw.wetube.adapters.VideoItemAdapter;

/**
 * Created by Mark on 3/24/2015.
 */

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    Button searchButton;
    EditText searchBox;
    ImageButton prevPage;
    ImageButton nextPage;
    RecyclerView recyclerView;
    VideoItemAdapter videoItemAdapter;
    Handler handler;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchButton = (Button) findViewById(R.id.activity_main_search_button);
        searchBox = (EditText) findViewById(R.id.activity_main_search_video);
        prevPage = (ImageButton) findViewById(R.id.activity_main_prev_page);
        nextPage = (ImageButton) findViewById(R.id.activity_main_next_page);
        toolbar = (Toolbar) findViewById(R.id.tb_activity_main);

        setSupportActionBar(toolbar);

        prevPage.setOnClickListener(this);
        nextPage.setOnClickListener(this);
        searchButton.setOnClickListener(this);

        videoItemAdapter = new VideoItemAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.rv_activity_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(videoItemAdapter);

        handler = new Handler();


    }

    @Override
    public void onClick(View v) {
        final String search = searchBox.getText().toString();

        switch(v.getId()){
            case R.id.activity_main_prev_page:
                new Thread(){
                    public void run(){
                        WeTubeApplication.getSharedDataSource().searchForVideos(search, WeTubeApplication.getSharedDataSource().getPrevPageToken());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                videoItemAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(0);
                                toolbar.setTitle("Page: " + WeTubeApplication.getSharedDataSource().getCurrentPage());
                            }
                        });
                    }
                }.start();
                break;
            case R.id.activity_main_next_page:
                new Thread(){
                    public void run(){
                        WeTubeApplication.getSharedDataSource().searchForVideos(search, WeTubeApplication.getSharedDataSource().getNextPageToken());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                videoItemAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(0);
                                toolbar.setTitle("Page: " + WeTubeApplication.getSharedDataSource().getCurrentPage());
                            }
                        });
                    }
                }.start();
                break;
            case R.id.activity_main_search_button:
                if(search.isEmpty()){
                    Toast.makeText(this, "Enter a search keyword first", Toast.LENGTH_LONG).show();
                }else{
                    new Thread(){
                        public void run(){
                            WeTubeApplication.getSharedDataSource().searchForVideos(search);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    videoItemAdapter.notifyDataSetChanged();
                                    prevPage.setVisibility(View.VISIBLE);
                                    nextPage.setVisibility(View.VISIBLE);
                                    toolbar.setTitle("Page: " + WeTubeApplication.getSharedDataSource().getCurrentPage());
                                }
                            });
                        }
                    }.start();
                }
                break;
        }
    }
}

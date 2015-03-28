package com.gmail.markdevw.wetube.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.markdevw.wetube.R;
import com.gmail.markdevw.wetube.WeTubeApplication;
import com.gmail.markdevw.wetube.adapters.VideoItemAdapter;
import com.gmail.markdevw.wetube.api.model.VideoItem;

/**
 * Created by Mark on 3/27/2015.
 */
public class VideoListFragment extends Fragment implements VideoItemAdapter.Delegate {

    RecyclerView recyclerView;
    VideoItemAdapter videoItemAdapter;
    Delegate listener;

    public static interface Delegate {
        public void onVideoItemClicked(VideoItemAdapter itemAdapter, VideoItem videoItem);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (Delegate) activity;
        }catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnVideoItemClicked");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_video_list, container, false);

        videoItemAdapter = new VideoItemAdapter();
        videoItemAdapter.setDelegate(this);

        recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_fragment_video_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(WeTubeApplication.getSharedInstance()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(videoItemAdapter);

        return inflate;
    }

    public VideoItemAdapter getVideoItemAdapter() { return videoItemAdapter; }
    public RecyclerView getRecyclerView() { return recyclerView; }

    @Override
    public void onItemClicked(VideoItemAdapter itemAdapter, VideoItem videoItem) {
        listener.onVideoItemClicked(itemAdapter, videoItem);
    }
}

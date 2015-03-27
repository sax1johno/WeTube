package com.gmail.markdevw.wetube.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.markdevw.wetube.R;
import com.gmail.markdevw.wetube.WeTubeApplication;
import com.gmail.markdevw.wetube.api.DataSource;
import com.gmail.markdevw.wetube.api.model.VideoItem;
import com.squareup.picasso.Picasso;

/**
 * Created by Mark on 3/26/2015.
 */
public class VideoItemAdapter extends RecyclerView.Adapter<VideoItemAdapter.ItemAdapterViewHolder> {
    @Override
    public ItemAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item, viewGroup, false);
        return new ItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ItemAdapterViewHolder itemAdapterViewHolder, int index) {
        DataSource sharedDataSource = WeTubeApplication.getSharedDataSource();
        itemAdapterViewHolder.update(sharedDataSource.getVideos().get(index));
    }

    @Override
    public int getItemCount() {
        return WeTubeApplication.getSharedDataSource().getVideos().size();
    }

    class ItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail;
        TextView title;
        TextView description;
        VideoItem videoItem;

        public ItemAdapterViewHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
            title = (TextView) itemView.findViewById(R.id.video_title);
            description = (TextView) itemView.findViewById(R.id.video_description);

            itemView.setOnClickListener(this);
        }

        void update(VideoItem videoItem) {
            this.videoItem = videoItem;

            title.setText(videoItem.getTitle());
            description.setText(videoItem.getDescription());
            Picasso.with(WeTubeApplication.getSharedInstance()).load(videoItem.getThumbnailURL()).into(thumbnail);
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){

            }
        }
    }


}

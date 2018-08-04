package com.example.user.smartfoody.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.smartfoody.R;
import com.example.user.smartfoody.View.Video;
import com.example.user.smartfoody.View.ViewVideo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 12/11/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ViewVideo context;
    private List<Video> videoList;
    private OnItemClickListener mlistener;

    //setup event itemClick for RecyclerView
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlistener = listener;
    }

    public VideoAdapter(ViewVideo mcontext, List<Video> videoList) {
        this.context = mcontext;
        this.videoList = videoList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.videoadapter, parent, false);
        return new ViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Video video = videoList.get(position);
        holder.ten_vd.setText(video.getTitle());
        Picasso.with(context).load(video.getThumnail()).into(holder.img_vd);
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_vd;
        public TextView ten_vd;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            img_vd = (ImageView)itemView.findViewById(R.id.imgvideo);
            ten_vd = (TextView)itemView.findViewById(R.id.txttitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    context.VIDEO_ID = context.listarray.get(position).getVideoid().toString();
                    context.player.loadVideo(context.VIDEO_ID);
                    context.player.play();
                    videoList.remove(position);
                    context.adapter.notifyDataSetChanged();
                }
            });
        }
    }
}

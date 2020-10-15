package com.status.story.saver.downloader.free.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.status.story.saver.downloader.free.R;
import com.jignesh.jndroid.utils.OnSingleClickListener;
import com.jignesh.jndroid.utils.RVClickListener;

import java.util.ArrayList;

public class ThumbAdapter extends BaseAdapter {

    // Global Variables
    private RVClickListener clickListener;
    private Context mContext;
    private ArrayList<String> photoList = new ArrayList<>();
    LayoutInflater inflater;

    public ThumbAdapter(Context mContext, ArrayList<String> photoList, RVClickListener
            clickListener) {
        this.mContext = mContext;
        this.clickListener = clickListener;
        this.photoList = photoList;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public String getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (getCount() > 0) {
            return getCount();
        } else {
            return super.getViewTypeCount();
        }
    }

    class MyViewHolder {
        RoundedImageView icon;
        ImageView iv_play;

        MyViewHolder(View v) {
            icon = v.findViewById(R.id.iv_category_icon);
            iv_play = v.findViewById(R.id.iv_play);
        }

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        View v = view;
        MyViewHolder holder = null;

        if (v == null) {
            v = inflater.inflate(R.layout.card_thumb, parent, false);
            holder = new MyViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (MyViewHolder) v.getTag();
        }

        String path = photoList.get(position);
        int thumb;
        if (path.endsWith(".mp4")) {
            thumb = R.drawable.vid_thumb;
            holder.iv_play.setSelected(false);
        } else {
            thumb = R.drawable.img_thumb;
            holder.iv_play.setSelected(true);
        }

        RequestOptions simpleOptions = new RequestOptions()
                .centerCrop()
                .placeholder(thumb)
                .error(thumb)
                .override(250, 250)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE
                );

        final MyViewHolder finalHolder = holder;
        Glide.with(mContext)
                .load(path)
               .apply(simpleOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        finalHolder.iv_play.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource
                            , boolean isFirstResource) {
                        finalHolder.iv_play.setVisibility(View.VISIBLE);

                        return false;
                    }
                })
                .into(holder.icon);

        holder.icon.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                clickListener.onItemClick(position);
            }
        });
        return v;
    }

}

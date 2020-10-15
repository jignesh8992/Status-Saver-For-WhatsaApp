package com.status.story.saver.downloader.free.adapter;


import android.app.Activity;
import android.content.Context;

import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jignesh.jndroid.JndroidApplication;
import com.jignesh.jndroid.widgets.TouchImageView;
import com.status.story.saver.downloader.free.R;

import java.util.ArrayList;


public class PreviewPhotoAdapter extends PagerAdapter {

    // Global Variables
    private Activity _activity;
    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;

    // constructor
    public PreviewPhotoAdapter(Activity activity,
                               ArrayList<String> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.card_preview_image, container, false);
        imgDisplay = viewLayout.findViewById(R.id.imgDisplay);
        String path = _imagePaths.get(position);

      //  Glide.with(_activity).loadFBInterstitialAd(path).into(imgDisplay);
        // imgDisplay.setImageURI(Uri.parse(path));

        JndroidApplication.loadFromSdcard(path,imgDisplay);
        container.addView(viewLayout);
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }

    public void removeView(int position) {
        _imagePaths.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}

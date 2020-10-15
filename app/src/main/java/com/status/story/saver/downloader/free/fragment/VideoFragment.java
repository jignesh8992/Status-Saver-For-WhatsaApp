package com.status.story.saver.downloader.free.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.jignesh.jndroid.utils.FileHelper;
import com.jignesh.jndroid.utils.RVClickListener;
import com.jignesh.jndroid.utils.SPHelper;
import com.jignesh.jndroid.utils.SortHelper;
import com.status.story.saver.downloader.free.BaseFragment;
import com.status.story.saver.downloader.free.R;
import com.status.story.saver.downloader.free.activity.VideoPreviewActivity;
import com.status.story.saver.downloader.free.adapter.ThumbAdapter;
import com.status.story.saver.downloader.free.ads.AdsConstants;
import com.status.story.saver.downloader.free.utils.Constant;
import com.status.story.saver.downloader.free.utils.LoadStatusHelper;

import java.io.File;
import java.util.ArrayList;


public class VideoFragment extends BaseFragment {

    private String TAG = VideoFragment.class.getSimpleName();

    // Global Variables
    private ArrayList<String> photoList = new ArrayList<>();
    // Adapter
    private ThumbAdapter adapter;
    // Views
    private GridView grid_view;
    private TextView tvNoStatus;
    private SpinKitView spinKitView;
    private int clickPosition = 0;
    private LinearLayout linear_status;


    @Override
    public void onInterstitialAdClosed() {
        try {
            loadInterstitialAd();
        } catch (Exception e) {
            Log.e(TAG_ADS, "Ads_Interstitial_Load_Error: " + e.getMessage());
        }
        startPreview();
        // perform your action

    }


    @Override
    public int getLayout() {
        return R.layout.fragment_videos;
    }

    @Override
    public Activity getContext() {
        return getActivity();
    }

    @Override
    public void initViews(View view) {
        grid_view = view.findViewById(R.id.grid_view);
        linear_status = view.findViewById(R.id.linear_status);
        tvNoStatus = view.findViewById(R.id.no_data);
        spinKitView = view.findViewById(R.id.spinKitView);
    }

    @Override
    public void initAds() {

    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        try {
            loadInterstitialAd();
        } catch (Exception e) {
            Log.e(TAG_ADS, "Ads_Interstitial_Load_Error: " + e.getMessage());
        }
    }

    @Override
    public void initActions() {

    }


    @Override
    public void onResume() {
        super.onResume();
        new LoadVideos().execute();
    }

    private class LoadVideos extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinKitView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            ArrayList<File> list = new ArrayList<>();
            if (Constant.TYPE_STATUS == Constant.TYPE_WHATSAPP_STATUS) {
                list = LoadStatusHelper.getAllStatuses();
            } else {
                String folder_path = Constant.getVideoPath2(mContext);
                list = FileHelper.loadFiles(folder_path);
            }

            ArrayList<String> temp = SortHelper.sortVideos(list);
            if (photoList.isEmpty()) {
                photoList = temp;
                return true;
            } else {
                if (photoList.size() == temp.size()) {
                    return false;
                } else {
                    photoList = temp;
                    return true;
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean isUpdate) {
            super.onPostExecute(isUpdate);
            if (isUpdate) {
                if (photoList.size() > 0) {


                    linear_status.setVisibility(View.GONE);
                    grid_view.setVisibility(View.VISIBLE);
                    adapter = new ThumbAdapter(mContext, photoList, new RVClickListener() {
                        @Override
                        public void onItemClick(int position) {

                            try {
                                clickPosition = position;
                                int adsCount = sp.getInt(SPHelper.ADS_STATUS_VIEW_COUNTER);
                                adsCount = adsCount + 1;
                                sp.save(SPHelper.ADS_STATUS_VIEW_COUNTER, adsCount);
                                if (adsCount >= AdsConstants.STATUS_CLICK) {

                                    if (isAdLoaded()) {
                                        sp.save(SPHelper.ADS_STATUS_VIEW_COUNTER, 0);
                                        showAd();
                                    } else {
                                        startPreview();
                                    }
                                } else {
                                    startPreview();
                                }
                            } catch (Exception e) {
                                Toast.makeText(mContext, mContext.getString(R.string.went_wrong),
                                        Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                    grid_view.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    if (Constant.TYPE_STATUS == Constant.TYPE_WHATSAPP_STATUS) {
                        tvNoStatus.setText(R.string.no_video);
                    } else {
                        tvNoStatus.setText(R.string.no_saved_video);
                    }
                    linear_status.setVisibility(View.VISIBLE);
                    grid_view.setVisibility(View.GONE);
                }
            }

            spinKitView.setVisibility(View.GONE);
        }

    }

    private void startPreview() {
        Intent intent = new Intent(mContext, VideoPreviewActivity.class);
        intent.putExtra("path", photoList.get(clickPosition));
        intent.putExtra("position", clickPosition);
        intent.putExtra("arraylist", photoList);
        startActivity(intent);
    }
}

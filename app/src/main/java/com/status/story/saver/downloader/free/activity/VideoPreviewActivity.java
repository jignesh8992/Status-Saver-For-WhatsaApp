package com.status.story.saver.downloader.free.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.jignesh.jndroid.utils.DialogHelper;
import com.jignesh.jndroid.utils.FileHelper;
import com.jignesh.jndroid.utils.OnSingleClickListener;
import com.jignesh.jndroid.utils.ShareHelper;
import com.status.story.saver.downloader.free.BaseActivity;
import com.status.story.saver.downloader.free.R;
import com.status.story.saver.downloader.free.adapter.PreviewVideoAdapter;
import com.status.story.saver.downloader.free.ads.BannerAdsHelper;
import com.status.story.saver.downloader.free.inapp.AdsManager;
import com.status.story.saver.downloader.free.utils.Constant;
import com.status.story.saver.downloader.free.utils.LoadStatusHelper;

import java.io.File;
import java.util.ArrayList;


public class VideoPreviewActivity extends BaseActivity {

    String TAG = VideoPreviewActivity.class.getSimpleName();
    // Global Variables
    private String path;
    private String destPath;
    // Adapter
    private PreviewVideoAdapter adapter;
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    // Views
    private ViewPager viewPager;
    private ImageView imgBack;
    private ImageView ivShare;
    private ImageView ivSaveDelete;
    private int current_position;
    private ArrayList<String> video_list = new ArrayList<>();
    private int stop_position = 0;
    private boolean isPlaying = true;
    private int mSeekPosition;
    private TextView tvTitle;
    private ImageView ivRepost;
    private SpinKitView progressBar;


    @Override
    public void onInterstitialAdClosed() {
        // perform your action
        copyStatus();
        try {
            loadInterstitialAd();
        } catch (Exception e) {
            Log.e(TAG_ADS, "Ads_Interstitial_Load_Error: " + e.getMessage());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        getWindow().setFlags(1024, 1024);
    }

    @Override
    protected Activity getContext() {
        return VideoPreviewActivity.this;
    }

    @Override
    public void initViews() {
        viewPager = findViewById(R.id.pager);
        imgBack = findViewById(R.id.iv_back);
        ivRepost = findViewById(R.id.iv_repost);
        ivShare = findViewById(R.id.iv_share);
        tvTitle = findViewById(R.id.tv_title);
        ivSaveDelete = findViewById(R.id.iv_save_delete);
        progressBar = findViewById(R.id.progressBar);
        if (Constant.TYPE_STATUS == Constant.TYPE_SAVED_STATUS) {
            ivSaveDelete.setImageResource(R.drawable.btn_delete);
        } else {
            ivSaveDelete.setImageResource(R.drawable.selector_download);
        }
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        tvTitle.setTypeface(typeface);
    }

    @Override
    public void initAds() {
        try {
            if (new AdsManager(mContext).isNeedToShowAds()) {
                BannerAdsHelper.getInstance().loadBanner(mContext);
                loadInterstitialAd();
            } else {
                manageRemoveAds();
            }
        } catch (Exception e) {
            Log.e(TAG_ADS, "Ads_Banner_Load_Error: " + e.getMessage());
        }
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {

        Intent i = getIntent();
        current_position = i.getIntExtra("position", 0);
        video_list = i.getStringArrayListExtra("arraylist");

        if (!video_list.isEmpty()) {
            try {
                path = video_list.get(current_position);
                adapter = new PreviewVideoAdapter(mContext, video_list, current_position);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(current_position);
            } catch (Exception ignored) {
                Toast.makeText(mContext, mContext.getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        } else {
            onBackPressed();
            Toast.makeText(mContext, mContext.getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initActions() {
        imgBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                onBackPressed();
            }
        });
        ivSaveDelete.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                destPath = Constant.getVideoPath(mContext);
                String fileName = FileHelper.getFileName(path);
                String oPath = destPath + File.separator + fileName;
                File file = new File(oPath);

                if (Constant.TYPE_STATUS == Constant.TYPE_SAVED_STATUS) {
                    DialogHelper.showAlert(mContext, new DialogHelper.onPositive() {
                        @Override
                        public void onYes() {
                            deleteImage(current_position);
                        }
                    });
                } else {
                    if (file.exists()) {

                        Toast.makeText(mContext, mContext.getString(R.string.status_exist), Toast.LENGTH_SHORT).show();


                    } else {

                        try {
                            if (new AdsManager(mContext).isNeedToShowAds() && isAdLoaded()) {
                                showAd();
                            } else {
                                // perform your action
                                copyStatus();
                            }
                        } catch (Exception e) {
                            Toast.makeText(mContext, mContext.getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            }
        });
        ivRepost.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {


                Log.i(TAG, "onSingleClick: " + path);
                if (path.contains("/.Statuses/")) {

                    new CopyTempTask(true, path).execute();
                } else {
                    ShareHelper.shareVideo(mContext, path, ShareHelper.WHATSAPP);
                }


            }
        });
        ivShare.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                if (path.contains("/.Statuses/")) {

                    new CopyTempTask(false, path).execute();
                } else {
                    ShareHelper.shareVideo(mContext, path);
                }

            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                current_position = position;
                path = video_list.get(position);
                adapter.setPosition(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void copyStatus() {
        new CopyTask(path, destPath, new OnFinished() {
            @Override
            public void onFinish(String oPath) {
                FileHelper.refreshGallery(mContext, oPath);

                Toast.makeText(mContext, mContext.getString(R.string.save_video), Toast.LENGTH_SHORT).show();


            }
        }).execute();
    }


    private class CopyTask extends AsyncTask<Void, Void, Void> {
        String fromPath;
        String toPath;
        OnFinished onFinished;
        private String oPath;

        public CopyTask(String fromPath, String toPath, OnFinished onFinished) {
            this.fromPath = fromPath;
            this.toPath = toPath;
            this.onFinished = onFinished;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            oPath = LoadStatusHelper.copyFile(fromPath, toPath);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            onFinished.onFinish(oPath);
        }


    }

    private class CopyTempTask extends AsyncTask<Void, Void, Void> {
        String fromPath;
        Boolean toWhatsApp;
        String oPath;

        public CopyTempTask(Boolean toWhatsApp, String fromPath) {
            this.fromPath = fromPath;
            this.toWhatsApp = toWhatsApp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String tempPath = Constant.getTempVideoPath(mContext);
            String fileName = FileHelper.getFileName(path);
            oPath = tempPath + File.separator + fileName;
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            File file = new File(oPath);
            if (!file.exists()) {
                oPath = LoadStatusHelper.copyFile(fromPath, Constant.getTempVideoPath(mContext));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            Log.i(TAG, "onPostExecute: " + oPath);
            if (toWhatsApp) {
                ShareHelper.shareVideo(mContext, oPath, ShareHelper.WHATSAPP);
            } else {
                ShareHelper.shareVideo(mContext, oPath);
            }
        }


    }

    public interface OnFinished {
        void onFinish(String oPath);
    }

    private void deleteImage(int position) {
        path = video_list.get(position);
        current_position = position;
        FileHelper.delete(path);
        Toast.makeText(mContext, mContext.getString(R.string.del_video), Toast.LENGTH_SHORT).show();
        adapter.removeView(position);
        if (position == adapter.getCount()) position--;
        viewPager.setCurrentItem(position);
        if (adapter.getCount() < 1) {
            finish();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            Log.d(TAG, "onResume isPlaying=" + isPlaying);
            adapter.setSeekPosition(mSeekPosition);
            Log.d(TAG, "onResume mSeekPosition=" + mSeekPosition);
            adapter.setPosition(current_position, isPlaying);
        }
        if (!new AdsManager(mContext).isNeedToShowAds()) {
            manageRemoveAds();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");

        try {
            if (adapter.getVideoView() != null) {
                adapter.getVideoView().pause();
                mSeekPosition = adapter.getVideoView().getCurrentPosition();
                Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
                isPlaying = false;
                Log.d(TAG, "onPause isPlaying=" + mSeekPosition);

            } else {
                isPlaying = false;
            }
        } catch (Exception e) {
            Log.d(TAG, "onPause" + e.toString());

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter.getVideoView() != null) {
            outState.putInt(SEEK_POSITION_KEY, adapter.getSeekPosition());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        int mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        adapter.setSeekPosition(mSeekPosition);
        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }

    @Override
    protected void onDestroy() {
        Glide.with(getApplicationContext()).pauseRequests();
        super.onDestroy();
    }


    private void manageRemoveAds() {
        findViewById(R.id.rel_banner_ad).setVisibility(View.GONE);
    }
}

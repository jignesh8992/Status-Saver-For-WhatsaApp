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
import com.status.story.saver.downloader.free.adapter.PreviewPhotoAdapter;
import com.status.story.saver.downloader.free.ads.BannerAdsHelper;
import com.status.story.saver.downloader.free.ads.NativeBannerAdsHelper;
import com.status.story.saver.downloader.free.inapp.AdsManager;
import com.status.story.saver.downloader.free.utils.Constant;
import com.status.story.saver.downloader.free.utils.LoadStatusHelper;

import java.io.File;
import java.util.ArrayList;

public class PhotoPreviewActivity extends BaseActivity {


    private String TAG = PhotoPreviewActivity.class.getSimpleName();

    // Global Variables
    private String path;
    private String destPath;
    private int current_position;
    private ArrayList<String> photo_list = new ArrayList<>();
    // Adapter
    private PreviewPhotoAdapter adapter;
    // Views
    private ViewPager viewPager;
    private ImageView imgBack, ivShare, ivSaveDelete, ivRepost;
    private TextView tvTitle;
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
        return PhotoPreviewActivity.this;
    }

    @Override
    public void initViews() {
        viewPager = findViewById(R.id.pager);
        imgBack = findViewById(R.id.iv_back);
        ivRepost = findViewById(R.id.iv_repost);
        ivShare = findViewById(R.id.iv_share);
        ivSaveDelete = findViewById(R.id.iv_save_delete);
        progressBar = findViewById(R.id.progressBar);
        tvTitle = findViewById(R.id.tv_title);
        if (Constant.TYPE_STATUS == Constant.TYPE_SAVED_STATUS) {
            ivSaveDelete.setImageResource(R.drawable.btn_delete);
        } else {
            ivSaveDelete.setImageResource(R.drawable.selector_download);
        }
    }

    @Override
    public void initAds() {


        try {
            if (new AdsManager(mContext).isNeedToShowAds()) {
                BannerAdsHelper.getInstance().loadBanner(mContext);
                loadInterstitialAd();
            }else {
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
        photo_list = i.getStringArrayListExtra("arraylist");

        if (!photo_list.isEmpty()) {
            try {
                adapter = new PreviewPhotoAdapter(PhotoPreviewActivity.this, photo_list);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(current_position);
                path = photo_list.get(current_position);
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light"
                        + ".ttf");
                tvTitle.setTypeface(typeface);
            } catch (Exception ignored) {
                onBackPressed();
                Toast.makeText(mContext, mContext.getString(R.string.went_wrong),
                        Toast.LENGTH_SHORT).show();
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
                destPath = Constant.getPhotoPath(mContext);
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
                        Toast.makeText(mContext, mContext.getString(R.string.status_exist),
                                Toast.LENGTH_SHORT).show();
                    } else {


                        try {
                            if (new AdsManager(mContext).isNeedToShowAds() &&  isAdLoaded()) {
                                showAd();
                            } else {
                                // perform your action
                                copyStatus();
                            }
                        } catch (Exception e) {
                            Toast.makeText(mContext, mContext.getString(R.string.went_wrong),
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            }
        });

        ivRepost.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                ShareHelper.shareImage(mContext, path, ShareHelper.WHATSAPP);
            }
        });

        ivShare.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                ShareHelper.shareImage(mContext, path);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                current_position = position;
                path = photo_list.get(position);
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
                Toast.makeText(mContext, mContext.getString(R.string.save_img),
                        Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }


    private class CopyTask extends AsyncTask<Void, Void, Void> {
        String fromPath;
        String toPath;
        String oPath;
        OnFinished onFinished;

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
            onFinished.onFinish(oPath);
            progressBar.setVisibility(View.GONE);
        }


    }

    public interface OnFinished {
        void onFinish(String path);
    }

    private void deleteImage(int position) {
        path = photo_list.get(position);
        current_position = position;
        FileHelper.delete(path);
        Toast.makeText(mContext, mContext.getString(R.string.del_img), Toast.LENGTH_SHORT).show();
        adapter.removeView(position);
        if (position == adapter.getCount()) position--;
        viewPager.setCurrentItem(position);
        if (adapter.getCount() < 1) {
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        Glide.with(getApplicationContext()).pauseRequests();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!new AdsManager(mContext).isNeedToShowAds()) {
            manageRemoveAds();
        }
    }


    private void manageRemoveAds() {
        findViewById(R.id.rel_banner_ad).setVisibility(View.GONE);
    }
}

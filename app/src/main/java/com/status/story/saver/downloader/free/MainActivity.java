package com.status.story.saver.downloader.free;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.google.android.material.snackbar.Snackbar;
import com.jignesh.jndroid.statusbar.StatusBarCompat;
import com.jignesh.jndroid.utils.AppHelper;
import com.jignesh.jndroid.utils.FileFormatHelper;
import com.jignesh.jndroid.utils.OnSingleClickListener;
import com.jignesh.jndroid.utils.Permission;
import com.jignesh.jndroid.utils.SPHelper;
import com.jignesh.jndroid.utils.SortHelper;
import com.makeramen.roundedimageview.RoundedImageView;
import com.status.story.saver.downloader.free.aboutus.utilities.EasyRateDialog;
import com.status.story.saver.downloader.free.activity.DirectChatActivity;
import com.status.story.saver.downloader.free.activity.HowUseActivity;
import com.status.story.saver.downloader.free.activity.PhotoPreviewActivity;
import com.status.story.saver.downloader.free.activity.SettingsActivity;
import com.status.story.saver.downloader.free.activity.StatusActivity;
import com.status.story.saver.downloader.free.activity.VideoPreviewActivity;
import com.status.story.saver.downloader.free.ads.AdsConstants;
import com.status.story.saver.downloader.free.ads.BannerAdsHelper;
import com.status.story.saver.downloader.free.inapp.AdsManager;
import com.status.story.saver.downloader.free.utils.ClickShrinkEffect;
import com.status.story.saver.downloader.free.utils.Constant;
import com.status.story.saver.downloader.free.utils.LoadStatusHelper;
import com.stepstone.apprating.listener.RatingDialogListener;
import com.stepstone.easy_feedback.components.FeedbackUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends BaseActivity implements RatingDialogListener {
    private String TAG = MainActivity.class.getSimpleName();
    // Required Permissions
    private String[] permissions = {Permission.READ_STORAGE, Permission.WRITE_STORAGE};
    // Global Variables
    private ImageView ivTitle;
    private RelativeLayout relativeHeader;
    private LinearLayout linearStart;
    private ImageView ivStart;
    private ImageView ivSaved;
    private LinearLayout linearWhtsappDirect;
    private ImageView ivWhatsappDirect;
    private LinearLayout linearMoreMenu;
    private ImageView ivShare;
    private ImageView ivRate;
    private ImageView ivSettings;
    private ImageView ivHowToUse;
    private LinearLayout linearRecentBullet;
    private ImageView ivBullet;
    private LinearLayout llRecentContainer;
    private RelativeLayout rl_recents;
    private RelativeLayout square1;
    private RoundedImageView ivStatus1;
    private ImageView ivThumb1;
    private RelativeLayout square2;
    private RoundedImageView ivStatus2;
    private ImageView ivThumb2;
    private RelativeLayout square3;
    private RoundedImageView ivStatus3;
    private ImageView ivThumb3;
    private RelativeLayout square4;
    private RoundedImageView ivStatus4;
    private ImageView ivThumb4;
    private RelativeLayout square5;
    private RoundedImageView ivStatus5;
    private ImageView ivThumb5;
    private RelativeLayout square6;
    private RoundedImageView ivStatus6;
    private ImageView ivThumb6;


    private ArrayList<RelativeLayout> recentStatuses = new ArrayList<>();
    private ArrayList<ImageView> recentStatusesImages = new ArrayList<>();
    private ArrayList<ImageView> recentStatusesThumb = new ArrayList<>();


    private ArrayList<String> photoList = new ArrayList<>();

    private int clickPosition;
    private int start_start = 0;
    private int start_saved = 1;
    private int start_recent = 2;
    private int start_direct_chat = 3;
    private int start_click = start_start;
    private boolean isActive;
    private boolean isFromRate = true;


    @Override
    public void onInterstitialAdClosed() {

        // perform your action
        try {
            loadInterstitialAd();
        } catch (Exception e) {
            Log.e(TAG, "Ads_Interstitial_Load_Error: " + e.getMessage());
        }

        if (start_click == start_saved || start_click == start_start) {
            checkPermissions();
        } else if (start_click == start_recent) {
            openRecentPreview(clickPosition);
        } else if (start_click == start_direct_chat) {
            startActivity(new Intent(mContext, DirectChatActivity.class));
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.translucentStatusBar(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initActions();


        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        Log.i("Date_Today:", thisDate);
        if (sp.get(SPHelper.APP_DATE, "null").equals("null")) {
            Log.i("Date_", "null");
            sp.save(SPHelper.APP_DATE, thisDate);
        }
        if (!sp.get(SPHelper.APP_DATE, null).equals(thisDate)) {
            Log.i("Date_Not_Match", sp.get(SPHelper.APP_DATE, null) + "!=" + thisDate);
            sp.save(SPHelper.APP_DATE, thisDate);
            AppUpdater appUpdater = new AppUpdater(mContext);
            appUpdater.setDisplay(Display.DIALOG);
            appUpdater.start();
        } else {
            Log.i("Date_Match", sp.get(SPHelper.APP_DATE, null) + "==" + thisDate);
        }

        int counter = sp.get(SPHelper.APP_EXIT_COUNTER, 0);
        if (!sp.get(SPHelper.APP_RATED, false) && counter == 2) {
            isFromRate = true;
            sp.save(SPHelper.APP_EXIT_COUNTER, counter + 1);
            EasyRateDialog.show(mContext);
        }


    }

    @Override
    protected Activity getContext() {
        return MainActivity.this;
    }

    @Override
    public void initViews() {

        ivTitle = findViewById(R.id.iv_title);
        relativeHeader = findViewById(R.id.relative_header);
        linearStart = findViewById(R.id.linear_start);
        ivStart = findViewById(R.id.iv_start);
        ivSaved = findViewById(R.id.iv_saved);
        linearWhtsappDirect = findViewById(R.id.linear_whtsapp_direct);
        ivWhatsappDirect = findViewById(R.id.iv_whatsapp_direct);
        linearMoreMenu = findViewById(R.id.linear_more_menu);
        ivShare = findViewById(R.id.iv_share);
        ivRate = findViewById(R.id.iv_rate);
        ivSettings = findViewById(R.id.iv_settings);
        ivHowToUse = findViewById(R.id.iv_how_to_use);
        linearRecentBullet = findViewById(R.id.linear_recent_bullet);
        ivBullet = findViewById(R.id.iv_bullet);
        llRecentContainer = findViewById(R.id.ll_recent_container);
        rl_recents = findViewById(R.id.rl_recents);
        square1 = findViewById(R.id.square_1);
        ivStatus1 = findViewById(R.id.iv_status_1);
        ivThumb1 = findViewById(R.id.iv_thumb_1);
        square2 = findViewById(R.id.square_2);
        ivStatus2 = findViewById(R.id.iv_status_2);
        ivThumb2 = findViewById(R.id.iv_thumb_2);
        square3 = findViewById(R.id.square_3);
        ivStatus3 = findViewById(R.id.iv_status_3);
        ivThumb3 = findViewById(R.id.iv_thumb_3);
        square4 = findViewById(R.id.square_4);
        ivStatus4 = findViewById(R.id.iv_status_4);
        ivThumb4 = findViewById(R.id.iv_thumb_4);
        square5 = findViewById(R.id.square_5);
        ivStatus5 = findViewById(R.id.iv_status_5);
        ivThumb5 = findViewById(R.id.iv_thumb_5);
        square6 = findViewById(R.id.square_6);
        ivStatus6 = findViewById(R.id.iv_status_6);
        ivThumb6 = findViewById(R.id.iv_thumb_6);

        recentStatuses.add(square1);
        recentStatuses.add(square2);
        recentStatuses.add(square3);
        recentStatuses.add(square4);
        recentStatuses.add(square5);
        recentStatuses.add(square6);
        recentStatusesImages.add(ivStatus1);
        recentStatusesImages.add(ivStatus2);
        recentStatusesImages.add(ivStatus3);
        recentStatusesImages.add(ivStatus4);
        recentStatusesImages.add(ivStatus5);
        recentStatusesImages.add(ivStatus6);
        recentStatusesThumb.add(ivThumb1);
        recentStatusesThumb.add(ivThumb2);
        recentStatusesThumb.add(ivThumb3);
        recentStatusesThumb.add(ivThumb4);
        recentStatusesThumb.add(ivThumb5);
        recentStatusesThumb.add(ivThumb6);


    }

    @Override
    public void initAds() {


        if (new AdsManager(mContext).isNeedToShowAds()) {
            if (!hasPermission(permissions)) {
                try {
                    BannerAdsHelper.getInstance().loadBanner(mContext);
                } catch (Exception e) {
                    Log.e(TAG, "Ads_Banner_Load_Error: " + e.getMessage());
                }
            }

            try {
                loadInterstitialAd();
            } catch (Exception e) {
                Log.e(TAG, "Ads_Interstitial_Load_Error: " + e.getMessage());
            }
        } else {
            manageRemoveAds();
        }


    }

    @Override
    public void initUI() {

        setHeightWidth(ivTitle, 862, 174);
        setMargins(ivTitle, 0, 150, 50, 0);
        setHeightWidth(relativeHeader, 1012, 658);
        setMarginTop(relativeHeader, 425);
        setHeight(linearStart, 280);
        setHeight(linearWhtsappDirect, 150);
        setHeightWidth(ivStart, 319, 215);
        setHeightWidth(ivSaved, 319, 215);
        setHeightWidth(ivWhatsappDirect, 715, 110);
        setHeight(linearMoreMenu, 228);
        setHeightWidth(ivShare, 130, 100);
        setHeightWidth(ivRate, 130, 100);
        setHeightWidth(ivSettings, 130, 100);
        setHeightWidth(ivHowToUse, 130, 100);

        setMargins(rl_recents, 35, 0, 35, 20);
        setMargins(linearRecentBullet, 40, 40, 0, 30);
        setHeightWidth(ivBullet, 13, 30);


    }

    @Override
    public void initData() {
        int updater_count = sp.get(SPHelper.APP_UPDATER_COUNT, 0);
        sp.save(SPHelper.APP_UPDATER_COUNT, updater_count + 1);
    }

    @Override
    public void initActions() {

        new ClickShrinkEffect(ivStart);
        new ClickShrinkEffect(ivSaved);
        new ClickShrinkEffect(ivWhatsappDirect);

        ivStart.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {
                    start_click = start_start;
                    Constant.TYPE_STATUS = Constant.TYPE_WHATSAPP_STATUS;
                    int adsCount = sp.getInt(SPHelper.ADS_START_SAVE_COUNTER);
                    adsCount = adsCount + 1;
                    sp.save(SPHelper.ADS_START_SAVE_COUNTER, adsCount);
                    Log.i(TAG_ADS, "Button InterstitialAd counter : " + adsCount);
                    if (adsCount >= AdsConstants.HOME_BUTTON_CLICK) {
                        Log.i(TAG_ADS, "Is need to show button InterstitialAd : " + adsCount);
                        if (new AdsManager(mContext).isNeedToShowAds() && isAdLoaded()) {
                            sp.save(SPHelper.ADS_START_SAVE_COUNTER, 0);
                            showAd();
                        } else {
                            checkPermissions();
                        }
                    } else {
                        checkPermissions();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, mContext.getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                }

            }
        });

        ivSaved.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {
                    start_click = start_saved;
                    Constant.TYPE_STATUS = Constant.TYPE_SAVED_STATUS;
                    int adsCount = sp.getInt(SPHelper.ADS_START_SAVE_COUNTER);
                    adsCount = adsCount + 1;
                    sp.save(SPHelper.ADS_START_SAVE_COUNTER, adsCount);
                    Log.i(TAG_ADS, "Button InterstitialAd counter : " + adsCount);
                    if (adsCount >= AdsConstants.HOME_BUTTON_CLICK) {
                        Log.i(TAG_ADS, "Is need to show button InterstitialAd : " + adsCount);
                        if (new AdsManager(mContext).isNeedToShowAds() && isAdLoaded()) {
                            sp.save(SPHelper.ADS_START_SAVE_COUNTER, 0);
                            showAd();
                        } else {
                            checkPermissions();
                        }
                    } else {
                        checkPermissions();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, mContext.getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                }


            }
        });

        ivWhatsappDirect.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                start_click = start_direct_chat;
                if (new AdsManager(mContext).isNeedToShowAds() && isAdLoaded()) {
                    showAd();
                } else {
                    startActivity(new Intent(mContext, DirectChatActivity.class));
                }

            }
        });


        ivRate.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                isFromRate = true;
                FeedbackUtils.rateApp(mContext, getPackageName());
                //    EasyRateDialog.show(mContext);

            }
        });


        ivSettings.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                startActivity(new Intent(mContext, SettingsActivity.class));
            }
        });

        ivShare.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                AppHelper.shareApp(mContext);


            }
        });

        ivHowToUse.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(mContext, HowUseActivity.class);
                intent.putExtra("help_type", Constant.HELP_STATUS_SAVER);
                startActivity(intent);

            }
        });
    }


    private void checkPermissions() {

        Permission.requestPermission(mContext, permissions, new Permission.PermissionListener() {
            @Override
            public void onGranted() {
                startActivity(new Intent(mContext, StatusActivity.class));
            }

            @Override
            public void onDenied() {
                Toast.makeText(mContext, Permission.FAILED_MULTIPLE, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermenantlyDenied() {
                Permission.showPermissionsAlert(mContext);
            }
        });

    }

    @Override
    public void onPositiveButtonClicked(int rate, @NotNull String comment) {
        sp.save(SPHelper.APP_RATED, true);
        if (rate == 4) {
            AppHelper.rateApp(mContext);
        } else {
            FeedbackUtils.sendEmail(this, null, comment, null);
        }
    }

    @Override
    public void onNegativeButtonClicked() {
        if (!isFromRate) {
            finishAffinity();
        }
    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onMoreThan4StarSelection(int rate) {
        sp.save(SPHelper.APP_RATED, true);
        FeedbackUtils.rateApp(this, getPackageName());
    }


    private class LoadPhotos extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            photoList = new ArrayList<>();
            ArrayList<File> list = LoadStatusHelper.getAllStatuses();
            photoList.addAll(SortHelper.loadLatest(list));
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isUpdate) {
            super.onPostExecute(isUpdate);
            if (isUpdate & isActive) {
                if (photoList.size() > 0) {
                    llRecentContainer.setVisibility(View.VISIBLE);
                    setupRecent(photoList);
                    findViewById(R.id.rel_banner_ad).setVisibility(View.GONE);
                } else {
                    llRecentContainer.setVisibility(View.GONE);
                    try {
                        if (new AdsManager(mContext).isNeedToShowAds()) {
                            try {
                                BannerAdsHelper.getInstance().loadBanner(mContext);
                            } catch (Exception e) {
                                Log.e(TAG, "Ads_Banner_Load_Error: " + e.getMessage());
                            }
                        } else {
                            manageRemoveAds();
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    private void setupRecent(final ArrayList<String> photoList) {

        for (int i = 0; i < recentStatuses.size(); i++) {
            recentStatuses.get(i).setVisibility(View.INVISIBLE);

        }

        for (int i = 0; i < photoList.size(); i++) {
            recentStatuses.get(i).setVisibility(View.VISIBLE);
            final int finalI = i;
            recentStatuses.get(i).setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {


                    try {
                        clickPosition = finalI;
                        start_click = start_recent;
                        int adsCount = sp.getInt(SPHelper.ADS_HOME_MOST_RECENT_COUNTER);
                        adsCount = adsCount + 1;
                        sp.save(SPHelper.ADS_HOME_MOST_RECENT_COUNTER, adsCount);
                        Log.i(TAG_ADS, "Recent InterstitialAd counter : " + adsCount);
                        if (adsCount >= AdsConstants.MOST_RECENT) {
                            Log.i(TAG_ADS, "Is need to show Recent InterstitialAd : " + adsCount);
                            if (new AdsManager(mContext).isNeedToShowAds() && isAdLoaded()) {
                                sp.save(SPHelper.ADS_HOME_MOST_RECENT_COUNTER, 0);
                                showAd();
                            } else {
                                openRecentPreview(clickPosition);
                            }
                        } else {
                            openRecentPreview(clickPosition);
                        }
                    } catch (Exception ignoredd) {
                        Toast.makeText(mContext, mContext.getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                    }

                }
            });
            String path = photoList.get(i);
            int thumb = R.drawable.img_thumb;

            switch (FileFormatHelper.FileType.getFileType(new File(path))) {
                case IMAGE:
                    thumb = R.drawable.img_thumb;
                    recentStatusesThumb.get(i).setSelected(true);
                    break;
                case VIDEO:
                    thumb = R.drawable.vid_thumb;
                    recentStatusesThumb.get(i).setSelected(false);
                    break;
            }
            RequestOptions simpleOptions = new RequestOptions().centerCrop().placeholder(thumb).error(thumb).override(250, 250).diskCacheStrategy(DiskCacheStrategy.RESOURCE);

            Glide.with(mContext).load(path).apply(simpleOptions).into(recentStatusesImages.get(i));

        }


    }

    private void openRecentPreview(int position) {
        Constant.TYPE_STATUS = Constant.TYPE_WHATSAPP_STATUS;
        String path = photoList.get(position);
        Intent intent = null;
        switch (FileFormatHelper.FileType.getFileType(new File(path))) {
            case IMAGE:
                intent = new Intent(mContext, PhotoPreviewActivity.class);
                break;
            case VIDEO:
                intent = new Intent(mContext, VideoPreviewActivity.class);
                break;
            default:
                Toast.makeText(mContext, mContext.getString(R.string.invalid_format), Toast.LENGTH_SHORT).show();

        }
        intent.putExtra("path", path);
        intent.putExtra("position", 0);
        ArrayList<String> temp = new ArrayList<>();
        temp.add(photoList.get(position));
        intent.putExtra("arraylist", temp);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadPhotos().execute();

        if (!new AdsManager(mContext).isNeedToShowAds()) {
            manageRemoveAds();
        }


    }

    private void manageRemoveAds() {
        findViewById(R.id.rel_banner_ad).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        boolean showDialog = false;
        int counter = sp.get(SPHelper.APP_EXIT_COUNTER, 0);
        Log.i("Exit Counter", " " + counter);
        if (!sp.get(SPHelper.APP_RATED, false)) {
            Log.i("Exit Counter", "Not Rated");
            if (counter >= 4) {
                showDialog = true;
            }
        } else {
            Log.i("Exit Counter", "Rated");
        }
        if (showDialog) {
            isFromRate = false;
            sp.save(SPHelper.APP_EXIT_COUNTER, counter + 1);
            EasyRateDialog.show(mContext);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                sp.save(SPHelper.APP_EXIT_COUNTER, counter + 1);
                super.onBackPressed();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Press once again to exit!", Snackbar.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();
        }
    }


    @Override
    protected void onDestroy() {
        Glide.with(getApplicationContext()).pauseRequests();
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isActive = false;
    }


}

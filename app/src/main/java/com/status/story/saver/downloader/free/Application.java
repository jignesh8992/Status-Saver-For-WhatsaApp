package com.status.story.saver.downloader.free;


import android.content.Context;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.jignesh.jndroid.JndroidApplication;
import com.llew.huawei.verifier.LoadedApkHuaWei;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

public final class Application extends JndroidApplication {

    private static Application instance;


    public static Application getInstance() {
        if (instance == null) {
            synchronized (Application.class) {
                if (instance == null) {
                    instance = new Application();
                }
            }
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        AudienceNetworkAds.initialize(this);
        LoadedApkHuaWei.hookHuaWeiVerifier(this);


        // Initialize Google Ads
        MobileAds.initialize(this);

        // Add Facebook Test Device id here
        AdSettings.addTestDevice("ce0f216b-e9fc-462b-9203-c50513747c11");

        // Add Google Test Device id here
        ArrayList<String> testDevices = new ArrayList<>();
        testDevices.add(AdRequest.DEVICE_ID_EMULATOR);
        testDevices.add("F3DB57B38CD2293DF25DF9C31C158C6A");
        RequestConfiguration requestConfiguration = new RequestConfiguration.Builder().setTestDeviceIds(testDevices).build();
        MobileAds.setRequestConfiguration(requestConfiguration);


        initImageLoader(this);
    }

    public void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }


}
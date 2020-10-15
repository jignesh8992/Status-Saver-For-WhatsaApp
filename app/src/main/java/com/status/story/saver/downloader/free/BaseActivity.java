package com.status.story.saver.downloader.free;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jignesh.jndroid.utils.SPHelper;
import com.status.story.saver.downloader.free.ads.AdsConstants;
import com.status.story.saver.downloader.free.ads.BannerAdsHelper;
import com.status.story.saver.downloader.free.ads.NativeAdsHelper;
import com.status.story.saver.downloader.free.ads.NativeBannerAdsHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * ToDo. BaseActivity contains some modifications to the native Activity.
 */
public abstract class BaseActivity extends FragmentActivity {


    public int SCALE_WIDTH = 1080; // scale width of ui
    public int SCALE_HEIGHT = 1920; // scale height of ui

    public Activity mContext;
    public SPHelper sp;
    public long back_pressed;
    // Ads
    private String TAG_GOOGLE = "Ads_Google_InterstitialAd";
    private String TAG_FB = "Ads_Facebook_InterstitialAd";
    public String TAG_ADS = "Ads_Ads";
    private InterstitialAd interstitialGoogleAd = null;
    private com.facebook.ads.InterstitialAd interstitialFBAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        sp = new SPHelper(mContext);
    }

    public void setContentView(int layout) {
        super.setContentView(layout);
        initViews();
        initAds();
        initUI();
        initData();
        initActions();
    }

    /**
     * ToDo. Set the context of activity
     *
     * @return The context of activity.
     */
    protected abstract Activity getContext();


    /**
     * ToDo. Use this method to initialize view components.
     */
    public abstract void initViews();


    /**
     * ToDo. Use this method to loadFBInterstitialAd Ads.
     */
    public abstract void initAds();

    /**
     * ToDo. Use this method to setup view ui.
     */
    public abstract void initUI();

    /**
     * ToDo. Use this method to initialize data to view components.
     */
    public abstract void initData();


    /**
     * ToDo. Use this method to initialize action on view components.
     */
    public abstract void initActions();

    /**
     * ToDo. Use this method to initialize action on view components.
     */
    public abstract void onInterstitialAdClosed();


    /**
     * Get status bar height
     * * @return Status bar height
     */
    public int getStatusBarHeight() {
        // Get status bar height
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return mContext.getResources().getDimensionPixelSize(resourceId);
    }


    /**
     * ToDo.. Set width and height of view
     *
     * @param view     The view whose width and height are to be set
     * @param v_width  The width to be set
     * @param v_height The height to be set
     */
    public void setHeightWidth(View view, int v_width, int v_height) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int width = dm.widthPixels * v_width / SCALE_WIDTH;
        int height = dm.heightPixels * v_height / SCALE_HEIGHT;
        view.getLayoutParams().width = width;
        view.getLayoutParams().height = height;
    }

    /**
     * ToDo.. Set height of view
     *
     * @param view     The view whose width and height are to be set
     * @param v_height The height to be set
     */
    public void setHeight(View view, int v_height) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int height = dm.heightPixels * v_height / SCALE_HEIGHT;
        view.getLayoutParams().height = height;
    }


    /**
     * ToDo.. Set margin to view
     *
     * @param view     The view whose width and height are to be set
     * @param m_left   The left margin to be set
     * @param m_top    The top margin to be set
     * @param m_right  The right margin to be set
     * @param m_bottom The bottom margin to be set
     */
    public void setMargins(View view, int m_left, int m_top, int m_right, int m_bottom) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        // margin
        int left = dm.widthPixels * m_left / SCALE_WIDTH;
        int top = dm.heightPixels * m_top / SCALE_HEIGHT;
        int right = dm.widthPixels * m_right / SCALE_WIDTH;
        int bottom = dm.heightPixels * m_bottom / SCALE_HEIGHT;

        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }


    /**
     * ToDo.. Set top margin to view
     *
     * @param view  The view whose width and height are to be set
     * @param m_top The top margin to be set
     */
    public void setMarginTop(View view, int m_top) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        // margin
        int top = dm.heightPixels * m_top / SCALE_HEIGHT;
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(0, top, 0, 0);
            view.requestLayout();
        }
    }


    /**
     * ToDo.. Check if permissions are already exist
     *
     * @param permissions The name of the permission being checked.
     * @return {@link PackageManager#PERMISSION_GRANTED} if you have the
     * permission, or {@link PackageManager#PERMISSION_DENIED} if not.
     */
    public boolean hasPermission(String[] permissions) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            result = ContextCompat.checkSelfPermission(mContext, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            return false;
        }
        return true;
    }

    public void loadInterstitialAd() {

        if (AdsConstants.PRIORITY_ADS == AdsConstants.PRIORITY_FB_ADS) {
            Log.i(TAG_FB, "Facebook Interstitial Requested");
            loadFBInterstitialAd();
        } else if (AdsConstants.PRIORITY_ADS == AdsConstants.PRIORITY_GOOGLE_ADS) {
            Log.i(TAG_GOOGLE, "Google Interstitial Requested");
            loadGoogleInterstitialAd();
        }

    }

    private void loadFBInterstitialAd() {
        if (interstitialFBAd == null || !interstitialFBAd.isAdLoaded()) {
            try {
                interstitialFBAd = new com.facebook.ads.InterstitialAd(mContext, mContext.getString(R.string.interstitial_placement_id));
                // Set listeners for the Interstitial Ad
                interstitialFBAd.setAdListener(new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {
                        // Interstitial ad displayed callback
                        Log.i(TAG_FB, "on Interstitial ad displayed.");
                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        // Interstitial dismissed callback
                        Log.i(TAG_FB, "on Interstitial ad dismissed.");
                        onInterstitialAdClosed();
                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        // Ad error callback
                        Log.e(TAG_FB, "on Interstitial ad failed to : " + adError.getErrorMessage());
                        loadGoogleInterstitialAd();

                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        // Interstitial ad is loaded and ready to be displayed
                        Log.i(TAG_FB, "on Interstitial ad is loaded and ready to be " + "displayed!");
                        // Show the ad
                    }

                    @Override
                    public void onAdClicked(Ad ad) {
                        // Ad clicked callback
                        Log.i(TAG_FB, "on Interstitial ad clicked!");
                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                        // Ad impression logged callback
                        Log.i(TAG_FB, "on Interstitial ad impression logged!");
                    }
                });

                // For auto play video ads, it's recommended to loadFBInterstitialAd the ad
                // at least 30 seconds before it is shown
                // Request an ad
                Log.i(TAG_FB, "Load Facebook Interstitial Request: ");
                interstitialFBAd.loadAd();
            } catch (Exception e) {
                Log.e(TAG_FB, "Facebook Interstitial Error: " + e.getMessage());
            }
        } else {
            Log.i(TAG_FB, "Facebook Interstitial Already Loaded: ");
        }
    }

    private void loadGoogleInterstitialAd() {
        if (interstitialGoogleAd == null || !interstitialGoogleAd.isLoaded()) {
            try {
                interstitialGoogleAd = new InterstitialAd(mContext);
                interstitialGoogleAd.setAdUnitId(mContext.getString(R.string.interstitial_ad_id));
                // Request an ad
                Log.i(TAG_GOOGLE, "Load Google Interstitial Request: ");
                interstitialGoogleAd.loadAd(new AdRequest.Builder().build());
                interstitialGoogleAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        Log.i(TAG_GOOGLE, "onInterstitialAd_Loaded: ");
                        // Code to be executed when an ad finishes loading.
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        Log.e(TAG_GOOGLE, "onInterstitialAd_FailedToLoad: " + errorCode);
                        // Code to be executed when an ad request fails.
                        loadFBInterstitialAd();
                    }

                    @Override
                    public void onAdOpened() {
                        Log.i(TAG_GOOGLE, "onInterstitialAd_Opened: ");
                        // Code to be executed when the ad is displayed.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        Log.i(TAG_GOOGLE, "onInterstitialAd_LeftApplication: ");
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        Log.i(TAG_GOOGLE, "onInterstitialAd_Closed: ");
                        onInterstitialAdClosed();
                        // Code to be executed when the interstitialGoogleAd ad is closed.
                    }
                });
            } catch (Exception e) {
                Log.e(TAG_GOOGLE, "Google Interstitial Error: " + e.getMessage());
            }
        } else {
            Log.i(TAG_GOOGLE, "Google Interstitial Already Loaded: ");
        }
    }


    public boolean isAdLoaded() {
        return interstitialFBAd != null && interstitialFBAd.isAdLoaded() || interstitialGoogleAd != null && interstitialGoogleAd.isLoaded();
    }

    public void showAd() {
        if (AdsConstants.PRIORITY_ADS == AdsConstants.PRIORITY_FB_ADS) {
            if (interstitialFBAd.isAdLoaded()) {
                interstitialFBAd.show();
            } else {
                interstitialGoogleAd.show();
            }
        } else if (AdsConstants.PRIORITY_ADS == AdsConstants.PRIORITY_GOOGLE_ADS) {
            if (interstitialGoogleAd.isLoaded()) {
                interstitialGoogleAd.show();
            } else {
                interstitialFBAd.show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BannerAdsHelper.getInstance().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BannerAdsHelper.getInstance().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BannerAdsHelper.getInstance().onDestroy();
        NativeAdsHelper.getInstance().onDestroy();
        NativeBannerAdsHelper.getInstance().onDestroy();
    }


}
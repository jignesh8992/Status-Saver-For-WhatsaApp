package com.status.story.saver.downloader.free;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    public Activity mContext;
    public SPHelper sp;

    // Ads
    private String TAG_GOOGLE = "Ads_Google_InterstitialAd";
    private String TAG_FB = "Ads_Facebook_InterstitialAd";
    public String TAG_ADS = "Ads_Ads";
    private InterstitialAd interstitialGoogleAd = null;
    private com.facebook.ads.InterstitialAd interstitialFBAd = null;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        sp = new SPHelper(mContext);
        initViews(view);
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
    public abstract int getLayout();

    /**
     * ToDo. Set the context of activity
     *
     * @return The context of activity.
     */
    public abstract Activity getContext();


    /**
     * ToDo. Use this method to initialize view components.
     *
     * @param view
     */
    public abstract void initViews(View view);

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
    public abstract void onInterstitialAdClosed();

    /**
     * ToDo. Use this method to initialize action on view components.
     */
    public abstract void initActions();

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
    public void onPause() {
        super.onPause();
        BannerAdsHelper.getInstance().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        BannerAdsHelper.getInstance().onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BannerAdsHelper.getInstance().onDestroy();
        NativeAdsHelper.getInstance().onDestroy();
        NativeBannerAdsHelper.getInstance().onDestroy();
    }


}

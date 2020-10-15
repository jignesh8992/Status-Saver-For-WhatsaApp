package com.status.story.saver.downloader.free.ads;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.status.story.saver.downloader.free.R;

public class BannerAdsHelper {

    /*
     <RelativeLayout
        android:id="@+id/rel_banner_ad"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:background="@color/colorPrimary"
        android:gravity="center"
        android:visibility="gone" />
    */

    private static BannerAdsHelper instance;
    private String TAG_GOOGLE = "Ads_Google_Banner";
    private String TAG_FB = "Ads_Facebook_Banner";


    private com.facebook.ads.AdView adViewFb = null;
    private AdView adViewGoogle = null;

    public static BannerAdsHelper getInstance() {
        if (instance == null) {
            synchronized (BannerAdsHelper.class) {
                if (instance == null) {
                    instance = new BannerAdsHelper();
                }
            }
        }
        return instance;
    }


    public void loadBanner(Activity mContext) {
        if (AdsConstants.PRIORITY_ADS == AdsConstants.PRIORITY_FB_ADS) {
            BannerAdsHelper.getInstance().loadFBBanner(mContext);
        } else if (AdsConstants.PRIORITY_ADS == AdsConstants.PRIORITY_GOOGLE_ADS) {
            BannerAdsHelper.getInstance().loadGoogleBanner(mContext);
        }
    }


    private void loadFBBanner(final Activity mContext) {

        final RelativeLayout adContainer = mContext.findViewById(R.id.rel_banner_ad);
        try {
            adContainer.removeAllViews();
        } catch (Exception ignored) {

        }
        adViewFb = new com.facebook.ads.AdView(mContext,
                mContext.getString(R.string.banner_placement_id),
                com.facebook.ads.AdSize.BANNER_HEIGHT_50);


        // Add the ad view to your activity layout
        adContainer.addView(adViewFb);


        adViewFb.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG_FB, "onBannerAd_Error: " + adError.getErrorMessage());
                loadGoogleBanner(mContext);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
                Log.i(TAG_FB, "onBannerAd_Loaded: ");
                adContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.i(TAG_FB, "onBannerAd_Clicked: ");

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.i(TAG_FB, "onBannerAd_LoggingImpression: ");

            }
        });

        // Request an ad
        Log.i(TAG_FB, "Load Facebook Banner Request: ");
        adViewFb.loadAd();
    }


    private AdSize adSize(Activity mContext, RelativeLayout fAdContainer) {
        Display display = mContext.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = outMetrics.density;
        float adWidthPixels = fAdContainer.getWidth();
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels;
        }
        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(mContext, adWidth);
    }

    private void loadGoogleBanner(final Activity mContext) {
        final RelativeLayout adContainer = mContext.findViewById(R.id.rel_banner_ad);
        try {
            adContainer.removeAllViews();
        } catch (Exception ignored) {

        }
        adViewGoogle = new AdView(mContext);
        adViewGoogle.setAdSize(adSize(mContext, adContainer));
        adViewGoogle.setAdUnitId(mContext.getString(R.string.banner_ad_id));
        adContainer.addView(adViewGoogle);
        adViewGoogle.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adContainer.setVisibility(View.VISIBLE);
                Log.i(TAG_GOOGLE, "onBanner_AdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(int error) {
                Log.e(TAG_GOOGLE, "onBanner_AdFailedToLoad: " + error);
                loadFBBanner(mContext);
            }

        });
        // Request an ad
        Log.i(TAG_GOOGLE, "Load Google Banner Request: ");
        adViewGoogle.loadAd(new AdRequest.Builder().build());
    }


    /**
     * Called when leaving the activity
     */
    public void onPause() {
        if (adViewGoogle != null) {
            adViewGoogle.pause();
        }
    }

    /**
     * Called when returning to the activity
     */
    public void onResume() {
        if (adViewGoogle != null) {
            adViewGoogle.resume();
        }

    }

    /**
     * Called before the activity is destroyed
     */
    public void onDestroy() {
        if (adViewGoogle != null) {
            adViewGoogle.destroy();
        }
        if (adViewFb != null) {
            adViewFb.destroy();
        }
    }

}

package com.status.story.saver.downloader.free.ads;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.status.story.saver.downloader.free.R;

import java.util.ArrayList;
import java.util.List;

public class NativeBannerAdsHelper {

    /*
     <RelativeLayout
        android:id="@+id/rel_native_banner_ad"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center" />
     */

    private static NativeBannerAdsHelper instance;
    private String TAG_GOOGLE = "Ads_Google_Native_Banner";
    private String TAG_FB = "Ads_Facebook_Native_Banner";

    private UnifiedNativeAd nativeGoogleBannerAd;
    private NativeBannerAd nativeFbBannerAd;

    public static NativeBannerAdsHelper getInstance() {
        if (instance == null) {
            synchronized (NativeBannerAdsHelper.class) {
                if (instance == null) {
                    instance = new NativeBannerAdsHelper();
                }
            }
        }
        return instance;
    }


    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     */
    private void loadFbNative(final Activity mContext) {
        // Instantiate a NativeBannerAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a
        // no fill error).
        nativeFbBannerAd = new NativeBannerAd(mContext,
                mContext.getString(R.string.native_banner_placement_id));
        nativeFbBannerAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                Log.e(TAG_FB, "onMediaDownloaded: ");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG_FB,
                        "onNativeBanner_Error: " + adError.getErrorCode() + ": " + adError.getErrorMessage());
                loadGoogleNative(mContext);
            }

            @Override
            public void onAdLoaded(Ad ad) {

                Log.e(TAG_FB, "onNativeBanner_Loaded: ");

                // Race condition, load() called again before last ad was displayed
                if (nativeFbBannerAd == null || nativeFbBannerAd != ad) {
                    return;
                }
                // Inflate Native Banner Ad into Container
                inflateFbNativeBannerAdView(mContext, nativeFbBannerAd);
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.e(TAG_FB, "onNativeBanner_Clicked: ");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.e(TAG_FB, "onNativeBanner_LoggingImpression: ");
            }
        });
        // load the ad
        nativeFbBannerAd.loadAd();


    }


    private void inflateFbNativeBannerAdView(final Activity mContext,
                                             NativeBannerAd nativeBannerAd) {

        // Unregister last ad
        nativeBannerAd.unregisterView();

        // Add the Ad view into the ad container.
        NativeAdLayout nativeAdLayout = new NativeAdLayout(mContext);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        nativeAdLayout.setLayoutParams(params);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.layout_fb_native_banner_ad
                , nativeAdLayout, false);


        nativeAdLayout.addView(adView);


        RelativeLayout adLayout = mContext.findViewById(R.id.rel_native_banner_ad);
        adLayout.removeAllViews();
        adLayout.addView(nativeAdLayout);

        // Add the AdChoices icon
        RelativeLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(mContext, nativeBannerAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        AdIconView nativeAdIconView = adView.findViewById(R.id.native_icon_view);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(nativeBannerAd.hasCallToAction() ? View.VISIBLE :
                View.INVISIBLE);
        nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
        nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
        sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeBannerAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);
    }


    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     */
    private void loadGoogleNative(final Activity mContext) {

        AdLoader.Builder builder = new AdLoader.Builder(mContext,
                mContext.getResources().getString(R.string.native_ad_id));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
                if (nativeGoogleBannerAd != null) {
                    nativeGoogleBannerAd.destroy();
                }
                nativeGoogleBannerAd = unifiedNativeAd;
                RelativeLayout adLayout = mContext.findViewById(R.id.rel_native_banner_ad);
                UnifiedNativeAdView adView =
                        (UnifiedNativeAdView) mContext.getLayoutInflater().inflate(R.layout.layout_google_native_banner_ad, null);
                inflateGoogleNativeBannerAdView(unifiedNativeAd, adView);
                adLayout.removeAllViews();
                adLayout.addView(adView);
            }

        });

        VideoOptions videoOptions = new VideoOptions.Builder().build();

        NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e(TAG_GOOGLE, "onNativeBanner_FailedToLoad: " + errorCode);
                loadFbNative(mContext);
            }
        }).build();
        // Request an ad
        Log.i(TAG_GOOGLE, "Load Google Native Advance Request: ");
        adLoader.loadAd(new AdRequest.Builder().build());


    }


    /**
     * Populates a {@link UnifiedNativeAdView} object with data from a given
     * {@link UnifiedNativeAd}.
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView   the view to be populated
     */
    private void inflateGoogleNativeBannerAdView(UnifiedNativeAd nativeAd,
                                                 UnifiedNativeAdView adView) {

        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);
        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline is guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.GONE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }


        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.GONE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.GONE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);
    }


    public void onDestroy() {
        if (nativeGoogleBannerAd != null) {
            nativeGoogleBannerAd.destroy();
        }
        if (nativeFbBannerAd != null) {
            nativeFbBannerAd.destroy();
        }
    }


    public void loadNativeBanner(Activity mContext) {
        if (AdsConstants.PRIORITY_NATIVE_BANNER_ADS == AdsConstants.PRIORITY_FB_ADS) {
            loadFbNative(mContext);
        } else if (AdsConstants.PRIORITY_NATIVE_BANNER_ADS == AdsConstants.PRIORITY_GOOGLE_ADS) {
            loadGoogleNative(mContext);
        }
    }
}

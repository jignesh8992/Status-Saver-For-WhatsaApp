package com.status.story.saver.downloader.free.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.jignesh.jndroid.statusbar.StatusBarCompat
import com.jignesh.jndroid.utils.AppHelper
import com.jignesh.jndroid.utils.DialogHelper
import com.jignesh.jndroid.utils.OnSingleClickListener
import com.status.story.saver.downloader.free.BaseActivity
import com.status.story.saver.downloader.free.R
import com.status.story.saver.downloader.free.aboutus.FeedbackActivity
import com.status.story.saver.downloader.free.aboutus.utilities.EasyAbout
import com.status.story.saver.downloader.free.ads.NativeAdsHelper
import com.status.story.saver.downloader.free.inapp.*
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity(), InAppPurchaseHelper.OnPurchased {

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBarCompat.translucentStatusBar(this@SettingsActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    override fun getContext(): Activity {
        return this@SettingsActivity
    }

    override fun initViews() {

    }

    override fun initAds() {
        try {
            if (AdsManager(mContext!!).isNeedToShowAds()) {
                InAppPurchaseHelper.instance!!.initBillingClient(mContext!!, this)
                NativeAdsHelper.getInstance().loadGoogleNative(mContext)


                val animation1: Animation = AnimationUtils.loadAnimation(mContext, R.anim.shake)
                iv_remove_ads.startAnimation(animation1)
            } else {
                manageRemoveAds()
            }

        } catch (e: Exception) {
            Log.e(TAG_ADS, "Ads_Native_Load_Error: " + e.message)
        }
    }

    override fun initUI() {}
    override fun initData() {}
    override fun initActions() {

        iv_back!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                onBackPressed()
            }
        })
        linear_about_us!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {
                    val pInfo = packageManager.getPackageInfo(packageName, 0)
                    val version = pInfo.versionName
                    EasyAbout.Builder(mContext).withVersion(version).build().start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
        linear_feedback!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                startActivity(Intent(mContext, FeedbackActivity::class.java))
            }
        })
        linear_more_apps!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                DialogHelper.showMoreAppAlert(mContext) { AppHelper.rateApp(mContext) }
            }
        })
        linear_privacy_policy!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                AppHelper.openPrivacyPolicy(mContext)
            }
        })
        iv_remove_ads!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {

                showPurchaseAlert(PRODUCT_PURCHASED, false)


            }
        })
    }

    override fun onInterstitialAdClosed() {}
    override fun onPurchasedSuccess(purchase: Purchase) {
        runOnUiThread {
            //    Toast.makeText(mContext, "Successfully Remove Ads", Toast.LENGTH_LONG).show()
            showPurchaseSuccess()
            manageRemoveAds()
        }
    }

    override fun onProductAlreadyOwn() {
        runOnUiThread {
            // Toast.makeText(mContext, "You alraedy own this product", Toast.LENGTH_LONG).show()
            showPurchaseSuccess()
            manageRemoveAds()
        }

    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {

    }

    override fun onBillingUnavailable() {

    }


    override fun onResume() {
        super.onResume()
        if (!AdsManager(mContext).isNeedToShowAds()) {
            manageRemoveAds()
        }
    }

    private fun manageRemoveAds() {
        findViewById<View>(R.id.rel_native_ad).visibility = View.GONE
        findViewById<View>(R.id.iv_remove_ads).visibility = View.GONE
    }
}
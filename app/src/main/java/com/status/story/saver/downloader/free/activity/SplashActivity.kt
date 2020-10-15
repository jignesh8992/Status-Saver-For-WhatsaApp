package com.status.story.saver.downloader.free.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.example.daliynotification.NotificationJob
import com.nostra13.universalimageloader.core.ImageLoader
import com.status.story.saver.downloader.free.BaseActivity
import com.status.story.saver.downloader.free.MainActivity
import com.status.story.saver.downloader.free.R
import com.status.story.saver.downloader.free.inapp.AdsManager
import com.status.story.saver.downloader.free.inapp.InAppPurchaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SplashActivity : BaseActivity(), InAppPurchaseHelper.OnPurchased {
    private val TAG = SplashActivity::class.java.simpleName
    override fun onInterstitialAdClosed() {
        startActivity(Intent(mContext, MainActivity::class.java))
        finish()
    }

    private var ivSplash: ImageView? = null
    private var handler: Handler? = null
    private val runnable = Runnable {
        try {
            if (AdsManager(mContext).isNeedToShowAds() && isAdLoaded) {
                showAd()
            } else {
                startActivity(Intent(mContext, MainActivity::class.java))
                finish()
            }
        } catch (e: Exception) {
            startActivity(Intent(mContext, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(1024, 1024)
        setContentView(R.layout.activity_splash)
    }

    override fun getContext(): Activity {
        return this@SplashActivity
    }

    override fun initViews() {
        ivSplash = findViewById(R.id.iv_splash)
    }

    override fun initAds() {
        try {
            //   loadInterstitialAd();
            InAppPurchaseHelper.instance!!.initBillingClient(mContext!!, this)
        } catch (e: Exception) {
            Log.e(TAG_ADS, "Interstitial Error: " + e.message)
        }
    }

    override fun initUI() {
        val imageLoader = ImageLoader.getInstance()
        imageLoader.displayImage("drawable://" + R.drawable.splash_bg, ivSplash)
    }

    override fun initData() {
        // Call Daily Notification to received offline notification
        dailyNotifications()

    }

    private fun startMain() {
        handler = Handler()
        handler!!.postDelayed(runnable, 2500)
    }

    private fun dailyNotifications() {

        try {
            NotificationJob.Builder(this)
                    .setIntervalInDay(2)
                    .setHour(10)
                    .setMinute(10)
                    .setIncludeToday(true)
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setTitle(getString(R.string.app_name))
                    .setMessage(getString(R.string.app_name))
                    .setStartActivity(SplashActivity::class.java)
                    .build()
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }


    }

    override fun initActions() {}
    override fun onPurchasedSuccess(purchase: Purchase) {

    }

    override fun onProductAlreadyOwn() {

    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        GlobalScope.launch(Dispatchers.Main) {
            InAppPurchaseHelper.instance!!.initProducts()
            Log.i(TAG, "IN_APP_BILLING | Done")
            startMain()
        }
    }

    override fun onBillingUnavailable() {
        startMain()
    }


}
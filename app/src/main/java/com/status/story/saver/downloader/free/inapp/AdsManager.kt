package com.status.story.saver.downloader.free.inapp

import android.app.Activity

class AdsManager(mActivity: Activity) {

    // SP to be save & retrieve
    private val isNeedToShow = "isNeedToShow"
    private val sp: SharedPreferences

    init {
        sp = SharedPreferences(mActivity)
    }

    fun onProductPurchased() {
        sp.save(isNeedToShow, true)
    }

    fun isNeedToShowAds(): Boolean {
        val isProductPurchased = sp.read(isNeedToShow, false)
        return !isProductPurchased
    }

    /**
     *   SharedPreferences helper class
     */
    private inner class SharedPreferences//  Default constructor
    internal constructor(private val mActivity: Activity) {
        private val myPreferences = "ads_pref"

        //  Save boolean value
        fun save(key: String, value: Boolean) {
            val editor = mActivity.getSharedPreferences(myPreferences, 0).edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        //  Read Boolean value
        fun read(key: String, defValue: Boolean): Boolean {
            return mActivity.getSharedPreferences(myPreferences, 0).getBoolean(key, defValue)
        }

    }
}
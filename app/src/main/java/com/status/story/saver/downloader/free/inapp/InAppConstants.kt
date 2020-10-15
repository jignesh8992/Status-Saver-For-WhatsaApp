package com.status.story.saver.downloader.free.inapp

import android.app.AlertDialog
import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.billingclient.api.Purchase
import com.status.story.saver.downloader.free.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull


const val PLAY_STORE_SUBSCRIPTION_URL = "https://play.google.com/store/account/subscriptions"
const val PLAY_STORE_SUBSCRIPTION_DEEPLINK_URL = "https://play.google.com/store/account/subscriptions?sku=%s&package=%s"

// Products Id's
// const val PRODUCT_PURCHASED = "android.test.purchased"
const val PRODUCT_PURCHASED = "com.statussaver.removeads"

// List of purchased products
val purchaseHistory: ArrayList<Purchase> = ArrayList()

// List of available products to buy
val PRODUCT_LIST: ArrayList<DaoProducts> = ArrayList()

// SKU list
val skuList = listOf(
        PRODUCT_PURCHASED
)

fun Context.showPurchaseAlert(@NotNull productId: String, fIsConsumable: Boolean) {
    // Initialize a new foreground color span instance
    val foregroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimaryDark))

    val titleText = getString(R.string.app_name)
    val msg = "Do you want to remove ads?"
    // Initialize a new spannable string builder instance
    val ssBuilder = SpannableStringBuilder(titleText)

    // Apply the text color span

    ssBuilder.setSpan(foregroundColorSpan, 0, titleText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    val dialog1 = AlertDialog.Builder(this)
            .setTitle(ssBuilder)
            .setMessage(msg)
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                GlobalScope.launch {
                    InAppPurchaseHelper.instance!!.purchaseProduct(productId, fIsConsumable)
                }
            }
            .show()
    val textView = dialog1.findViewById<TextView>(android.R.id.message)
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)

    try {
        val face = Typeface.createFromAsset(assets, "fonts/Montserrat-Medium.ttf")
        textView.typeface = face
    } catch (e: Exception) {
        Log.e("Error", e.toString())
    }

}


fun Context.showPurchaseSuccess() {
    // Initialize a new foreground color span instance
    val foregroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimaryDark))

    val titleText = getString(R.string.app_name)
    val msg = "You have successfully removed ads from the application. Now enjoy the app without any interference."
    // Initialize a new spannable string builder instance
    val ssBuilder = SpannableStringBuilder(titleText)

    // Apply the text color span

    ssBuilder.setSpan(foregroundColorSpan, 0, titleText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    val dialog1 = AlertDialog.Builder(this)
            .setTitle(ssBuilder)
            .setMessage(msg)
            .setPositiveButton("OK") { _, _ -> }
            .show()
    val textView = dialog1.findViewById<TextView>(android.R.id.message)
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)

    try {
        val face = Typeface.createFromAsset(assets, "fonts/Montserrat-Medium.ttf")
        textView.typeface = face
    } catch (e: Exception) {
        Log.e("Eorro", e.toString())
    }

}


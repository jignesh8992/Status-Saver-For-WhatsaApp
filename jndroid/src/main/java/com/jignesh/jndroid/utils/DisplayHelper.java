package com.jignesh.jndroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DisplayHelper {

    /**
     * ToDo.. Hide status bar
     *
     * @param mContext The context
     */
    public static void hideStatusBar(Context mContext) {
        ((Activity) mContext).getWindow().setFlags(1024, 1024);
    }

    /**
     * ToDo. Get Pixel from dp
     *
     * @param mContext The context
     * @return The Pixel
     */
    public static int dpToPx(Context mContext, int dp) {
        Resources r = mContext.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r
                .getDisplayMetrics()));
    }

    /**
     * ToDo. Get Pixel from sp
     *
     * @param mContext The context
     * @return The Pixel
     */
    public static int spToPx(Context mContext, int sp) {
        Resources r = mContext.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, r
                .getDisplayMetrics()));
    }

    /**
     * ToDo. Get SP from DP
     *
     * @param mContext The context
     * @return The SP
     */
    public static int dpToSp(Context mContext, int dp) {
        Resources r = mContext.getResources();
        return Math.round((dpToPx(mContext, dp) / r.getDisplayMetrics().scaledDensity));
    }

    /**
     * ToDo.. Get height of screen
     *
     * @param mContext The context
     * @return The height of screen
     */
    public static int getDisplayHeight(Context mContext) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * ToDo.. Get width of screen
     *
     * @param mContext The context
     * @return The width of screen
     */
    public static int getDisplayWidth(Context mContext) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }


}

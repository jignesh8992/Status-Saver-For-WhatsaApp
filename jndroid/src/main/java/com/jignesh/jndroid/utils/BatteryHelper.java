package com.jignesh.jndroid.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryHelper {

    /**
     * ToDo.. Get Battery percentage
     *
     * @param mContext The context
     * @return The percentage of battery
     */
    public static int getBatteryPercentage(Context mContext) {
        int percentage = 0;
        Intent batteryStatus = getBatteryStatusIntent(mContext);
        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            percentage = (int) ((level / (float) scale) * 100);
        }

        return percentage;
    }

    private static Intent getBatteryStatusIntent(Context mContext) {
        IntentFilter batFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        return mContext.registerReceiver(null, batFilter);
    }


}
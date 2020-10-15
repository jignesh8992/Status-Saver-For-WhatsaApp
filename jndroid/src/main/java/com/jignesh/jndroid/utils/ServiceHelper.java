package com.jignesh.jndroid.utils;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceHelper {

    /**
     * ToDo.. Get service running status
     *
     * @param mContext     The context
     * @param serviceClass The serviceClass to be check for running status
     * @return true if service running, or false
     */

    public static boolean isRunning(Context mContext, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context
                .ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer
                .MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

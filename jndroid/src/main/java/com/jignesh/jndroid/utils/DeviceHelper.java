package com.jignesh.jndroid.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

public class DeviceHelper {

    /**
     * ToDo.. Get device lock status
     *
     * @param mContext The Context
     * @return true if the device is locked or screen turned off,or false
     */
    public static boolean isDeviceLocked(Context mContext) {
        boolean isLocked = false;

        // First we check the locked state
        KeyguardManager keyguardManager = (KeyguardManager) mContext.getSystemService(Context
                .KEYGUARD_SERVICE);
        boolean inKeyguardRestrictedInputMode = keyguardManager.inKeyguardRestrictedInputMode();

        if (inKeyguardRestrictedInputMode) {
            isLocked = true;

        } else {
            // If password is not set in the settings, the inKeyguardRestrictedInputMode()
            // returns false,
            // so we need to check if screen on for this case
            PowerManager powerManager = (PowerManager) mContext.getSystemService(Context
                    .POWER_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                isLocked = !powerManager.isInteractive();
            } else {
                //noinspection deprecation
                isLocked = !powerManager.isScreenOn();
            }
        }
        //  Loggi.d(String.format("Now device is %s.", isLocked ? "locked" : "unlocked"));
        return isLocked;
    }
}

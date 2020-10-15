package com.jignesh.jndroid.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;

public class FlashHelper {

    public static String TAG = "JNP__" + FlashHelper.class.getSimpleName();
    public static Camera camera;
    public static boolean isFlashOn;
    public static Parameters params;
    public static String error = "Error Sorry, your device doesn't support flash light!";


    /*
     Add this two permissions in manifest before using it.
    <uses-permission android:name="android.permission.CAMERA"/>
    <uses-feature android:name="android.hardware.Camera"/>
     */


    /**
     * ToDo.. Check device is flash supported
     *
     * @param mContext The Context
     */

    public static Boolean hasSupport(Context mContext) {
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

    }

    /*
     * ToDo..  Get the camera
     */
    public static void getCamera() {
        if (camera == null) {

            try {

                camera = Camera.open();
                params = camera.getParameters();

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /*
     * ToDo.. Turning On flash
     */
    public static void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                getCamera();
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }
    }

    /*
     * ToDo.. Turning Off flash
     */
    public static void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                getCamera();
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }

    /**
     * ToDo.. Release Camera
     *
     * @param mContext The context
     */
    public static void destroy(Context mContext) {
        if (camera != null) {
            camera.release();
            camera = null;
        } else {
            Log.e(TAG, "Flash Helper : Camera is Null , Can't Destroy it");
        }

    }

}

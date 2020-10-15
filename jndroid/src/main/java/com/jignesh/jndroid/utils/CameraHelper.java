package com.jignesh.jndroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.jignesh.jndroid.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CameraHelper {

    public static String TAG = "JNP__" + CameraHelper.class.getSimpleName();
    public static int REQUEST_PHOTO = 1001;
    public static int REQUEST_VIDEO = 1002;
    public static String no_camera = "Sorry! Your device doesn't support camera";
    public static String CANCELLED_PHOTO = "Cancelled image capture";
    public static String FAILED_PHOTO = "Sorry! Failed to capture image";
    public static String CANCELLED_VIDEO = "Cancelled video record";
    public static String FAILED_VIDEO = "Sorry! Failed to record video";

    /**
     * ToDo.. Check if device support camera
     *
     * @param mContext The context
     * @return True if device support camera, otherwise false
     */
    public static boolean isDeviceSupportCamera(Context mContext) {
        if (mContext.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * ToDo.. Get JndroidApplication name from string resource
     *
     * @param mContext The context
     */
    public static String getAppName(Context mContext) {
        return mContext.getString(R.string.app_name);
    }


    /**
     * ToDo.. Capture image from device camera
     * <p>
     * Required Permission
     * <uses-permission android:name="android.permission.CAMERA" />
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
     * <p>
     *
     * @param mContext      The context
     * @param REQUEST_PHOTO JndroidApplication specific request code to match with a result
     * @return The uri of capture image
     */

    public static Uri captureImage(Context mContext, int REQUEST_PHOTO) {
        if (!isDeviceSupportCamera(mContext)) {
            Toast.makeText(mContext, no_camera, Toast.LENGTH_LONG).show();
            return null;
        } else {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            File img_path = Environment.getExternalStoragePublicDirectory(Environment
                    .DIRECTORY_PICTURES);
            File mediaStorageDir = new File(img_path, getAppName(mContext));
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(TAG, "Oops! Failed create " + getAppName(mContext) + " directory");
                    return null;
                }
            }
            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault())
                    .format(new Date());
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" +
                    timeStamp + ".jpg");
            Uri imageUri = Uri.fromFile(mediaFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            ((Activity) mContext).startActivityForResult(intent, REQUEST_PHOTO);
            return imageUri;
        }
    }

    /**
     * ToDo.. Capture image from device camera
     * <p>
     * Required Permission
     * <uses-permission android:name="android.permission.CAMERA" />
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
     * <p>
     *
     * @param mContext      The context
     * @param REQUEST_VIDEO JndroidApplication specific request code to match with a result
     * @return The uri of recorded video
     */
    public static Uri recordVideo(Context mContext, int REQUEST_VIDEO) {
        if (!isDeviceSupportCamera(mContext)) {
            Toast.makeText(mContext, no_camera, Toast.LENGTH_LONG).show();
            return null;
        } else {

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            File video_path = Environment.getExternalStoragePublicDirectory(Environment
                    .DIRECTORY_PICTURES);
            File mediaStorageDir = new File(video_path, getAppName(mContext));
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(TAG, "Oops! Failed create " + getAppName(mContext) + " directory");
                    return null;
                }
            }
            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault())
                    .format(new Date());
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" +
                    timeStamp + ".mp4");
            Uri videoUri = Uri.fromFile(mediaFile);
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            ((Activity) mContext).startActivityForResult(intent, REQUEST_VIDEO);
            return videoUri;
        }
    }
}

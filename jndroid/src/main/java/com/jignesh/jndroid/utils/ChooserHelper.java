package com.jignesh.jndroid.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

public class ChooserHelper {

    public static String TAG = "JNP__" + ChooserHelper.class.getSimpleName();
    public static int REQUEST_PHOTO = 10001;
    public static int REQUEST_MULTIPLE_PHOTO = 10001;
    public static int REQUEST_VIDEO = 10002;
    public static String CANCELLED_PHOTO = "Cancelled image load";
    public static String FAILED_PHOTO = "Sorry! Failed to load image";
    public static String CANCELLED_VIDEO = "Cancelled video load";
    public static String FAILED_VIDEO= "Sorry! Failed to load video";

    /**
     * ToDO.. Open Gallery and select single image
     * <p>
     * Required Permission
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     *
     * @param mContext      The context
     * @param REQUEST_PHOTO JndroidApplication specific request code to match with a result
     */
    public static void chooseImage(Context mContext, int REQUEST_PHOTO) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                    .EXTERNAL_CONTENT_URI);
            ((Activity) mContext).startActivityForResult(Intent.createChooser(intent, "Select " +
                    "Picture"), REQUEST_PHOTO);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    /**
     * ToDO.. Open Gallery and multiple images
     * <p>
     * Required Permission
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     *
     * @param mContext               The context
     * @param REQUEST_MULTIPLE_PHOTO JndroidApplication specific request code to match with a result
     */
    // @Override
    // protected void onActivityResult(int requestCode, int resultCode, Intent
    // data) {
    // if (resultCode == RESULT_OK) {
    // videoPreview.setVisibility(View.GONE);
    // imgPreview.setVisibility(View.VISIBLE);
    // Bitmap bitmap = null;
    // if (data.getData() != null) {
    // Uri selectedImage = data.getData();
    // String path = helper.getRealImagePathFromURI(selectedImage);
    // bitmap = BitmapFactory.decodeFile(path, helper.option());
    // } else {
    // if (data.getClipData() != null) {
    // ClipData mClipData = data.getClipData();
    // for (int i = 0; i < mClipData.getItemCount(); i++) {
    // ClipData.Item item = mClipData.getItemAt(i);
    // Uri selectedImage = item.getUri();
    // String path = helper.getRealImagePathFromURI(selectedImage);
    // bitmap = BitmapFactory.decodeFile(path, helper.option());
    // }
    // }
    // }
    // imgPreview.setImageBitmap(bitmap);
    // } else if (resultCode == RESULT_CANCELED) {
    // Toast.makeText(mContext, "User cancelled image load", 0).show();
    // } else {
    // Toast.makeText(mContext, "Sorry! Failed to load image", 0).show();
    // }
    // }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void openGalleryMultipleImage(Context mContext, int REQUEST_MULTIPLE_PHOTO) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity) mContext).startActivityForResult(Intent.createChooser(intent, "Select " +
                "Picture"), REQUEST_MULTIPLE_PHOTO);
    }


    /**
     * ToDO.. Open Gallery and select video
     * <p>
     * Required Permission
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     *
     * @param mContext      The context
     * @param REQUEST_VIDEO JndroidApplication specific request code to match with a result
     */
    public static void chooseVideo(Context mContext, int REQUEST_VIDEO) {
        try {
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            ((Activity) mContext).startActivityForResult(Intent.createChooser(intent, "Select " +
                    "Video"), REQUEST_VIDEO);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}

package com.jignesh.jndroid.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jignesh.jndroid.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;


public class BitmapHelper {

    private static String TAG = "JNP__" + BitmapHelper.class.getSimpleName();


    /**
     * ToDo.. Get Uri of Bitmap Image
     *
     * @param mContext The context
     * @param bitmap   The bitmap to be converted in uri
     * @return The uri of bitmap
     */
    public static Uri getUri(Context mContext, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(
                mContext.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }


    /**
     * ToDO.. Return bitmap of view
     *
     * @param view The view to be converted into bitmap
     * @return The bitmap of view
     */
    public static Bitmap getBitmap(View view) {
        view.clearFocus();
        view.setPressed(false);

        boolean willNotCache = view.willNotCacheDrawing();
        view.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = view.getDrawingCacheBackgroundColor();
        view.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            view.destroyDrawingCache();
        }
        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e(TAG, "failed getViewBitmap(" + view + ")", new RuntimeException());
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        // Restore the view
        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(willNotCache);
        view.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }


    /**
     * ToDO.. Return bitmap of view
     *
     * @param view The view to be converted into bitmap
     * @return The bitmap of view
     */
    public static Bitmap getBitmap2(View view) {
        //Get the dimensions of the view so we can re-layout the view at its current size
        //and create a bitmap of the same size
        int width = view.getWidth();
        int height = view.getHeight();

        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

        //Cause the view to re-layout
        view.measure(measuredWidth, measuredHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        //Create a bitmap backed Canvas to draw the view into
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        //Now that the view is laid out and we have a canvas, ask the view to draw itself into
        // the canvas
        view.draw(c);

        return b;
    }


    /**
     * ToDO.. Return bitmap of view
     *
     * @param mContext The context
     * @param uri      The uri to be converted into bitmap
     * @return The bitmap of uri
     */
    public static Bitmap getBitmap(Context mContext, Uri uri) {
        Bitmap image = null;
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    mContext.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        return image;
    }


    /**
     * ToDo.. Save Bitmap in internal storage
     * Required Permission
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     *
     * @param mContext The context
     * @param bitmap   The bitmap to be save in internal storage
     * @return The path of image whee image wil be saved
     */

    public static String save(Context mContext, Bitmap bitmap) {
        String storage_path = Environment.getExternalStorageDirectory().getAbsolutePath() + File
                .separator + mContext.getString(R.string.app_name);
        String name = mContext.getString(R.string.app_name) + "_" + System.currentTimeMillis() +
                ".png";
        return save(mContext, bitmap, storage_path, name);
    }


    /**
     * ToDo.. Save Bitmap in internal storage
     * Required Permission
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     *
     * @param mContext The context
     * @param bitmap   The bitmap to be save in internal storage
     * @param name     The name of image
     * @return The path of image whee image wil be saved
     */

    public static String save(Context mContext, Bitmap bitmap, String name) {
        String storage_path = Environment.getExternalStorageDirectory().getAbsolutePath() + File
                .separator + mContext.getString(R.string.app_name);
        return save(mContext, bitmap, storage_path, name);
    }


    /**
     * ToDo.. Save Bitmap in internal storage
     * Required Permission
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     *
     * @param mContext The context
     * @param bitmap   The bitmap to be save in internal storage
     * @param path     The path where bitmap image will be save
     * @param name     The name of image
     * @return The path of image whee image wil be saved
     */

    public static String save(Context mContext, Bitmap bitmap, String path, String name) {

        String storage_path = Environment.getExternalStorageDirectory().getAbsolutePath() + File
                .separator + mContext.getString(R.string.app_name);

        if (!TextUtils.isEmpty(path)) {
            storage_path = storage_path + File.separator + path;
        }

        File myDir = new File(storage_path);
        myDir.mkdirs();
        File file = new File(myDir, name);
        String filepath = file.getPath();
        if (file.exists()) {
            file.delete();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
            ostream.close();

        } catch (Exception e) {
            // log(e.getMessage().toString());
        } finally {
            Intent mediaScanIntent = new Intent(
                    "android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            mediaScanIntent.setData(Uri.fromFile(new File(filepath)));
            mContext.sendBroadcast(mediaScanIntent);
        }

        return file.getPath();
    }


    /**
     * ToDo.. Get center crop bitmap by given high and width
     *
     * @param bitmap    The source bitmap to be center crop
     * @param newHeight The height to be crop
     * @param newWidth  The width to be crop
     * @return The center crop bitmap
     */
    public static Bitmap scaleCenterCropBitmap(Bitmap bitmap, int newHeight, int newWidth) {
        int sourceWidth = bitmap.getWidth();
        int sourceHeight = bitmap.getHeight();

        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        RectF targetRect = new RectF(left, top, left + scaledWidth, top
                + scaledHeight);

        Bitmap center_crop = Bitmap.createBitmap(newWidth, newHeight,
                bitmap.getConfig());
        Canvas canvas = new Canvas(center_crop);
        canvas.drawBitmap(bitmap, null, targetRect, null);

        return center_crop;
    }


}

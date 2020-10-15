package com.jignesh.jndroid.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AssetsHelper {

    private static String TAG = "JNP__" + AssetsHelper.class.getSimpleName();


    /**
     * ToDo.. Get total size of given directory
     *
     * @param mContext The context
     * @param path     The path of directory
     * @return The total size of files of given directory
     */

    public static int listSize(Context mContext, String path) {
        try {
            return mContext.getAssets().list(path).length;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        return 0;
    }


    /**
     * ToDo.. Get total files of given directory
     *
     * @param mContext The context
     * @param path     The path of directory
     * @return The list of files of given directory
     */
    public static ArrayList<String> listOfFiles(Context mContext, String path) {
        ArrayList<String> listOfFiles = new ArrayList<>();
        try {
            // to reach asset
            AssetManager assetManager = mContext.getAssets();
            // to get all item in give path folder.
            String[] files = assetManager.list(path);
            // the loop read all files in give path folder and add into arraylist
            for (int i = 0; i < files.length; i++) {
                listOfFiles.add(path + File.separator + files[i]);
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        return listOfFiles;
    }

    /**
     * ToDo.. Get bitmap
     *
     * @param mContext The context
     * @param path     The path of image
     * @return The bitmap of given path
     */
    public static Bitmap getBitmap(Context mContext, String path) {
        InputStream stream = null;
        try {
            stream = mContext.getAssets().open(path);
            return BitmapFactory.decodeStream(stream);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        return null;
    }

    /**
     * ToDo.. Get bitmap
     *
     * @param mContext The context
     * @param path     The path of image
     * @return The drawable of given path
     */
    public static Drawable getDrawable(Context mContext, String path) {
        InputStream stream = null;
        try {
            stream = mContext.getAssets().open(path);
            return Drawable.createFromStream(stream, null);
        } catch (Exception ignored) {
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        return null;
    }
}

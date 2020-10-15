package com.jignesh.jndroid.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.jignesh.jndroid.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class AppHelper {
    private static String TAG = AppHelper.class.getSimpleName();


    /**
     * ToDo.. Get JndroidApplication name from string resource
     *
     * @param mContext The context
     */
    public static String getAppName(Context mContext) {
        return mContext.getString(R.string.app_name);
    }


    /**
     * ToDo.. Get path of app name folder
     *
     * @param mContext The context
     */
    public static String getOutputPath(Context mContext) {
        String path = Environment.getExternalStorageDirectory() + File.separator + getAppName(mContext);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    /**
     * ToDo.. Share our application url
     *
     * @param mContext The context
     */
    public static void shareApp(Context mContext) {

        // change message as per app
        String text = "I am using the awesome \"%1$s\" app for saving WhatsApp statues. if you want to try, " + "Please " +
                "search: \"%2$s\" in play store!,  Or Click on the link given below to " +
                "download: ";
        String link = "https://play.google.com/store/apps/details?id=" + mContext.getPackageName();

        String appName = "New Status Saver 2020";

        String msg = String.format(text, appName, appName);


        try {

            //share file
            Intent intent = new Intent(Intent.ACTION_SEND);
            try {
                //convert drawable  to bitmap
                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.banner);
                File file = new File(mContext.getExternalCacheDir(), "banner.png");
                FileOutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();

                intent.setType("image/*");
                Uri uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", file);
                intent.putExtra(Intent.EXTRA_STREAM, uri);


            } catch (Exception e) {
                intent.setType("text/plain");
                e.printStackTrace();
            }
            intent.putExtra(Intent.EXTRA_TEXT, msg + link);
            mContext.startActivity(Intent.createChooser(intent, "Share Via"));
        } catch (Exception e) {
            Log.e(TAG, "shareApp: " + e.toString());
        }


    }

    /**
     * ToDo.. Open application in google playstore for giving rating our app
     *
     * @param mContext The context
     */
    public static void rateApp(Context mContext) {
        try {
            Uri marketUri = Uri.parse("market://details?id=" + mContext.getPackageName());
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            mContext.startActivity(marketIntent);
        } catch (ActivityNotFoundException e) {
            Uri marketUri =
                    Uri.parse("https://play.google.com/store/apps/details?id=" + mContext.getPackageName());
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            mContext.startActivity(marketIntent);
        }
    }

    /**
     * ToDo. Open our more apps on playstore
     *
     * @param mContext The context
     */
    public static void moreApp(Context mContext) {
        try {
            String query = mContext.getString(R.string.developer_name); // change query here
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://search?q"
                    + "=pub:" + query));
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            mContext.startActivity(intent);

        } catch (Exception e) {
        }
    }

    /**
     * ToDo.. Get Uri of Bitmap Image
     *
     * @param mContext The context
     * @param bitmap   The image bitmap
     * @return The uri of bitmap image
     */
    public static Uri getImageUri(Context mContext, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }


    public static void openPrivacyPolicy(Context mContext) {
        String url = "https://status-saver-status-downloader.flycricket.io/privacy.html";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        mContext.startActivity(i);
    }
}

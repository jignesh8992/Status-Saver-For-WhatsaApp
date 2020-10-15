package com.stepstone.easy_feedback.components;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.stepstone.apprating.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FeedbackUtils {

    public static String LOG_TO_STRING = SystemLog.extractLogToString();

    public static String getAppLabel(Context mContext) {
        return mContext.getResources().getString(R.string.app_name);
    }

    public static void sendEmail(Context mContext, String subject, String body, String realPath) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mContext.getString(R.string.feedback_mail_id)});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, mContext.getString(R.string.feedback_mail_subject,
                        getAppLabel(mContext))+ " "+ DeviceInfo.getAppVersion(mContext));
        String mailBody = body;
        if (subject != null) {
            mailBody = "Subject: " + subject + "\n\n" + body;
        }
        emailIntent.putExtra(Intent.EXTRA_TEXT, mailBody);
        ArrayList<Uri> uris = new ArrayList<>();


        // Device info
        String deviceInfo = DeviceInfo.getAllDeviceInfo(mContext, false);
        Uri deviceInfoUri = createFileFromString(mContext, deviceInfo,
                mContext.getString(R.string.file_name_device_info));
        uris.add(deviceInfoUri);
        Uri logUri = createFileFromString(mContext, LOG_TO_STRING,
                mContext.getString(R.string.file_name_device_log));
        uris.add(logUri);


        // path
        if (realPath != null) {
            Uri uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", new File(realPath));
            //Uri uri = Uri.parse("file://" + realPath);
            uris.add(uri);
        }
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mContext.startActivity(FileUtils.createEmailOnlyChooserIntent(mContext, emailIntent,
                mContext.getString(R.string.send_feedback_two)));
    }

    private static Uri createFileFromString(Context mContext, String text, String name) {
        File file = new File(mContext.getExternalCacheDir(), name);
        //create the file if it didn't exist
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            //BufferedWriter for performance, false to overrite to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(file, false));
            buf.write(text);
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", file);
    }


    /**
     * ToDo.. Open application in google playstore for giving rating our app
     *
     * @param mContext The context
     */
    public static void rateApp(Context mContext,String pkgName) {
        try {
            Uri marketUri = Uri.parse("market://details?id=" + pkgName);
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            mContext.startActivity(marketIntent);
        } catch (ActivityNotFoundException e) {
            Uri marketUri = Uri.parse("https://play.google.com/store/apps/details?id=" + pkgName);
            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            mContext.startActivity(marketIntent);
        }
    }
}

package com.status.story.saver.downloader.free.utils;

import android.content.Context;
import android.os.Environment;

import com.jignesh.jndroid.utils.AppHelper;

import java.io.File;

public class Constant {

    public static int HELP_STATUS_SAVER = 1;
    public static int HELP_DIRECT_MESSAGE = 2;
    public static int TYPE_HELP = 1;


    public static int TYPE_WHATSAPP_STATUS = 0;
    public static int TYPE_SAVED_STATUS = 1;
    public static int TYPE_STATUS = TYPE_WHATSAPP_STATUS;


    public static String folder = Environment.getExternalStorageDirectory() + File.separator +
            "WhatsApp" + File.separator + "Media" + File.separator + ".Statuses";

    public static String folder2 = Environment.getExternalStorageDirectory() + File.separator +
            "WhatsApp Business" + File.separator + "Media" + File.separator + ".Statuses";

    public static String folder3 = Environment.getExternalStorageDirectory() + File.separator +
            "GBWhatsApp" + File.separator + "Media" + File.separator + ".Statuses";

    public static String folder4 = Environment.getExternalStorageDirectory() + File.separator +
            "parallel_intl" + File.separator + "0" + File.separator + "WhatsApp" + File.separator + "Media" + File.separator + ".Statuses";

    public static String folder5 = Environment.getExternalStorageDirectory() + File.separator +
            "parallel_intl" + File.separator + "0" + File.separator + "WhatsApp Business" + File.separator + "Media" + File.separator + ".Statuses";

    public static String folder6 = Environment.getExternalStorageDirectory() + File.separator +
            "parallel_intl" + File.separator + "0" + File.separator + "GBWhatsApp" + File.separator + "Media" + File.separator + ".Statuses";


    public static String folder7 = Environment.getExternalStorageDirectory() + File.separator +
            "parallel_lite" + File.separator + "0" + File.separator + "WhatsApp" + File.separator + "Media" + File.separator + ".Statuses";

    public static String folder8 = Environment.getExternalStorageDirectory() + File.separator +
            "parallel_lite" + File.separator + "0" + File.separator + "WhatsApp Business" + File.separator + "Media" + File.separator + ".Statuses";

    public static String folder9 = Environment.getExternalStorageDirectory() + File.separator +
            "parallel_lite" + File.separator + "0" + File.separator + "GBWhatsApp" + File.separator + "Media" + File.separator + ".Statuses";


    public static String getPhotoPath(Context mContext) {
        return AppHelper.getOutputPath(mContext) + File.separator + "Photos" + File.separator;
    }

    public static String getVideoPath(Context mContext) {
        return AppHelper.getOutputPath(mContext) + File.separator + "Videos" + File.separator;
    }

    public static String getTempVideoPath(Context mContext) {
        return AppHelper.getOutputPath(mContext) + File.separator + "TempVideo" + File.separator;
    }

    public static String getPhotoPath2(Context mContext) {
        return AppHelper.getOutputPath(mContext) + File.separator + "Photos";
    }

    public static String getVideoPath2(Context mContext) {
        return AppHelper.getOutputPath(mContext) + File.separator + "Videos";
    }

}

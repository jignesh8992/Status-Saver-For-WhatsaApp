package com.jignesh.jndroid.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class RawHelper {

    private static String TAG = "JNP__" + RawHelper.class.getSimpleName();

    /*
     * Todo.. Get List of resources id from raw folder
     *
     * @return ArrayList<RAW> of raw files if exists,or NULL
     */
    public static ArrayList<RAW> list() {
        ArrayList<RAW> ringtone_list = new ArrayList<>();
        Field[] fields = new Field[0];
        for (int i = 0; i < fields.length; i++) {
            try {
                int res_id = fields[i].getInt(fields[i]); // file id
                String res_name = fields[i].getName(); // file name
                RAW raw = new RAW();
                raw.set_ID(res_id);
                raw.set_NAME(res_name);
                ringtone_list.add(raw);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.d(TAG, e.toString());
            }
        }
        return ringtone_list;
    }

    /**
     * ToDo.. Get raw file id by it's name
     *
     * @param mContext The Context
     * @param name     The name of raw file
     * @return Id of raw file
     */
    public static int getId(Context mContext, String name) {
        return mContext.getResources().getIdentifier(name, "raw", mContext.getPackageName());
    }

    /**
     * ToDo.. Get raw file name by it's id
     *
     * @param mContext The Context
     * @param id       The id of raw file
     * @return Name of raw file
     */
    public static String getName(Context mContext, int id) {
        String name = mContext.getResources().getResourceName(id);
        return name.substring(name.lastIndexOf("/") + 1);
    }


    /**
     * ToDo.. Get uri of raw file by it's id
     *
     * @param mContext The Context
     * @param id       The id of raw file
     * @return uri of raw file
     */
    public static Uri getUri(Context mContext, int id) {
        return getRawUri(mContext, getName(mContext, id));
    }


    /**
     * ToDo.. Get uri of raw file by it's name
     *
     * @param mContext The Context
     * @param name     The id of raw file
     * @return uri of raw file
     */
    public static Uri getRawUri(Context mContext, String name) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator + File
                .separator + mContext.getPackageName() + "/raw/" + name);
    }


    /*
     * ToDo.. Copy raw file into SDCard
     */
    public static void CopyRAWtoSDCard(Context mContext, String name, String path, String
            extension) throws
            IOException {
        CopyRAWtoSDCard(mContext, getId(mContext, name), path, extension);
    }


    /*
     * ToDo.. Copy raw file into SDCard
     */
    public static void CopyRAWtoSDCard(Context mContext, int id, String path, String extension)
            throws IOException {
        String outputpath = path + File.separator + getName(mContext, id) + extension;
        InputStream in = mContext.getResources().openRawResource(id);
        FileOutputStream out = new FileOutputStream(outputpath);
        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } finally {
            in.close();
            out.close();
        }
    }

    public static class RAW {
        int _ID;
        String _NAME;

        public RAW() {
        }

        public RAW(int _ID, String _NAME) {
            this._ID = _ID;
            this._NAME = _NAME;
        }

        public int get_ID() {
            return _ID;
        }

        public void set_ID(int _ID) {
            this._ID = _ID;
        }

        public String get_NAME() {
            return _NAME;
        }

        public void set_NAME(String _NAME) {
            this._NAME = _NAME;
        }
    }

}

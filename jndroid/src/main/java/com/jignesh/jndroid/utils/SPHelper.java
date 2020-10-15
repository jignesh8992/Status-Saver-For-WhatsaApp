package com.jignesh.jndroid.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SPHelper {


    // Preferences name to bes used for save and read value from sp

    // SP to be save & retrieve
    public static final String ADS_HOME_MOST_RECENT_COUNTER = "ADS_HOME_MOST_RECENT_COUNTER";
    public static final String ADS_START_SAVE_COUNTER = "ADS_START_SAVE_COUNTER";
    public static final String ADS_STATUS_VIEW_COUNTER = "ADS_STATUS_VIEW_COUNTER";
    public static final String APP_RATED = "APP_RATED_12";
    public static final String APP_EXIT_COUNTER = "APP_EXIT_COUNTER";
    public static final String APP_UPDATER_COUNT = "APP_UPDATER_COUNT";
    public static final String APP_DATE = "APP_DATE";


    // Default value when sp is null
    private final long DEFAULT_LONG = 0;
    private final int DEFAULT_INTEGER = 0;
    private final float DEFAULT_FLOAT = 1.0f;
    private final boolean DEFAULT_FALSE = false;
    private final String DEFAULT_STRING = null;
    private SharedPreferences sp;

    // ToDo.. Default Constructor
    public SPHelper(Context mContext) {
        sp = new SharedPreferences(mContext);
    }


    // ToDo Save and get boolean value

    /**
     * ToDo.. Save boolean value
     *
     * @param key   The key to store value in sp
     * @param value The boolean value is to save
     */
    public void save(String key, boolean value) {
        sp.save(key, value);
    }

    /**
     * ToDo.. Get boolean value
     *
     * @param key      The key to get value from sp
     * @param defValue The defValue to return
     * @return The boolean value if saved, or defValue
     */

    public boolean get(String key, boolean defValue) {
        return sp.read(key, defValue);
    }

    public int get(String key, int defValue) {
        return sp.read(key, defValue);
    }

    /**
     * ToDo.. Save string value
     *
     * @param key   The key to store value in sp
     * @param value The string value is to save
     */
    public void save(String key, String value) {
        sp.save(key, value);
    }

    /**
     * ToDo.. Get string value
     *
     * @param key The key to get value from sp
     * @return The string value
     */
    public String getString(String key) {
        return sp.read(key, DEFAULT_STRING);
    }

    /**
     * ToDo.. Get string value
     *
     * @param key      The key to get value from sp
     * @param defValue The defValue to return
     * @return The string value if saved, or defValue
     */
    public String get(String key, String defValue) {
        return sp.read(key, defValue);
    }

    // ToDo Save and get Integer value

    /**
     * ToDo.. Save integer value
     *
     * @param key   The key to store value in sp
     * @param value The integer value is to save
     */
    public void save(String key, int value) {
        sp.save(key, value);
    }

    /**
     * ToDo.. Get integer value
     *
     * @param key The key to get value from sp
     * @return The integer value
     */
    public int getInt(String key) {
        return sp.read(key, DEFAULT_INTEGER);
    }

    // ToDo Save and get Long value

    // ToDo Save and get float value

    // ToDo Save and get string value


    /**
     * ToDo.. Save and get ArrayList
     * implementation 'com.google.code.gson:gson:2.8.4'
     */



   /* public void saveArrayList(String key, ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        sp.save(key, json);
    }*/




    /*
     * ToDo..  SharedPreferences helper class
     */
    private class SharedPreferences {

        private Context mContext;
        private String MyPREFERENCES = "JNP_pref";

        //  Default constructor
        private SharedPreferences(Context mContext) {
            this.mContext = mContext;
        }

        //  Save Integer value
        private void save(String key, int value) {
            Editor editor = mContext.getSharedPreferences(MyPREFERENCES, 0).edit();
            editor.putInt(key, value);
            editor.commit();
        }

        //  Save Long value
        private void save(String key, long value) {
            Editor editor = mContext.getSharedPreferences(MyPREFERENCES, 0).edit();
            editor.putLong(key, value);
            editor.commit();
        }

        //  Save Float value
        private void save(String key, float value) {
            Editor editor = mContext.getSharedPreferences(MyPREFERENCES, 0).edit();
            editor.putFloat(key, value);
            editor.commit();
        }

        //  Save String value
        private void save(String key, String value) {
            Editor editor = mContext.getSharedPreferences(MyPREFERENCES, 0).edit();
            editor.putString(key, value);
            editor.commit();
        }

        //  Save boolean value
        private void save(String key, boolean value) {
            Editor editor = mContext.getSharedPreferences(MyPREFERENCES, 0).edit();
            editor.putBoolean(key, value);
            editor.commit();
        }


        //  Read String value
        private String read(String key, String defValue) {
            return mContext.getSharedPreferences(MyPREFERENCES, 0).getString(key, defValue);
        }

        //  Read Integer value
        private int read(String key, int defValue) {
            return mContext.getSharedPreferences(MyPREFERENCES, 0).getInt(key, defValue);
        }

        //  Read Long value
        private long read(String key, long defValue) {
            return mContext.getSharedPreferences(MyPREFERENCES, 0).getLong(key, defValue);
        }

        //  Read Float value
        private float read(String key, float defValue) {
            return mContext.getSharedPreferences(MyPREFERENCES, 0).getFloat(key, defValue);
        }

        //  Read Boolean value
        private boolean read(String key, boolean defValue) {
            return mContext.getSharedPreferences(MyPREFERENCES, 0).getBoolean(key, defValue);
        }

    }

}

package com.jignesh.jndroid.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper {

    public static String TAG = "JNP__" + DatabaseHelper.class.getSimpleName();
    private Database dbHelper;
    private SQLiteDatabase db;


    /*
     * ToDo.. Constructor of Database helper class
     */
    public DatabaseHelper(Context mContext) {
        dbHelper = new Database(mContext);
    }

    /**
     * ToDo.. Check if record already exist in table
     *
     * @param name The name to be check in database
     * @return true if record exist, or false
     */
    public boolean isExists(String name) {
        db = dbHelper.getReadableDatabase();
        String[] columns = {dbHelper._NAME};
        String whereClause = dbHelper._NAME + " =? ";
        String[] whereArgs = {name};

        Cursor cursor = null;
        try {
            cursor = db.query(true, dbHelper.TABLE_NAME, columns, whereClause, whereArgs, null,
                    null, null, null);
            if (cursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }

    }

    /**
     * ToDo.. Insert record in table
     *
     * @param data Object of data
     * @return -1 if insert record failed, or 0
     */
    public long insert(Data data) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper._NAME, data.get_NAME());
        values.put(dbHelper._PASSWORD, data.get_PASSWORD());

        return db.insert(dbHelper.TABLE_NAME, null, values);
    }

    /**
     * ToDo.. Get all records of table
     *
     * @return ArrayList<Data> if table has records, or NULL
     */
    public ArrayList<Data> getAll() {
        ArrayList<Data> recordList = new ArrayList<>();
        db = dbHelper.getWritableDatabase();
        String[] columns = {dbHelper._NAME, dbHelper._PASSWORD};
        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, null, null,
                null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(dbHelper._ID));
            String name = cursor.getString(cursor.getColumnIndex(dbHelper._NAME));
            String password = cursor.getString(cursor.getColumnIndex(dbHelper._PASSWORD));
            Data data = new Data();
            data.set_ID(id);
            data.set_NAME(name);
            data.set_PASSWORD(password);
            recordList.add(data);
        }
        if (cursor != null) cursor.close();
        return recordList;
    }


    /**
     * ToDo.. Get table records by single argument
     *
     * @return ArrayList<Data> if table has matching records, or NULL
     */

    public ArrayList<Data> get(String argument) {
        ArrayList<Data> recordList = new ArrayList<>();
        db = dbHelper.getWritableDatabase();
        String[] columns = {dbHelper._NAME, dbHelper._PASSWORD};
        String whereClause = dbHelper._NAME + " =? ";
        String[] whereArgs = {argument};

        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, whereClause,
                whereArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(dbHelper._ID));
            String name = cursor.getString(cursor.getColumnIndex(dbHelper._NAME));
            String password = cursor.getString(cursor.getColumnIndex(dbHelper._PASSWORD));
            Data data = new Data();
            data.set_ID(id);
            data.set_NAME(name);
            data.set_PASSWORD(password);
            recordList.add(data);
        }
        if (cursor != null) cursor.close();
        return recordList;
    }

    /**
     * ToDo.. Get table records by single argument
     *
     * @return ArrayList<Data> if table has matching records, or NULL
     */
    public ArrayList<Data> get(String argument, String argument2) {
        ArrayList<Data> recordList = new ArrayList<>();
        db = dbHelper.getWritableDatabase();
        String[] columns = {dbHelper._ID};
        String whereClause = dbHelper._NAME + " =? AND " + dbHelper._PASSWORD + " =?";
        String[] whereArgs = {argument, argument2};

        Cursor cursor = db.query(dbHelper.TABLE_NAME, columns, whereClause, whereArgs, null,
                null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(dbHelper._ID));
            String name = cursor.getString(cursor.getColumnIndex(dbHelper._NAME));
            String password = cursor.getString(cursor.getColumnIndex(dbHelper._PASSWORD));
            Data data = new Data();
            data.set_ID(id);
            data.set_NAME(name);
            data.set_PASSWORD(password);
            recordList.add(data);
        }
        if (cursor != null) cursor.close();
        return recordList;
    }


    /**
     * ToDo.. Update record
     *
     * @param id       The id of record to update new value of that record
     * @param newValue The new value that relace with new value
     * @return -1 if update record failed, or 0
     */

    public long update(int id, String newValue) {
        db = dbHelper.getWritableDatabase();
        String whereClause = dbHelper._NAME + " =? ";
        String[] whereArgs = {String.valueOf(id)};

        ContentValues values = new ContentValues();
        values.put(dbHelper._NAME, newValue);
        return db.update(dbHelper.TABLE_NAME, values, whereClause, whereArgs);

    }


    /**
     * ToDo.. Delete record
     *
     * @param id The id of record to be deleted
     * @return -1 if update record failed, or 0
     */

    public long delete(int id) {
        db = dbHelper.getWritableDatabase();
        String whereClause = dbHelper._NAME + " =? ";
        String[] whereArgs = {String.valueOf(id)};
        return db.delete(dbHelper.TABLE_NAME, whereClause, whereArgs);
    }

    /*
     * ToDo.. SQLiteOpenHelper Helper class
     */
    class Database extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "database_db"; // Name of database
        private static final int DATABASE_VERSION = 1; // Database version

        private String TABLE_NAME = "database_temp"; // Table name of database

        private String _ID = "_id";
        private String _NAME = "name";
        private String _PASSWORD = "password";


        // Create table query
        private String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + _PASSWORD + " TEXT,"
                + _NAME + " TEXT " + ")";

        // Drop table query
        private String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public Database(Context mContext) {
            super(mContext, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                Log.d(TAG, e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                Log.d(TAG, e.toString());
            }
        }

    }

    public static class Data {
        private int _ID;
        private String _NAME;
        private String _PASSWORD;

        public Data() {
        }

        public Data(int _ID, String _NAME, String _PASSWORD) {
            this._ID = _ID;
            this._NAME = _NAME;
            this._PASSWORD = _PASSWORD;
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

        public String get_PASSWORD() {
            return _PASSWORD;
        }

        public void set_PASSWORD(String _PASSWORD) {
            this._PASSWORD = _PASSWORD;
        }
    }
}

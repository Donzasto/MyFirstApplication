package com.donzasto.myfirstapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class DataProvider extends ContentProvider {

    public static final String DB_NAME = "mytable";
    public static final int DB_VERSION = 1;
    public static final String DB_CREATE = "create table " + DB_NAME + "(_id integer primary key, title text, content text);";

    private final Uri URI = Uri.parse("content://com.donzasto.myfirstapplication.DataProvider/mytable");

    DB db;

    SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onCreate() {
        Log.d(MainActivity.LOGS, "onCreate", null);
        db = new DB(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Log.d(MainActivity.LOGS, "query" + uri, null);
        sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(DB_NAME, null, null, null, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), URI);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(MainActivity.LOGS, "getType", null);
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d(MainActivity.LOGS, "insert", null);
        sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.insert(DB_NAME, null, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        Log.d(MainActivity.LOGS, "delete", null);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        Log.d(MainActivity.LOGS, "update", null);
        return 0;
    }

    class DB extends SQLiteOpenHelper {
        public DB(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            Log.d(MainActivity.LOGS, "DBSQL", null);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d(MainActivity.LOGS, "onCreateSQL", null);
            sqLiteDatabase.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            Log.d(MainActivity.LOGS, "onUpgradeSQL", null);
        }
    }
}

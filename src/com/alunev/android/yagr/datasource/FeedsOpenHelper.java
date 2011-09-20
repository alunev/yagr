package com.alunev.android.yagr.datasource;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FeedsOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_PATH = "/data/data/com.alunev.android.yagr/databases/";

    public static final String FEEDS_TABLE_NAME = "feeds";
    public static final String FIELD_FEED_ID = "id";
    public static final String FIELD_FEED_NAME = "name";
    public static final String FIELD_FEED_UNREAD_COUNT = "unread";

    private static final String DATABASE_NAME = "yagr.db";
    private static final int DATABASE_VERSION = 1;

    private static final String FEEDS_TABLE_CREATE =
                "CREATE TABLE " + FEEDS_TABLE_NAME + " (" +
                    FIELD_FEED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_FEED_NAME + " TEXT, " +
                    FIELD_FEED_UNREAD_COUNT + " INTEGER);";

    public FeedsOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public FeedsOpenHelper(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FEEDS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    public SQLiteDatabase getFreshDatabase() {
        if (checkDataBase()) {
            File file = new File(DATABASE_PATH + DATABASE_NAME);
            file.delete();
        }

        return getWritableDatabase();
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            Log.i(FeedsOpenHelper.class.toString(), "Database not exist yet");
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }
}

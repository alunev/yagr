package com.alunev.android.yagr.ds;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alunev.android.yagr.ds.info.Feed;

public class FeedsDao {
    private SQLiteDatabase db;

    public FeedsDao(SQLiteDatabase db) {
        this.db = db;
    }

    public void insertNewFeed(String title, int unreadCount) {
        db.beginTransaction();

        try {
            db.execSQL("INSERT INTO " + FeedsOpenHelper.FEEDS_TABLE_NAME + " VALUES ("
                    + "NULL,"
                    + "'" + title + "',"
                    + "'" + unreadCount + "')");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void insertNewFeed(Feed feedInfo) {
        insertNewFeed(feedInfo.getTitle(), feedInfo.getUnreadCount());
    }

    public Feed getFeed(int id) {
        Cursor cursor = db.query(FeedsOpenHelper.FEEDS_TABLE_NAME,
                new String[] {
                    FeedsOpenHelper.FIELD_FEED_ID,
                    FeedsOpenHelper.FIELD_FEED_NAME,
                    FeedsOpenHelper.FIELD_FEED_UNREAD_COUNT },
                FeedsOpenHelper.FIELD_FEED_ID + " = ?",
                new String[] { String.valueOf(id) },
                null, null, null);

        Feed feedInfo = null;

        try {
            if (cursor.moveToFirst()) {
                feedInfo = new Feed(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return feedInfo;
    }

    public List<Feed> getAllFeeds() {
        Cursor cursor = db.query(FeedsOpenHelper.FEEDS_TABLE_NAME,
                new String[] {
                    FeedsOpenHelper.FIELD_FEED_ID,
                    FeedsOpenHelper.FIELD_FEED_NAME,
                    FeedsOpenHelper.FIELD_FEED_UNREAD_COUNT },
                null, null, null, null, null);

        Feed feedInfo = null;

        List<Feed> allFeeds = new ArrayList<Feed>();
        try {
            while (cursor.moveToNext()) {
                feedInfo = new Feed(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                allFeeds.add(feedInfo);
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return allFeeds;
    }
}

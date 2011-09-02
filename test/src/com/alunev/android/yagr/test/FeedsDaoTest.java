package com.alunev.android.yagr.test;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.alunev.android.yagr.ds.FeedsDao;
import com.alunev.android.yagr.ds.info.Feed;

public class FeedsDaoTest extends AndroidTestCase {
    private SQLiteDatabase db;

    public void setUp() throws Exception {
        db = (new TestOpenHelper(getContext())).getFreshDatabase();
    }

    public void tearDown() throws Exception {
        db.close();
    }

    public void testInsertFeed() {
        FeedsDao dao = new FeedsDao(db);
        dao.insertNewFeed("qwe", 5);
    }

    public void testInsertGetFeed() {
        Feed info = null;
        FeedsDao dao = new FeedsDao(db);

        dao.insertNewFeed("asd", 2);
        info = dao.getFeed(1);

        assertEquals(new Feed(1, "asd", 2), info);
    }

    public void testMultipleInsertGetFeed() {
        Feed info = null;
        FeedsDao dao = new FeedsDao(db);

        dao.insertNewFeed("asd0", 2);
        dao.insertNewFeed("asd1", 2);
        dao.insertNewFeed("asd2", 2);

        info = dao.getFeed(1);
        assertEquals(new Feed(1, "asd0", 2), info);

        info = dao.getFeed(2);
        assertEquals(new Feed(2, "asd1", 2), info);

        info = dao.getFeed(3);
        assertEquals(new Feed(3, "asd2", 2), info);
    }

    public void testGetAllFeeds() {
        FeedsDao dao = new FeedsDao(db);

        dao.insertNewFeed("asd0", 2);
        dao.insertNewFeed("asd1", 2);
        dao.insertNewFeed("asd2", 2);

        List<Feed> insertedFeeds = new ArrayList<Feed>();
        insertedFeeds.add(new Feed(1, "asd0", 2));
        insertedFeeds.add(new Feed(2, "asd1", 2));
        insertedFeeds.add(new Feed(3, "asd2", 2));

        List<Feed> allFeeds = dao.getAllFeeds();

        assertTrue(allFeeds.containsAll(insertedFeeds));
    }
}

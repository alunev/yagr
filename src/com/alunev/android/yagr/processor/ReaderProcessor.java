package com.alunev.android.yagr.processor;

import java.util.List;

import android.content.Context;

import com.alunev.android.yagr.datasource.FeedsDao;
import com.alunev.android.yagr.datasource.FeedsOpenHelper;
import com.alunev.android.yagr.datasource.info.Feed;
import com.alunev.android.yagr.rest.RestClient;

public class ReaderProcessor {
    private Context context;

    public ReaderProcessor(Context context) {
        this.context = context;
    }

    public List<Feed> getReaderFeeds(String authToken, String authSeret) {
        List<Feed> feeds = new RestClient().getReaderFeeds(authToken, authSeret);

        FeedsDao feedsDao = new FeedsDao(new FeedsOpenHelper(context).getWritableDatabase());
        feedsDao.insertManyFeeds(feeds);

        return feeds;
    }
}

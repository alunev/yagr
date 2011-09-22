package com.alunev.android.yagr.test;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.test.ServiceTestCase;

import com.alunev.android.yagr.datasource.info.Feed;
import com.alunev.android.yagr.service.IReader;
import com.alunev.android.yagr.service.IReaderListener;
import com.alunev.android.yagr.service.ReaderService;

public class ReaderServiceTest extends ServiceTestCase<ReaderService> {
    private IReader reader;

    public ReaderServiceTest() {
        super(ReaderService.class);
    }

    public void testGetReaderFeedsWorks() {
        Intent serviceIntent = new Intent(getContext(), ReaderService.class);

        getContext().bindService(serviceIntent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                reader = (IReader) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                reader = null;
            }
        }, Context.BIND_AUTO_CREATE);

        List<Feed> feeds = reader.getReaderFeeds(getContext(), new IReaderListener() {
            @Override
            public void done() {
                System.out.println("done!");
            }
        });

        assertTrue(feeds.size() > 0);
    }
}

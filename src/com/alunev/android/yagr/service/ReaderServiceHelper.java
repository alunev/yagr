package com.alunev.android.yagr.service;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class ReaderServiceHelper {
    private static ReaderServiceHelper instance;

    private Application context;
    private IReader readerService = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            readerService =  (IReader) binder;
        }

        public void onServiceDisconnected(ComponentName className) {
            readerService = null;
        }
    };

    private ReaderServiceHelper() {

    }

    public static ReaderServiceHelper getInstance() {
        // not synchronizing for now
        if (instance == null) {
            instance = new ReaderServiceHelper();
        }

        return instance;
    }

    public boolean init(Application context) {
        this.context = context;

        Intent serviceIntent = new Intent(context, ReaderService.class);
        return context.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Obtain and store auth token
     */
    public void initializeAuthentication(IReaderListener callback) {
        readerService.initializeAuthentication(context, callback);
    }
}

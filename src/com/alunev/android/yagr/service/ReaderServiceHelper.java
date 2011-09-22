package com.alunev.android.yagr.service;

import java.util.List;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.alunev.android.yagr.datasource.info.Feed;
import com.alunev.android.yagr.exception.OAuthYagrException;
import com.alunev.android.yagr.web.oauth.InitialOAuthenticator;

public class ReaderServiceHelper {

    private static ReaderServiceHelper instance;

    private Application context;
    private IReader readerService = null;
    private InitialOAuthenticator authenticator;
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

    public void initializeAuthentication(IReaderListener callback) throws OAuthYagrException {
        // we are saving this to field cause for some reason our OAuth library is stateful!
        authenticator = readerService.initializeAuthenticationStep1(callback);

        readerService.initializeAuthenticationStep2(context, authenticator, callback);
    }

    public void finalizeAuthentication(IReaderListener callback, String token, String verifCode)
            throws OAuthYagrException {
        authenticator.setToken(token);
        authenticator.setVerificationCode(verifCode);

        readerService.finalizeAuthentication(context, authenticator, callback);
    }

    /**
     * Get List of feeds.
     * @param callback
     * @return
     */
    public List<Feed> getReaderFeeds(IReaderListener callback) {
        return readerService.getReaderFeeds(context, callback);
    }
}

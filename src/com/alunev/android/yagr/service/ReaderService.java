package com.alunev.android.yagr.service;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;

import com.alunev.android.yagr.datasource.info.Feed;
import com.alunev.android.yagr.exception.OAuthYagrException;
import com.alunev.android.yagr.info.Settings;
import com.alunev.android.yagr.processor.ReaderProcessor;
import com.alunev.android.yagr.web.oauth.InitialOAuthenticator;


public class ReaderService extends Service {
    private final ReaderServiceBinder readerBinder = new ReaderServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return readerBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return super.onStartCommand(intent, flags, startId);
    }

    public class ReaderServiceBinder extends Binder implements IReader {
        public InitialOAuthenticator initializeAuthenticationStep1(IReaderListener callback) throws OAuthYagrException {
            InitialOAuthenticator authenticator;
            authenticator = new ReaderProcessor(getApplicationContext()).initializeAuthenticationStep1();

            callback.done();

            return authenticator;
        }

        public void initializeAuthenticationStep2(final Context context, InitialOAuthenticator authenticator,
                IReaderListener callback) {
            new ReaderProcessor(context).initializeAuthenticationStep2(context, authenticator, callback);
        }

        public void finalizeAuthentication(final Context context, InitialOAuthenticator authenticator,
                IReaderListener callback) throws OAuthYagrException {
            new ReaderProcessor(context).finalizeAuthentication(authenticator, callback);
        }

        public List<Feed> getReaderFeeds(final Context context, IReaderListener callback) {
            SharedPreferences preferences = getApplicationContext().
                getSharedPreferences(Settings.SETTINGS_FILE_NAME, MODE_PRIVATE);

            return new ReaderProcessor(ReaderService.this).getReaderFeeds(
                    preferences.getString(Settings.READER_AUTH_TOKEN, null),
                    preferences.getString(Settings.READER_AUTH_SECRET, null));
        }
    }
}

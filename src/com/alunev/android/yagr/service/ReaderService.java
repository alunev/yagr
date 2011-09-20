package com.alunev.android.yagr.service;

import java.util.List;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import com.alunev.android.yagr.datasource.info.Feed;
import com.alunev.android.yagr.info.Settings;
import com.alunev.android.yagr.processor.ReaderProcessor;


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
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return super.onStartCommand(intent, flags, startId);
    }

    public class ReaderServiceBinder extends Binder implements IReader {
        public void initializeAuthentication(final Context context, String url, IReaderListener callback)
                throws OAuthMessageSignerException, OAuthNotAuthorizedException,
                    OAuthExpectationFailedException, OAuthCommunicationException {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

            callback.done();
        }

        public void finalizeAuthentication(final Context context, IReaderListener callback,
                String token, String verifCode)
            throws OAuthMessageSignerException, OAuthNotAuthorizedException,
                OAuthExpectationFailedException, OAuthCommunicationException {
            // just stub
            callback.done();
        }

        public List<Feed> getReaderFeeds(final Context context, IReaderListener callback) {
            SharedPreferences preferences = getApplicationContext().
                getSharedPreferences(Settings.SETTINGS_FILE_NAME, MODE_PRIVATE);

            return new ReaderProcessor().getReaderFeeds(
                    preferences.getString(Settings.READER_AUTH_TOKEN, null),
                    preferences.getString(Settings.READER_AUTH_SECRET, null));
        }
    }
}

package com.alunev.android.yagr.service;

import java.util.List;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
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

import com.alunev.android.yagr.info.Settings;
import com.alunev.android.yagr.processor.ReaderProcessor;


public class ReaderService extends Service {
    private final ReaderServiceBinder readerBinder = new ReaderServiceBinder();

    private static final String CONSUMER_KEY = "<YOUR KEY>";
    private static final String CONSUMER_SECRET = "<YOUR SECRET>";
    private static final String APPNAME = "Yagr";
    private static final String scope = "http://www.google.com/reader/api";
    public static final String reqtokenURL = "https://www.google.com/accounts/OAuthGetRequestToken";
    public static final String authorizeURL = "https://www.google.com/accounts/OAuthAuthorizeToken";
    public static final String accessTokenURL = "https://www.google.com/accounts/OAuthGetAccessToken";

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
        public void initializeAuthentication(final Context context, IReaderListener callback)
                throws OAuthMessageSignerException, OAuthNotAuthorizedException,
                    OAuthExpectationFailedException, OAuthCommunicationException {
            OAuthConsumer oac = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
            OAuthProvider oap = new CommonsHttpOAuthProvider(
                reqtokenURL+"?scope="+scope+"&"+"xoauth_displayname="+APPNAME,
                accessTokenURL,
                authorizeURL+"?hl=en&btmpl=mobile");

            String url = oap.retrieveRequestToken(oac, OAuth.OUT_OF_BAND);
            System.out.println("Open this URL in browser and Grant Access to "+ APPNAME + " : "+url);

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url + "&oauth_callback="
                    + "com.alunev.android.yagr-android-app:///"));
            startActivity(i);

            String accessToken = oac.getToken();
            String tokenSecret = oac.getTokenSecret();

            callback.done();
        }

        public List<String> getReaderFeeds(final Context context, IReaderListener callback) {
            SharedPreferences preferences = getApplicationContext().
            getSharedPreferences(Settings.SETTINGS_FILE_NAME, MODE_PRIVATE);

            return new ReaderProcessor().getReaderFeeds(preferences.getString(Settings.READER_AUTH_TOKEN, null));
        }
    }
}

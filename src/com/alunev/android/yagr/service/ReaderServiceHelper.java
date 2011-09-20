package com.alunev.android.yagr.service;

import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;

import com.alunev.android.yagr.datasource.info.Feed;
import com.alunev.android.yagr.info.Settings;

public class ReaderServiceHelper {
    private static final String APPNAME = "Yagr";
    private static final String scope = "http://www.google.com/reader/api";
    public static final String reqtokenURL = "https://www.google.com/accounts/OAuthGetRequestToken";
    public static final String authorizeURL = "https://www.google.com/accounts/OAuthAuthorizeToken";
    public static final String accessTokenURL = "https://www.google.com/accounts/OAuthGetAccessToken";

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
    private OAuthConsumer oac;
    private OAuthProvider oap;

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
     * @throws OAuthCommunicationException
     * @throws OAuthExpectationFailedException
     * @throws OAuthNotAuthorizedException
     * @throws OAuthMessageSignerException
     */
    public void initializeAuthentication(IReaderListener callback)
        throws OAuthMessageSignerException, OAuthNotAuthorizedException,
            OAuthExpectationFailedException, OAuthCommunicationException {
        // readerService.initializeAuthentication(context, callback);

        oac = new CommonsHttpOAuthConsumer(Settings.CONSUMER_KEY, Settings.CONSUMER_SECRET);
        oap = new CommonsHttpOAuthProvider(
            reqtokenURL+"?scope="+scope+"&"+"xoauth_displayname=" + APPNAME,
            accessTokenURL,
            authorizeURL + "?hl=en&btmpl=mobile");

        String url = oap.retrieveRequestToken(oac, "com-alunev-android-yagr-android-app:///done");
        System.out.println("Open this URL in browser and Grant Access to "+ APPNAME + " : "+url);

        // launch the browser for user to grant us access
        // readerService.initializeAuthentication(context, callback);
        readerService.initializeAuthentication(context, url, callback);
    }

    public void finalizeAuthentication(IReaderListener callback, String token, String verifCode)
        throws OAuthMessageSignerException, OAuthNotAuthorizedException,
            OAuthExpectationFailedException, OAuthCommunicationException {
        oap.retrieveAccessToken(oac, verifCode);

        SharedPreferences preferences = this.context.getSharedPreferences(
                Settings.SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(Settings.READER_AUTH_TOKEN, oac.getToken());
        editor.putString(Settings.READER_AUTH_SECRET, oac.getTokenSecret());
        editor.putBoolean(Settings.IS_FIRST_LAUNCH, false);
        editor.commit();
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

package com.alunev.android.yagr.processor;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;

import com.alunev.android.yagr.datasource.FeedsDao;
import com.alunev.android.yagr.datasource.FeedsOpenHelper;
import com.alunev.android.yagr.datasource.info.Feed;
import com.alunev.android.yagr.exception.OAuthYagrException;
import com.alunev.android.yagr.info.Settings;
import com.alunev.android.yagr.service.IReaderListener;
import com.alunev.android.yagr.web.oauth.Credentials;
import com.alunev.android.yagr.web.oauth.InitialOAuthenticator;
import com.alunev.android.yagr.web.oauth.OAuthClient;
import com.alunev.android.yagr.web.rest.RestClient;

public class ReaderProcessor {
    private Context context;

    public ReaderProcessor(Context context) {
        this.context = context;
    }

    public InitialOAuthenticator initializeAuthenticationStep1() throws OAuthYagrException {
        InitialOAuthenticator authenticator = new OAuthClient().getRequestToken();

        return authenticator;
    }

    public void initializeAuthenticationStep2(final Context context, InitialOAuthenticator authenticator,
            IReaderListener callback) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(authenticator.getRequestURL()));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

        callback.done();
    }

    public void finalizeAuthentication(InitialOAuthenticator authenticator, IReaderListener callback)
            throws OAuthYagrException {
        Credentials credentials = new OAuthClient().getCredentials(authenticator);

        SharedPreferences preferences = this.context.getSharedPreferences(
                Settings.SETTINGS_FILE_NAME, Context.MODE_PRIVATE);

        Editor editor = preferences.edit();
        editor.putString(Settings.READER_AUTH_TOKEN, credentials.getToken());
        editor.putString(Settings.READER_AUTH_SECRET, credentials.getTokenSecret());
        editor.putBoolean(Settings.IS_FIRST_LAUNCH, false);
        editor.commit();
        callback.done();
    }

    public List<Feed> getReaderFeeds(String authToken, String authSeret) {
        List<Feed> feeds = new RestClient().getReaderFeeds(authToken, authSeret);

        FeedsDao feedsDao = new FeedsDao(new FeedsOpenHelper(context).getWritableDatabase());
        feedsDao.insertManyFeeds(feeds);

        return feeds;
    }

}

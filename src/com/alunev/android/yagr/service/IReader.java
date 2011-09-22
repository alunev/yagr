package com.alunev.android.yagr.service;

import java.util.List;

import android.content.Context;

import com.alunev.android.yagr.datasource.info.Feed;
import com.alunev.android.yagr.exception.OAuthYagrException;
import com.alunev.android.yagr.web.oauth.InitialOAuthenticator;

public interface IReader {
    public InitialOAuthenticator initializeAuthenticationStep1(IReaderListener callback) throws OAuthYagrException;

    public void initializeAuthenticationStep2(final Context context, InitialOAuthenticator authenticator,
            IReaderListener callback);

    public void finalizeAuthentication(final Context context, InitialOAuthenticator authenticator,
            IReaderListener callback) throws OAuthYagrException;

    public List<Feed> getReaderFeeds(final Context context, IReaderListener callback);
}

package com.alunev.android.yagr.service;

import java.util.List;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.content.Context;

import com.alunev.android.yagr.datasource.info.Feed;

public interface IReader {
    public void initializeAuthentication(Context context, String url, IReaderListener callback)
        throws OAuthMessageSignerException, OAuthNotAuthorizedException,
            OAuthExpectationFailedException, OAuthCommunicationException;

    public void finalizeAuthentication(Context context, IReaderListener callback,
            String token, String verifCode)
        throws OAuthMessageSignerException, OAuthNotAuthorizedException,
            OAuthExpectationFailedException, OAuthCommunicationException;

    public List<Feed> getReaderFeeds(Context context, IReaderListener callback);
}

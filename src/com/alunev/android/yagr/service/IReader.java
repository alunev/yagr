package com.alunev.android.yagr.service;

import java.util.List;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.content.Context;

public interface IReader {
    public void initializeAuthentication(Context context, IReaderListener callback)
        throws OAuthMessageSignerException, OAuthNotAuthorizedException,
            OAuthExpectationFailedException, OAuthCommunicationException;

    public List<String> getReaderFeeds(Context context, IReaderListener callback);
}

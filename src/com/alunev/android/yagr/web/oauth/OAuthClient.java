package com.alunev.android.yagr.web.oauth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthException;

import com.alunev.android.yagr.exception.OAuthYagrException;
import com.alunev.android.yagr.info.Settings;

public class OAuthClient {
    private static final String APPNAME = "Yagr";
    private static final String scope = "http://www.google.com/reader/api";
    private static final String reqtokenURL = "https://www.google.com/accounts/OAuthGetRequestToken";
    private static final String authorizeURL = "https://www.google.com/accounts/OAuthAuthorizeToken";
    private static final String accessTokenURL = "https://www.google.com/accounts/OAuthGetAccessToken";

    public InitialOAuthenticator getRequestToken() throws OAuthYagrException {
        OAuthConsumer oac = new CommonsHttpOAuthConsumer(Settings.CONSUMER_KEY, Settings.CONSUMER_SECRET);
        OAuthProvider oap = new CommonsHttpOAuthProvider(reqtokenURL
                + "?scope=" + scope
                + "&" + "xoauth_displayname=" + APPNAME,
                accessTokenURL, authorizeURL + "?hl=en&btmpl=mobile");

        String url;
        try {
            url = oap.retrieveRequestToken(oac, "com-alunev-android-yagr-android-app:///done");
        } catch (OAuthException e) {
            throw new OAuthYagrException("Failed to get request token(first step of OAuth)", e);
        }

        return new InitialOAuthenticator(oac, oap, url);
    }

    public Credentials getCredentials(InitialOAuthenticator authenticator) throws OAuthYagrException {
        try {
            authenticator.getOap().retrieveAccessToken(authenticator.getOac(), authenticator.getVerificationCode());
        } catch (OAuthException e) {
            throw new OAuthYagrException("Failed to get access token!(second step of OAuth process)", e);
        }

        return new Credentials(authenticator.getOac().getToken(), authenticator.getOac().getTokenSecret());
    }
}

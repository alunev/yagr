package com.alunev.android.yagr.web.oauth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;

public class InitialOAuthenticator {
    private OAuthConsumer oac;
    private OAuthProvider oap;

    /**
     * Direct User to this URL and there he will be asked if he trusts our application.
     */
    private String requestURL;
    private String token;
    private String verificationCode;

    public InitialOAuthenticator(OAuthConsumer oac, OAuthProvider oap, String requestURL) {
        this.oac = oac;
        this.oap = oap;
        this.requestURL = requestURL;
    }

    public OAuthConsumer getOac() {
        return oac;
    }

    public void setOac(OAuthConsumer oac) {
        this.oac = oac;
    }

    public OAuthProvider getOap() {
        return oap;
    }

    public void setOap(OAuthProvider oap) {
        this.oap = oap;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}

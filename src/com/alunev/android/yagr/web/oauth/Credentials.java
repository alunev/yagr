package com.alunev.android.yagr.web.oauth;

public class Credentials {
    private String token;
    private String tokenSecret;

    public Credentials(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}

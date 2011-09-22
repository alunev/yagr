package com.alunev.android.yagr.exception;

public class OAuthYagrException extends YagrException {
    private static final long serialVersionUID = 3118514848928582609L;

    public OAuthYagrException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public OAuthYagrException(String detailMessage) {
        super(detailMessage);
    }

    public OAuthYagrException(Throwable throwable) {
        super(throwable);
    }
}

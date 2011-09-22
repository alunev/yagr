package com.alunev.android.yagr.exception;

public class YagrException extends Exception {
    private static final long serialVersionUID = -7032207537332547817L;

    public YagrException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public YagrException(String detailMessage) {
        super(detailMessage);
    }

    public YagrException(Throwable throwable) {
        super(throwable);
    }
}

package com.alunev.android.yagr.service;

import android.content.Context;

public interface IReader {
    public void initializeAuthentication(Context context, IReaderListener callback);
}

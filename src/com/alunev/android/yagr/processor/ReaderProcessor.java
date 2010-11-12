package com.alunev.android.yagr.processor;

import java.util.List;

import com.alunev.android.yagr.rest.RestClient;

public class ReaderProcessor {
    public List<String> getReaderFeeds(String authToken) {
        return new RestClient().getReaderFeeds(authToken);
    }
}

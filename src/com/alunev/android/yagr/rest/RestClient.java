package com.alunev.android.yagr.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.alunev.android.yagr.datasource.info.Feed;
import com.alunev.android.yagr.info.Settings;

public class RestClient {
    public List<Feed> getReaderFeeds(String authToken, String authSeret) {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Settings.CONSUMER_KEY,
                Settings.CONSUMER_SECRET);
        consumer.setTokenWithSecret(authToken, authSeret);

        HttpUriRequest request = new HttpGet(ReaderAPICalls.LIST_FEEDS + "?"
                + ReaderAPIParams.OUTPUT + "json&"
                + ReaderAPIParams.TIMESTAMP + System.currentTimeMillis() + "&"
                + ReaderAPIParams.CLIENT + ReaderAPIParams.VALUE_CLIENT);

        try {
            consumer.sign(request);
        } catch (OAuthMessageSignerException e) {
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            e.printStackTrace();
        }

        HttpClient httpClient = new DefaultHttpClient();
        String response = null;
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            response = httpClient.execute(request, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Feed> feeds = new ArrayList<Feed>();
        JSONTokener tokener = new JSONTokener(response);
        try {
            JSONObject obj = (JSONObject) tokener.nextValue();
            JSONArray subscriptions = obj.getJSONArray("subscriptions");
            for (int i = 0; i < subscriptions.length(); i++) {
                feeds.add(new Feed(subscriptions.getJSONObject(i).getString("title")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return feeds;
    }
}

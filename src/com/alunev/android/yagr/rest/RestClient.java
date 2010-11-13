package com.alunev.android.yagr.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.PostMethod;


public class RestClient {

    public List<String> getReaderFeeds(String authToken) {
        List<String> res = new ArrayList<String>();

        return res;
    }

    public List<String> getReaderFeeds1(String authToken) {
        HttpClient httpClient = new HttpClient();
/*
        HttpMethod method = new GetMethod("http://www.google.com/reader/atom/subscription/list?output=xml&ck="
            + System.currentTimeMillis() + "&client=apachehttpclient");

        HttpMethod method = new GetMethod("http://www.google.com/reader/api/0/subscription/list"
                + "?output=xml&" + "&ck=" + System.currentTimeMillis() + "&client=123123123");
                */

        HttpMethod method = new PostMethod("https://www.google.com/reader/atom/user/-/state/com.google/read");
        // HttpMethod method = new PostMethod("http://www.google.com/reader/atom/feed/" +
        // 	    "http://xkcd.com/rss.xml?n=17&ck=1169900000&xt=user/-/state/com.google/read");

        // Cookie
        HttpState state = new HttpState();
        Cookie cookie = new Cookie(".google.com", "SID", authToken, "/", 1600000000, true);
        state.addCookie(cookie);
        httpClient.setState(state);

        /*
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        method.setRequestHeader("Cookie", "SID=" + authToken
            + ";domain=.google.com"
            + ";path=/"
            + ";expires=1600000000");

        method.setRequestHeader("Cookie", "domain=.google.com");
        method.setRequestHeader("Cookie", "path=/");
        method.setRequestHeader("Cookie", "expires=1600000000");
        */

        /*
        HttpMethod method = new PostMethod("https://www.google.com/accounts/ClientLogin");
        method.getParams().setParameter("accountType", "GOOGLE");
        method.getParams().setParameter("Email", "antonluneyv@gmail.com");
        method.getParams().setParameter("service", "gr");
        method.getParams().setParameter("logintoken", authToken);
        */

        try {
            int httpRes = httpClient.executeMethod(method);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String redirect = "https://www.google.com/accounts/ServiceLogin?service=reader&passive=1209600&continue=https://www.google.com/reader/atom/user/-/state/com.google/read&followup=https://www.google.com/reader/atom/user/-/state/com.google/read";
        method = new PostMethod(redirect);
        httpClient.setState(state);

        try {
            int httpRes = httpClient.executeMethod(method);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> res = new ArrayList<String>();
        try {
            byte[] responseBody = method.getResponseBody();
            res.add(new String(responseBody));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }

        return res;
    }
}

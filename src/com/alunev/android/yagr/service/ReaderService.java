package com.alunev.android.yagr.service;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.alunev.android.yagr.info.Settings;


public class ReaderService extends Service {
    private final ReaderServiceBinder readerBinder = new ReaderServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return readerBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return super.onStartCommand(intent, flags, startId);
    }

    public class ReaderServiceBinder extends Binder implements IReader {
        public void initializeAuthentication(final Context context, IReaderListener callback) {
            // perform stuff in separate thread
            Runnable worker = new Runnable() {
                public void run() {
                    // here we do authentication, request access to google reader
                    AccountManager am = AccountManager.get(context);
                    Account[] accounts = am.getAccountsByType("com.google");

                    // we leave system to handle situation when user has to enter login/password
                    AccountManagerFuture<Bundle> accountManagerFuture = am.getAuthToken(
                            accounts[0], "reader", true, null, null);

                    Bundle authTokenBundle = null;
                    try {
                        authTokenBundle = accountManagerFuture.getResult();
                    } catch (OperationCanceledException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (AuthenticatorException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    String authToken = null;
                    if (authTokenBundle != null) {
                        authToken = authTokenBundle.get(AccountManager.KEY_AUTHTOKEN).toString();
                    }

                    // save/update token
                    if (authToken != null) {
                        SharedPreferences preferences = getApplicationContext().
                            getSharedPreferences(Settings.SETTINGS_FILE_NAME, MODE_PRIVATE);
                        Editor editor = preferences.edit();
                        editor.putString(Settings.READER_AUTH_TOKEN, authToken);
                        editor.commit();
                    }
                }
            };

            Thread workerThread = new Thread(worker);
            workerThread.start();

            // wait till done and then notify callbacker
            try {
                workerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                callback.done();
            }
        }
    }
}

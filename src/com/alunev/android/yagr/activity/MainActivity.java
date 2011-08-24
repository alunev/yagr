package com.alunev.android.yagr.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.alunev.android.yagr.info.ActivityIntents;
import com.alunev.android.yagr.info.Settings;
import com.alunev.android.yagr.service.ReaderServiceHelper;

public class MainActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initiate our service
        ReaderServiceHelper.getInstance().init(getApplication());

        // process to workflow
        SharedPreferences preferences = getSharedPreferences(Settings.SETTINGS_FILE_NAME, MODE_PRIVATE);
        if (preferences == null || preferences.getBoolean(Settings.IS_FIRST_LAUNCH, true)) {
            // launch authentication wizard
            Intent startSetup = new Intent(ActivityIntents.welcomeIntentAction);
            startActivity(startSetup);
        } else {
            // launch main screen(feeds list view)
            Intent startFeeds = new Intent(ActivityIntents.feedsIntentAction);
            startActivity(startFeeds);
        }
    }
}
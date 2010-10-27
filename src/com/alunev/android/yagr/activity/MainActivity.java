package com.alunev.android.yagr.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.alunev.android.yagr.R;
import com.alunev.android.yagr.info.ActivityIntents;
import com.alunev.android.yagr.info.Settings;

public class MainActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(Settings.SETTINGS_FILE_NAME, MODE_WORLD_READABLE);
        if (preferences == null || preferences.getBoolean(Settings.IS_FIRST_LAUNCH, true)) {
            Intent startSetup = new Intent(ActivityIntents.welcomeIntentAction);
            startActivity(startSetup);
        } else {
            setContentView(R.layout.main);
        }
    }
}
package com.alunev.android.yagr.activity;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.alunev.android.yagr.R;
import com.alunev.android.yagr.info.Settings;

public class FeedsActivity extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load feeds from Google Reader
        // get auth token
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(
                Settings.SETTINGS_FILE_NAME, MODE_PRIVATE);
        String authToken = preferences.getString(Settings.READER_AUTH_TOKEN, null);

        if (authToken != null) {
            setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
                    new String[] {
                        "qweqwe", "qweqwe", "qweqwe", "qweqwe",
                        "qweqwe", "qweqwe", "qweqwe", "qweqwe"}));
        }
    }
}

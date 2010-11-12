package com.alunev.android.yagr.activity;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.alunev.android.yagr.R;
import com.alunev.android.yagr.service.IReaderListener;
import com.alunev.android.yagr.service.ReaderServiceHelper;

public class FeedsActivity extends ListActivity implements IReaderListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
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
        */
        List<String> res = ReaderServiceHelper.getInstance().getReaderFeeds(this);

        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, res.toArray(new String[]{})));
    }

    @Override
    public void done() {
        // TODO Auto-generated method stub
    }
}

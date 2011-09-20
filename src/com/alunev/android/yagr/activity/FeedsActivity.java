package com.alunev.android.yagr.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alunev.android.yagr.R;
import com.alunev.android.yagr.datasource.info.Feed;
import com.alunev.android.yagr.service.IReaderListener;
import com.alunev.android.yagr.service.ReaderServiceHelper;

public class FeedsActivity extends ListActivity implements IReaderListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> labels = loadFeeds();

        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, labels.toArray(new String[]{})));
    }

    private List<String> loadFeeds() {
        List<Feed> feeds = ReaderServiceHelper.getInstance().getReaderFeeds(this);
        List<String> labels = new ArrayList<String>();
        for (Feed feed : feeds) {
            labels.add(feed.getTitle());
        }
        return labels;
    }

    public void done() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
    }
}

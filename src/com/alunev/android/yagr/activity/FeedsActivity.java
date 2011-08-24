package com.alunev.android.yagr.activity;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alunev.android.yagr.R;
import com.alunev.android.yagr.ds.FeedsDao;
import com.alunev.android.yagr.ds.FeedsOpenHelper;
import com.alunev.android.yagr.ds.info.Feed;
import com.alunev.android.yagr.service.IReaderListener;
import com.alunev.android.yagr.service.ReaderServiceHelper;

public class FeedsActivity extends ListActivity implements IReaderListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FeedsDao dao = new FeedsDao((new FeedsOpenHelper(this)).getFreshDatabase());

        // load feeds from Google Reader
        List<String> res = ReaderServiceHelper.getInstance().getReaderFeeds(this);

        // try to save to DB
        Feed feedInfo = new Feed();
        for (String str : res) {
            feedInfo.setTitle(str);
            feedInfo.setUnreadCount(0);
            dao.insertNewFeed(feedInfo);
        }

        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, res.toArray(new String[]{})));
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

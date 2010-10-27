package com.alunev.android.yagr.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.alunev.android.yagr.R;
import com.alunev.android.yagr.info.ActivityIntents;
import com.alunev.android.yagr.service.IReaderListener;
import com.alunev.android.yagr.service.ReaderServiceHelper;

public class WelcomeActivity extends Activity implements IReaderListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome);

        final Button activateButton = (Button) findViewById(R.id.activateButton);
        final Button nextButton = (Button) findViewById(R.id.nextButton);

        ReaderServiceHelper.getInstance().init(getApplication());

        activateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // activate button clicked - so activate(perform initial authentication)
                // i.e. obtain auth key that we need
                ReaderServiceHelper.getInstance().initializeAuthentication(WelcomeActivity.this);
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startFeeds = new Intent(ActivityIntents.feedsIntentAction);
                startActivity(startFeeds);
            }
        });
    }

    @Override
    public void done() {
        final Button activateButton = (Button) findViewById(R.id.activateButton);
        final Button nextButton = (Button) findViewById(R.id.nextButton);

        // hide Activate button and show Next button
        activateButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.VISIBLE);
    }
}

package com.alunev.android.yagr.activity;


import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
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

        activateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // activate button clicked - so activate(perform initial authentication)
                // i.e. obtain auth key that we need

                try {
                    ReaderServiceHelper.getInstance().initializeAuthentication(WelcomeActivity.this);
                } catch (OAuthMessageSignerException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (OAuthNotAuthorizedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (OAuthExpectationFailedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (OAuthCommunicationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent startFeeds = new Intent(ActivityIntents.feedsIntentAction);
                startActivity(startFeeds);
            }
        });
    }

    public void done() {
        final Button activateButton = (Button) findViewById(R.id.activateButton);
        final Button nextButton = (Button) findViewById(R.id.nextButton);

        // hide Activate button and show Next button
        activateButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.VISIBLE);
    }
}

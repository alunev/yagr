package com.alunev.android.yagr.activity;

import oauth.signpost.OAuth;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.alunev.android.yagr.R;
import com.alunev.android.yagr.exception.OAuthYagrException;
import com.alunev.android.yagr.info.ActivityIntents;
import com.alunev.android.yagr.service.IReaderListener;
import com.alunev.android.yagr.service.ReaderServiceHelper;

public class AuthSuccessActivity extends Activity implements IReaderListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_success);

        final Button finishButton = (Button) findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent startFeeds = new Intent(ActivityIntents.feedsIntentAction);
                startActivity(startFeeds);
            }
        });

        String token = getIntent().getData().getQueryParameter(OAuth.OAUTH_TOKEN);
        String verifCode = getIntent().getData().getQueryParameter(OAuth.OAUTH_VERIFIER);

        try {
            ReaderServiceHelper.getInstance().finalizeAuthentication(this, token, verifCode);
        } catch (OAuthYagrException e) {
            throw new RuntimeException("Not handled properly yet!!!", e);
        }
    }

    public void done() {

    }
}

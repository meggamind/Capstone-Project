package com.example.aniket.capstone_project.ui.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.auth0.android.Auth0;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.result.Credentials;
import com.example.aniket.capstone_project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.token)
    TextView token;
    private Lock mLock;


    private LockCallback callback = new AuthenticationCallback() {
        @Override
        public void onAuthentication(Credentials credentials) {
            Timber.d("Aniket1 in onAuthentication");
        }

        @Override
        public void onCanceled() {
        }

        @Override
        public void onError(LockException error) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonClicked() {
        token.setText("Not logged in");
        Auth0 auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);
        mLock = Lock.newBuilder(auth0, callback)
                .withAudience("https://travel-log.auth0.com/userinfo")
                .build(this);

        startActivity(mLock.newIntent(this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLock.onDestroy(this);
        mLock = null;
    }

}

package bree.com.bewwweibo.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import bree.com.bewwweibo.Constants.BWConstants;
import bree.com.bewwweibo.Constants.PrepKey;
import bree.com.bewwweibo.R;
import bree.com.bewwweibo.utils.LogUtil;
import bree.com.bewwweibo.utils.LoginState;
import bree.com.bewwweibo.utils.MyPreference;

/**
 * 登录页面
 */
public class LandingPageActivity extends BaseActivity {
    private SsoHandler mSsoHandler;
    private AuthInfo mAuthInfo;
    private MyPreference myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToorBarX().hide();
        myPreference=MyPreference.getInstance(this);
        mAuthInfo = new AuthInfo(this, BWConstants.APP_KEY, BWConstants.REDIRECT_URL, BWConstants.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (LoginState.isLogin()){
                    startActivity(new Intent(LandingPageActivity.this,HomePageActivity.class));
                    finish();
                }else {

                    mSsoHandler.authorize(new WeiboAuthListener() {
                        @Override
                        public void onComplete(Bundle bundle) {
                            Oauth2AccessToken oauth2AccessToken = Oauth2AccessToken.parseAccessToken(bundle);
                            myPreference.write(PrepKey.LOGIN_TOKEN, new Gson().toJson(oauth2AccessToken));
                            myPreference.write(PrepKey.LOGIN,true);
                            startActivity(new Intent(LandingPageActivity.this,HomePageActivity.class));
                            finish();

                        }

                        @Override
                        public void onWeiboException(WeiboException e) {
                            e.printStackTrace();
                        }
                        @Override
                        public void onCancel() {

                        }
                    });
                }
            }
        },500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_landing_page;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtil.d(" LandingPageActivity  onDestroy login token : "+myPreference.read(PrepKey.LOGIN_TOKEN));
        super.onDestroy();
    }
}

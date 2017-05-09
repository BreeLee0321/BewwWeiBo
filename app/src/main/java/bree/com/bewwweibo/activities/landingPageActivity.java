package bree.com.bewwweibo.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import bree.com.bewwweibo.R;

public class LandingPageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToorBarX().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LandingPageActivity.this,HomePageActivity.class));
                finish();
            }
        },500);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_landing_page;
    }
}

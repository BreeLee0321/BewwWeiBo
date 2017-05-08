package bree.com.bewwweibo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bree.com.bewwweibo.R;

/**
 * Created by bree on 2017/5/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RelativeLayout rlContent;
    private ToorBarX toorBarX;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activiy_base);
        initView();
        View v = getLayoutInflater().inflate(getLayoutId(), rlContent, false);
        rlContent.addView(v);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        rlContent = (RelativeLayout) findViewById(R.id.rlContent);


    }

    public ToorBarX getToorBarX() {
        if (null == toorBarX)
            toorBarX = new ToorBarX(mToolbar, this);
        return toorBarX;
    }

    public abstract int getLayoutId();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anmi_in_right_left, R.anim.anmi_out_right_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anmi_out_right_left, R.anim.anmi_in_right_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.anmi_out_right_left, R.anim.anmi_in_right_left);
    }
}

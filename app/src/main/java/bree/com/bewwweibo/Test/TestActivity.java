package bree.com.bewwweibo.Test;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import bree.com.bewwweibo.R;
import bree.com.bewwweibo.activities.BaseActivity;
import bree.com.bewwweibo.activities.ToorBarX;

public class TestActivity extends BaseActivity {

    private ToorBarX toorBarX;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this,Test2Activity.class));
            }
        });

        toorBarX = getToorBarX();
        toorBarX.setTitle("title");
        toorBarX.setSubTitle("suntitle");


    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }
}

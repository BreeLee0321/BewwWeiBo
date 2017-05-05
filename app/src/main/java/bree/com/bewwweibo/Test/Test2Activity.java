package bree.com.bewwweibo.Test;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import bree.com.bewwweibo.R;
import bree.com.bewwweibo.activities.BaseActivity;

public class Test2Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test2;
    }
}

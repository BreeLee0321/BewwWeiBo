package bree.com.bewwweibo.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by bree on 2017/5/10.
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}

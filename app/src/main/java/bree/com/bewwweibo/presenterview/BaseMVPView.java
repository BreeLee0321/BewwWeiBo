package bree.com.bewwweibo.presenterview;

import android.app.Activity;
import android.content.Context;

/**
 * Created by bree on 2017/6/5.
 */

public interface BaseMVPView {
    Activity getActivity();
    void fail(String error);
}

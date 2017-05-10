package bree.com.bewwweibo.utils;

import bree.com.bewwweibo.Constants.PrepKey;
import bree.com.bewwweibo.application.MyApplication;

/**
 * Created by bree on 2017/5/10.
 */

public class LoginState {
    public static boolean isLogin(){
        return MyPreference.getInstance(MyApplication.getContext()).readBoolean(PrepKey.LOGIN);
    }
}

package bree.com.bewwweibo.utils;

import bree.com.bewwweibo.application.MyApplication;

/**
 * Created by bree on 2017/5/10.
 */

public class ToastUtil {
    private static android.widget.Toast toast;
    public static void ShowToast(String message){
        if (toast==null){
            toast= android.widget.Toast.makeText(MyApplication.getContext(),message, android.widget.Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }
}

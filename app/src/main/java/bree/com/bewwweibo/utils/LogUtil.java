package bree.com.bewwweibo.utils;

/**
 * Created by bree on 2017/5/10.
 */

public class LogUtil {
    //	public static boolean enDEBUG = false;
    public static boolean enDEBUG = true;

    static public void d(String Msg) {
//		android.util.Log.d("AMP",Msg);
        if (enDEBUG) {
            android.util.Log.d("BREE", Msg);
        }
    }

    static public void i(String Msg) {
//		android.util.Log.i("AMP",Msg);
        if (enDEBUG) {
            android.util.Log.i("BREE", Msg);
        }
    }

    static public void e(String Msg) {
//		android.util.Log.e("AMP",Msg);
        if (enDEBUG) {
            android.util.Log.e("BREE", Msg);
        }
    }

    static public void w(String Msg) {
//		android.util.Log.w("AMP",Msg);
        if (enDEBUG) android.util.Log.w("BREE", Msg);
    }

    //-------------------------------------------------------可分类的log-------------------------------------------------------------------------------//

    static public void d(String TAG, String Msg) {
        if (enDEBUG) {
            android.util.Log.d("BREE-"+TAG, Msg);
        }
    }

    static public void i(String TAG, String Msg) {
        if (enDEBUG) {
            android.util.Log.i("BREE-"+TAG, Msg);
        }
    }

    static public void e(String TAG, String Msg) {
        if (enDEBUG) {
            android.util.Log.e("BREE-"+TAG, Msg);
        }
    }

    static public void w(String TAG, String Msg) {
        if (enDEBUG) android.util.Log.w("BREE-"+TAG, Msg);
    }
}

package bree.com.bewwweibo.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import bree.com.bewwweibo.Constants.PrepKey;

/**
 * Created by bree on 2017/5/10.
 */

public class WeiBoTokenUtil {
    public static String  getToken(MyPreference myPreference){
        String json = myPreference.read(PrepKey.LOGIN_TOKEN,"");
        if(TextUtils.isEmpty(json)){
            return null;
        }
        String token = new Gson().fromJson(json, Oauth2AccessToken.class).getToken();
        return token;
    }
}

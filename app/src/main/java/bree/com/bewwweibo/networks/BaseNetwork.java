package bree.com.bewwweibo.networks;

import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

import org.json.JSONObject;

import bree.com.bewwweibo.entities.HttpResponse;

/**
 * Created by bree on 2017/5/10.
 */

public abstract class BaseNetwork {
    private AsyncWeiboRunner asyncWeiboRunner;
    private String url;

    public BaseNetwork(Context context,String url) {
        asyncWeiboRunner=new AsyncWeiboRunner(context);
        this.url=url;
    }

    private RequestListener requestListener=new RequestListener() {
        @Override
        public void onComplete(String s) {
            HttpResponse httpResponse = new HttpResponse();
            JsonParser parser=new JsonParser();
            JsonElement element = parser.parse(s);
            boolean success=false;
            if (element.isJsonObject()) {
                JsonObject object=element.getAsJsonObject();
                if (object.has("error_code")){
                    httpResponse.code=object.get("error_code").getAsInt();
                }
                if (object.has("error")){
                    httpResponse.message=object.get("error").getAsString();
                }
                if (object.has("statuses")){
                    httpResponse.response=object.get("statuses").toString();
                    success=true;
                }else if (object.has("users")){
                    httpResponse.response=object.get("users").toString();
                    success=true;
                }else if (object.has("comments")){
                    httpResponse.response=object.get("comments").toString();
                    success=true;
                }else {
                    httpResponse.response=s;
                    success=true;
                }

            }
            onFinish(httpResponse,success);
        }

        @Override
        public void onWeiboException(WeiboException e) {
            HttpResponse httpResponse = new HttpResponse();
            httpResponse.message=e.getMessage();
            onFinish(httpResponse,false);
        }
    };

    public void get(){
        asyncWeiboRunner.requestAsync(url,setWeiboParameters(),"GET",requestListener);

    }
    public void post(){
        asyncWeiboRunner.requestAsync(url,setWeiboParameters(),"POST",requestListener);
    }
    public void delete(){
        asyncWeiboRunner.requestAsync(url,setWeiboParameters(),"DELETE",requestListener);
    }

    abstract public WeiboParameters setWeiboParameters();
    abstract public void onFinish(HttpResponse httpResponse,boolean success);
}

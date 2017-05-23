package bree.com.bewwweibo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sina.weibo.sdk.api.share.Base;
import com.sina.weibo.sdk.net.WeiboParameters;

import bree.com.bewwweibo.Constants.BWConstants;
import bree.com.bewwweibo.R;
import bree.com.bewwweibo.entities.HttpResponse;
import bree.com.bewwweibo.networks.BWUrls;
import bree.com.bewwweibo.networks.BaseNetwork;
import bree.com.bewwweibo.networks.ParameterKeySet;
import bree.com.bewwweibo.utils.MyPreference;
import bree.com.bewwweibo.utils.WeiBoTokenUtil;
import de.greenrobot.event.EventBus;

public class RePostActivity extends BaseActivity {

    private EditText editText;
    private String action;
    private String ID;
    private String content;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        action = getIntent().getAction();
        ID = getIntent().getStringExtra(ParameterKeySet.ID);
        content = getIntent().getStringExtra(ParameterKeySet.STATUS);
        switch (action) {
            case "REPOST"://转发
                getToorBarX().setTitle("转发微博");
                url = BWUrls.STATUS_REPOST;
                break;
            case "COMMENT": //评论
                getToorBarX().setTitle("评论微博");
                url = BWUrls.COMMENT_CREATE;
                break;
        }


    }

    private void post(final String string) {
        new BaseNetwork(this, url) {
            @Override
            public WeiboParameters setWeiboParameters() {
                WeiboParameters weiboParameters = new WeiboParameters(BWConstants.APP_KEY);
                weiboParameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, WeiBoTokenUtil.getToken(MyPreference.getInstance(RePostActivity.this)));
                if (action.equals("REPOST")) {
                    weiboParameters.put(ParameterKeySet.STATUS, string);

                } else {
                    weiboParameters.put(ParameterKeySet.COMMENT, string);

                }
                weiboParameters.put(ParameterKeySet.ID, ID);
                return weiboParameters;
            }

            @Override
            public void onFinish(HttpResponse httpResponse, boolean success) {
                if (success) {
                    EventBus.getDefault().post("onFinish");
                    finish();
                }
            }
        }.post();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_re_post;
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.editText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_repost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        post(editText.getText().toString());
        return true;
    }
}

package bree.com.bewwweibo.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.sina.weibo.sdk.net.WeiboParameters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bree.com.bewwweibo.Constants.BWConstants;
import bree.com.bewwweibo.CustomView.PullToRefreshRecyclerView;
import bree.com.bewwweibo.R;
import bree.com.bewwweibo.adapters.ArticCommentAdapter;
import bree.com.bewwweibo.entities.CommentEntity;
import bree.com.bewwweibo.entities.HttpResponse;
import bree.com.bewwweibo.entities.StatusEntity;
import bree.com.bewwweibo.networks.BWUrls;
import bree.com.bewwweibo.networks.BaseNetwork;
import bree.com.bewwweibo.networks.ParameterKeySet;
import bree.com.bewwweibo.utils.DividerItemDecoration;
import bree.com.bewwweibo.utils.MyPreference;
import bree.com.bewwweibo.utils.WeiBoTokenUtil;

public class ArticleCommentActivity extends BaseActivity {
    private StatusEntity statusEntity;
    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager layoutManager;
    private DividerItemDecoration itemDecoration;
    private ArticCommentAdapter adapter;
    private List<CommentEntity> mDataSet;
    private int page=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusEntity= (StatusEntity) getIntent().getSerializableExtra(StatusEntity.class.getSimpleName());
        layoutManager=new LinearLayoutManager(context);
        mDataSet=new ArrayList<CommentEntity>();
        getToorBarX().setTitle(getString(R.string.title_activity_article_comment));
        initView();
        loadData();
    }
    private void initView(){
        rlv = (PullToRefreshRecyclerView) findViewById(R.id.rlv);
        rlv.setLayoutManager(layoutManager);
        itemDecoration = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
        rlv.addItemDecoration(itemDecoration);
        adapter=new ArticCommentAdapter(context,statusEntity,mDataSet);
        rlv.setAdapter(adapter);
        rlv.setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    public int getLayoutId() {
        return R.layout.v_common_recyclerview;
    }

    private void loadData() {
        new BaseNetwork(context,BWUrls.COMMENT_SHOW) {

            @Override
            public WeiboParameters setWeiboParameters() {
                WeiboParameters parameters = new WeiboParameters(BWConstants.APP_KEY);
                parameters.put(ParameterKeySet.ID, statusEntity.id);
                parameters.put(ParameterKeySet.PAGE, page);
                parameters.put(ParameterKeySet.COUNT, 10);
                parameters.put(ParameterKeySet.AUTH_ACCESS_TOKEN, WeiBoTokenUtil.getToken(MyPreference.getInstance(context)));
                return parameters;
            }

            @Override
            public void onFinish(HttpResponse response, boolean success) {
                if (success) {
                    Type type = new TypeToken<ArrayList<CommentEntity>>() {
                    }.getType();
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(response.response);
                    if (element.isJsonArray()) {
                        List<CommentEntity> temp = new Gson().fromJson(element, type);
//                        if (!loadMore) {
                            mDataSet.clear();
//                        }
                        mDataSet.addAll(temp);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }.get();
    }
}

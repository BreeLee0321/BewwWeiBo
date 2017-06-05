package bree.com.bewwweibo.activities;

import android.app.Activity;
import android.content.Context;
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
import bree.com.bewwweibo.presenter.ArticcleCommentPrecenterImpl;
import bree.com.bewwweibo.presenterview.ArticleCommentView;
import bree.com.bewwweibo.utils.DividerItemDecoration;
import bree.com.bewwweibo.utils.MyPreference;
import bree.com.bewwweibo.utils.ToastUtil;
import bree.com.bewwweibo.utils.WeiBoTokenUtil;

public class ArticleCommentActivity extends BaseActivity implements ArticleCommentView{
    private StatusEntity statusEntity;
    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager layoutManager;
    private DividerItemDecoration itemDecoration;
    private ArticCommentAdapter adapter;
    private List<CommentEntity> mDataSet;
    private ArticcleCommentPrecenterImpl articleCommentPrecenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleCommentPrecenter=new ArticcleCommentPrecenterImpl(this);
        statusEntity= articleCommentPrecenter.getEntity();
        layoutManager=new LinearLayoutManager(context);
        mDataSet=new ArrayList<CommentEntity>();
        getToorBarX().setTitle(getString(R.string.title_activity_article_comment));
        initView();
         articleCommentPrecenter.loadData();
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


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void fail(String reason) {
        ToastUtil.ShowToast(reason);
        rlv.onRefreshComplete();
    }

    @Override
    public void sucess(List<CommentEntity> entity) {
        rlv.onRefreshComplete();
        if (null!=entity&&entity.size()>0){
            mDataSet.clear();
            mDataSet.addAll(entity);
            adapter.notifyDataSetChanged();
        }
    }
}

package bree.com.bewwweibo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.constant.WBPageConstants;
import com.sina.weibo.sdk.net.WeiboParameters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bree.com.bewwweibo.Constants.BWConstants;
import bree.com.bewwweibo.CustomView.PullToRefreshRecyclerView;
import bree.com.bewwweibo.R;
import bree.com.bewwweibo.adapters.HomePageAdapter;
import bree.com.bewwweibo.entities.HttpResponse;
import bree.com.bewwweibo.entities.StatusEntity;
import bree.com.bewwweibo.networks.BWUrls;
import bree.com.bewwweibo.networks.BaseNetwork;
import bree.com.bewwweibo.presenter.HomePresenterImpl;
import bree.com.bewwweibo.presenterview.HomePresenterView;
import bree.com.bewwweibo.utils.DividerItemDecoration;
import bree.com.bewwweibo.utils.LogUtil;
import bree.com.bewwweibo.utils.MyPreference;
import bree.com.bewwweibo.utils.ToastUtil;
import bree.com.bewwweibo.utils.WeiBoTokenUtil;
import de.greenrobot.event.EventBus;

/**
 * Created by bree on 2017/5/8.
 * 获取最新动态
 * 参考文档 http://open.weibo.com/wiki/2/statuses/public_timeline
 */

public class HomeFragment extends BaseFragment implements HomePresenterView {
    private MyPreference myPreference;

    private PullToRefreshRecyclerView rlv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private HomePageAdapter adapter;
    private HomePresenterImpl homePresenter;
    private List<StatusEntity> total;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPreference = MyPreference.getInstance(context);
        EventBus.getDefault().register(this);
        homePresenter = new HomePresenterImpl(this);
        total=new ArrayList<StatusEntity>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rlv = (PullToRefreshRecyclerView) inflater.inflate(R.layout.v_common_recyclerview, container, false);
        init();
        homePresenter.loadData();
        return rlv;
    }
    private void init() {
        layoutManager = new LinearLayoutManager(context);
        rlv.getRefreshableView().setLayoutManager(layoutManager);
        itemDecoration = new DividerItemDecoration(context, LinearLayoutManager.VERTICAL);
        rlv.getRefreshableView().addItemDecoration(itemDecoration);
        adapter = new HomePageAdapter(total, context);
        rlv.getRefreshableView().setAdapter(adapter);
        rlv.setMode(PullToRefreshBase.Mode.BOTH);
        rlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                homePresenter.loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                homePresenter.loadMore();
            }
        });
        adapter.setOnItemClickListener(new HomePageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                LogUtil.e(position + "");
            }
        });
    }

    public void onEventMainThread(Object event) {
        if (event instanceof Integer) {
            switch ((int) event) {
                case R.id.action_one:
                    homePresenter.requestHomeTimeLine();
                    break;
                case R.id.action_two:
                    homePresenter.requestUserTimeLine();
                    break;
            }
        }

        if (event instanceof String) {
            homePresenter.loadData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void sucess(List<StatusEntity> entity) {
        if (entity!=null&&entity.size()>0){
        total.clear();
        total.addAll(entity);
    }
    adapter.notifyDataSetChanged();
    rlv.onRefreshComplete();
}

    @Override
    public void fail(String reason) {
        ToastUtil.ShowToast(reason);
        rlv.onRefreshComplete();
    }
}

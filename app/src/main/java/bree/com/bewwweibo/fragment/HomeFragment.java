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
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.constant.WBPageConstants;
import com.sina.weibo.sdk.net.WeiboParameters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bree.com.bewwweibo.Constants.BWConstants;
import bree.com.bewwweibo.R;
import bree.com.bewwweibo.adapters.HomePageAdapter;
import bree.com.bewwweibo.entities.HttpResponse;
import bree.com.bewwweibo.entities.StatusEntity;
import bree.com.bewwweibo.networks.BWUrls;
import bree.com.bewwweibo.networks.BaseNetwork;
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

public class HomeFragment extends BaseFragment {
    private MyPreference myPreference;

    private RecyclerView rlv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private List<StatusEntity> total;
    private HomePageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPreference = MyPreference.getInstance(context);
        total=new ArrayList<>();
        EventBus.getDefault().register(this);
//        IntentFilter filter=new IntentFilter();
//        filter.addAction("change");
//        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                setData(BWUrls.USER_TIME_LINE);
//            }
//        },filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rlv= (RecyclerView) inflater.inflate(R.layout.v_common_recyclerview, container, false);
        init();
        setData(BWUrls.HOME_TIME_LINE);
        return rlv;
    }

    private void setData(String url) {
        new BaseNetwork(context, url) {
            @Override
            public WeiboParameters setWeiboParameters() {
                WeiboParameters weiboParameters = new WeiboParameters(BWConstants.APP_KEY);
                weiboParameters.put(WBConstants.AUTH_ACCESS_TOKEN, WeiBoTokenUtil.getToken(myPreference));
                weiboParameters.put("page",1);
                weiboParameters.put("count",10);
                return weiboParameters;
            }
            @Override
            public void onFinish(HttpResponse httpResponse, boolean success) {
                if (success) {
                    List<StatusEntity> list = new ArrayList<>();
                    Type type = new TypeToken<ArrayList<StatusEntity>>() {
                    }.getType();
                    list = new Gson().fromJson(httpResponse.response, type);
                    if (list!=null&&list.size()>0){
                        total.clear();
                        total.addAll(list);
                    }
                    adapter.notifyDataSetChanged();

//                    LogUtil.d("onFinish  success "+httpResponse.response);
                } else {
                    LogUtil.d("onFinish error "+httpResponse.message);
                    ToastUtil.ShowToast(httpResponse.message);
                }
            }
        }.get();
    }

    private void init() {
        layoutManager=new LinearLayoutManager(context);
        rlv.setLayoutManager(layoutManager);
        itemDecoration=new DividerItemDecoration(context,LinearLayoutManager.VERTICAL);
        rlv.addItemDecoration(itemDecoration);
        adapter =  new HomePageAdapter(total,context );
        rlv.setAdapter(adapter);
        adapter.setOnItemClickListener(new HomePageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                LogUtil.e(position+"");
            }
        });
    }
    public void onEventMainThread(Integer event){
        switch (event){
            case R.id.action_one:
                setData(BWUrls.HOME_TIME_LINE);
                break;
            case R.id.action_two:
                setData(BWUrls.USER_TIME_LINE);
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

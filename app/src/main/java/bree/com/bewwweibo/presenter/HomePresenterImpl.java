package bree.com.bewwweibo.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.net.WeiboParameters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bree.com.bewwweibo.Constants.BWConstants;
import bree.com.bewwweibo.entities.HttpResponse;
import bree.com.bewwweibo.entities.StatusEntity;
import bree.com.bewwweibo.networks.BWUrls;
import bree.com.bewwweibo.networks.BaseNetwork;
import bree.com.bewwweibo.presenterview.HomePresenterView;
import bree.com.bewwweibo.utils.LogUtil;
import bree.com.bewwweibo.utils.MyPreference;
import bree.com.bewwweibo.utils.WeiBoTokenUtil;

/**
 * Created by bree on 2017/5/23.
 */

public class HomePresenterImpl implements HomePresenter{
    private String  url= BWUrls.HOME_TIME_LINE;
    private int page =1;
    private int count =1;
    private MyPreference myPreference;
    private HomePresenterView homePresenterView;
    private List<StatusEntity> total;

    public HomePresenterImpl(HomePresenterView homePresenterView){
        this.homePresenterView=homePresenterView;
        myPreference=MyPreference.getInstance(homePresenterView.getActivity());
        total=new ArrayList<StatusEntity>();
    }
    @Override
    public void loadData() {
        page =1;
        setData(false);
    }

    @Override
    public void loadMore() {
        page++;
        LogUtil.d(this.getClass().getSimpleName()+" loadMore page ="+page);
        setData(true);
    }

    @Override
    public void requestHomeTimeLine() {
        url= BWUrls.HOME_TIME_LINE;
        setData(false);
    }

    @Override
    public void requestUserTimeLine() {
        url= BWUrls.USER_TIME_LINE;
        setData(false);
    }

    private void setData(final boolean loadMore) {
        new BaseNetwork(homePresenterView.getActivity(), url) {
            @Override
            public WeiboParameters setWeiboParameters() {
                WeiboParameters weiboParameters = new WeiboParameters(BWConstants.APP_KEY);
                weiboParameters.put(WBConstants.AUTH_ACCESS_TOKEN, WeiBoTokenUtil.getToken(myPreference));
                weiboParameters.put("page",page);
                weiboParameters.put("count",count);
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
                        if (!loadMore){
                            total.clear();
                        }
                        total.addAll(list);
                    }
                    homePresenterView.sucess(total);
                    LogUtil.d("onFinish  success "+httpResponse.response);
                } else {
                    homePresenterView.fail(httpResponse.message);
                    LogUtil.d("onFinish error "+httpResponse.message);
//                    ToastUtil.ShowToast(httpResponse.message);
                }
            }
        }.get();
    }
}

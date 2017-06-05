package bree.com.bewwweibo.presenter;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.net.WeiboParameters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bree.com.bewwweibo.Constants.BWConstants;
import bree.com.bewwweibo.activities.ArticleCommentActivity;
import bree.com.bewwweibo.entities.CommentEntity;
import bree.com.bewwweibo.entities.HttpResponse;
import bree.com.bewwweibo.entities.StatusEntity;
import bree.com.bewwweibo.networks.BWUrls;
import bree.com.bewwweibo.networks.BaseNetwork;
import bree.com.bewwweibo.networks.ParameterKeySet;
import bree.com.bewwweibo.presenterview.ArticleCommentView;
import bree.com.bewwweibo.presenterview.BaseMVPView;
import bree.com.bewwweibo.utils.MyPreference;
import bree.com.bewwweibo.utils.WeiBoTokenUtil;

/**
 * Created by bree on 2017/6/5.
 */

public class ArticcleCommentPrecenterImpl implements ArticleCommentPresenter {
    private List<CommentEntity> list;
    private Activity context;
    private ArticleCommentView mvpView;
    private int page=1;

    public ArticcleCommentPrecenterImpl(BaseMVPView mvpView) {
        this.mvpView = (ArticleCommentView) mvpView;
        this.context=(Activity)mvpView.getActivity();
        list=new ArrayList<>();
    }

    @Override
    public void loadData() {
        page=1;
        getData(false);
    }

    @Override
    public void loadMore() {
        page++;
        getData(true);
    }

    private void getData(final boolean loadMore) {
        new BaseNetwork(context, BWUrls.COMMENT_SHOW) {

            @Override
            public WeiboParameters setWeiboParameters() {
                WeiboParameters parameters = new WeiboParameters(BWConstants.APP_KEY);
                parameters.put(ParameterKeySet.ID, getEntity().id);
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
                        if (!loadMore)
                            list.clear();
                        list.addAll(temp);
                        mvpView.sucess(list);
                    }
                }
            }
        }.get();
    }


    @Override
    public StatusEntity getEntity() {
        return (StatusEntity)context.getIntent().getSerializableExtra(StatusEntity.class.getSimpleName());
    }
}

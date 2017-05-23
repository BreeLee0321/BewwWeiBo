package bree.com.bewwweibo.CustomView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import bree.com.bewwweibo.utils.LogUtil;


/**
 * Created by bree on 2017/5/19.
 */

public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView>{
    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView=new RecyclerView(context,attrs);
        recyclerView.setId(com.handmark.pulltorefresh.library.R.id.recyclerview);
        return recyclerView;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        if (mRefreshableView.getChildCount() == 0) {
            return true;
        }
        //当前视图item数量
        int count = mRefreshableView.getChildCount();
        View view = mRefreshableView.getChildAt(count - 1);
        int position = mRefreshableView.getChildLayoutPosition(view);
        LogUtil.d("position"+position+"");
        LogUtil.d("mRefreshableView.getAdapter().getItemCount()"+mRefreshableView.getAdapter().getItemCount()+"");
       if (position+1==mRefreshableView.getAdapter().getItemCount()){
           LogUtil.d("view.getBottom()"+view.getBottom()+"");
           LogUtil.d("mRefreshableView.getBottom()"+mRefreshableView.getBottom()+"");
           return view.getBottom() <= mRefreshableView.getBottom();
       }
        return false;
    }

    @Override
    protected boolean isReadyForPullStart() {
        //当前可视界面条目数
        if (mRefreshableView.getChildCount()==0){
            return  true;
        }
        //获得当前可视条目第一个
        View view = mRefreshableView.getChildAt(0);
        int childPosition = mRefreshableView.getChildLayoutPosition(view);
        if (childPosition==0){
            if (view.getTop()==mRefreshableView.getTop()) {
                return true;
            }
        }

        return false;
    }
}

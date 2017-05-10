package bree.com.bewwweibo.CustomView;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import bree.com.bewwweibo.R;

/**
 * Created by bree on 2017/5/5.
 */

public class ToorBarX {
    private Toolbar toolbar;
    private AppCompatActivity mActivity;
    private ActionBar actionBar;
    private RelativeLayout rlCustom;

    public ToorBarX(Toolbar toolbar, final AppCompatActivity mActivity) {
        this.toolbar = toolbar;
        this.mActivity = mActivity;
        rlCustom= (RelativeLayout) toolbar.findViewById(R.id.rlCustom);
        mActivity.setSupportActionBar(toolbar);
        actionBar = mActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }

    public ToorBarX setTitle(String title){
        actionBar.setTitle(title);
        return this;
    }

    public ToorBarX setTitle(int resId){
        actionBar.setTitle(resId);
        return this;
    }
    public ToorBarX setSubTitle(int resId){
        actionBar.setSubtitle(resId);
        return this;
    }
    public ToorBarX setSubTitle(String subTitle){
        actionBar.setSubtitle(subTitle);
        return this;
    }

    public ToorBarX setNavigationOnClickListener(View.OnClickListener listener){
        toolbar.setNavigationOnClickListener(listener);
        return this;
    }

    public ToorBarX setNavigationIcon(int resId){
        toolbar.setNavigationIcon(resId);
        return  this;
    }
     public ToorBarX setDispalyHoneAsUpEnabled(boolean show){
         actionBar.setDisplayHomeAsUpEnabled(show);
         return  this;
     }

    public ToorBarX setCustomView(View v){
        rlCustom.removeAllViews();
        rlCustom.addView(v);
        return this;
    }

    public ToorBarX hide(){
        actionBar.hide();
        return this;
    }

}

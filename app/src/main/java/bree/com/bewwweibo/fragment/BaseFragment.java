package bree.com.bewwweibo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import bree.com.bewwweibo.R;

/**
 * Created by bree on 2017/5/5.
 */

public class BaseFragment extends Fragment{
    public Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Override
    public void startActivity(Intent intent) {
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anmi_in_right_left,R.anim.anmi_out_right_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        getActivity().startActivityForResult(intent,requestCode);
        getActivity().overridePendingTransition(R.anim.anmi_in_right_left,R.anim.anmi_out_right_left);
    }
}

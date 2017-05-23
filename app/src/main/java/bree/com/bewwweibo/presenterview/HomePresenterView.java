package bree.com.bewwweibo.presenterview;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import bree.com.bewwweibo.entities.StatusEntity;

/**
 * Created by bree on 2017/5/23.
 */

public interface HomePresenterView {
    Context getContext();
    void sucess(List<StatusEntity> entity);
    void fail(String reason);
}

package bree.com.bewwweibo.presenterview;

import android.content.Context;

import java.util.List;

import bree.com.bewwweibo.entities.CommentEntity;
import bree.com.bewwweibo.entities.StatusEntity;

/**
 * Created by bree on 2017/6/5.
 */

public interface ArticleCommentView extends BaseMVPView{
    void sucess(List<CommentEntity> entity);
}

package bree.com.bewwweibo.presenter;

/**
 * Created by bree on 2017/5/23.
 */

public interface HomePresenter extends BasePresenter{

    /**s
     * 加载Home数据
     */
    void requestHomeTimeLine();
    /**
     * 加载User数据
     */
    void requestUserTimeLine();


}

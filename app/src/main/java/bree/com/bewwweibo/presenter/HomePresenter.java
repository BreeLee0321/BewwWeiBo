package bree.com.bewwweibo.presenter;

/**
 * Created by bree on 2017/5/23.
 */

public interface HomePresenter {
    /**
     * 加载数据
     */
    void loadData();

    /**
     * 加载更多数据
     */
    void loadMore();
    /**
     * 加载Home数据
     */
    void requestHomeTimeLine();
    /**
     * 加载User数据
     */
    void requestUserTimeLine();


}

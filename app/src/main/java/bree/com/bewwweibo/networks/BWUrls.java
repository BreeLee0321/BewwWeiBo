package bree.com.bewwweibo.networks;

/**
 * Created by bree on 2017/5/10.
 */

public class BWUrls {


    private static final String WEIBO_BASE_URL ="https://api.weibo.com/2/";
    public static final String HOME_TIME_LINE =WEIBO_BASE_URL+"statuses/home_timeline.json";
    public static final String USER_TIME_LINE =WEIBO_BASE_URL+"statuses/user_timeline.json";
    public static final String STATUS_REPOST =WEIBO_BASE_URL+"statuses/repost.json";
    public static final String COMMENT_CREATE =WEIBO_BASE_URL+"comments/create.json";

}

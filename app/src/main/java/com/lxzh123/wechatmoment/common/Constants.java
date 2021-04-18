package com.lxzh123.wechatmoment.common;

/**
 * description: 测试使用配置数据
 * author:      Created by lxzh on 2021/4/18.
 */
public class Constants {
    public final static String LOG_TAG = "WeChatMoment";
    /**
     * Request user info from url
     */
    public final static String USER_INFO_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith";
    /**
     * Request tweets from url
     */
    public final static String TWEETS_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets";
    /**
     * user info assets path
     */
    public final static String ASSET_USER_INFO_PATH = "Test/user/jsmith.json";
    /**
     * tweet assets path
     */
    public final static String ASSET_TWEETS_PATH = "Test/tweet/jsmith_tweets.json";

    /**
     * Timeout for requesting all tweets from network
     */
    public final static int DEFAULT_LOAD_TIMEOUT = 3000;

    /**
     * Load all tweets in memory at first time, and get 5 of them each time from memory asynchronously
     */
    public final static int LOAD_TWEET_COUNT = 5;
    /**
     * Show 5 more while user pulling up the view at the bottom of view
     */
    public final static int PULL_UP_SHOW_MORE_COUNT = 5;
    /**
     * Pulling down the view to refresh, only first 5 items are shown after refreshing
     */
    public final static int PULL_DOWN_REFRESH_SHOW_TWEET_COUNT = 5;
    /**
     * 文件读写默认编码类型
     */
    public final static String APP_DEFAULT_FILE_ENCODING = "UTF-8";
    /**
     * 朋友圈状态图片默认加载缩放系数
     */
    public final static float IMG_THUMB_SCALRE = 0.2f;
    /**
     * 朋友圈状态图片最大显示列数
     */
    public final static int TWEET_IMG_GRIDE_MAX_COLUMN = 3;
}

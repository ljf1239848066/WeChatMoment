package com.lxzh123.wechatmoment.utils;

/**
 * description: 测试使用配置数据
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class TestUtil {
    /**
     * Request user info from url
     */
    public final static String USER_INFO_URL="http://thoughtworks-ios.herokuapp.com/user/jsmith";
    /**
     * Request tweets from url
     */
    public final static String TWEETS_URL="http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets";
    /**
     * user info assets path
     */
    public final static String ASSET_USER_INFO_PATH="Test/user/jsmith.json";
    /**
     * tweet assets path
     */
    public final static String ASSET_TWEETS_PATH="Test/tweet/jsmith_tweets.json";

    /**
     * Load all tweets in memory at first time, and get 5 of them each time from memory asynchronously
     */
    public final static int LOAD_TWEET_COUNT = 5;
    /**
     * Show 5 more while user pulling up the view at the bottom of view
     */
    public final static int PULL_UP_SHOW_MORE_COUNT=5;
    /**
     * Pulling down the view to refresh, only first 5 items are shown after refreshing
     */
    public final static int PULL_DOWN_REFRESH_SHOW_TWEET_COUNT=5;
}

package com.lxzh123.wechatmoment.loader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxzh123.wechatmoment.common.Constants;
import com.lxzh123.wechatmoment.holder.AppHolder;
import com.lxzh123.wechatmoment.model.Tweet;
import com.lxzh123.wechatmoment.utils.AssetsUtils;
import com.lxzh123.wechatmoment.utils.Common;
import com.lxzh123.wechatmoment.utils.HttpUtils;
import com.lxzh123.wechatmoment.utils.LogUtils;
import com.lxzh123.wechatmoment.utils.ThreadPoolUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * description: 朋友圈记录加载器
 * author:      Created by lxzh on 2021/4/19.
 */
public class TweetLoader {
    public interface TweetLoaderListener {
        void onLoadFinished(List<Tweet> tweets);
    }

    private static List<Tweet> mTweetList;


    public TweetLoader() {
        mTweetList = new ArrayList<>();
    }

    /**
     * 初始化状态列表
     */
    public void asyncLoadData(final TweetLoaderListener listener) {
        ThreadPoolUtils.get().execute(() -> {
            loadData0(new WeakReference<>(listener));
        });
    }

    /**
     * 加载更多记录
     */
    public void asyncLoadMore(final int currentCnt, final TweetLoaderListener listener) {
        ThreadPoolUtils.get().execute(() -> {
            try {
                // 当前直接从内存筛选更多记录
                // 此处模拟耗时加载
                Thread.sleep(randomTimeout(Constants.DEFAULT_LOAD_TIMEOUT));
            } catch (Exception e) {
                e.printStackTrace();
            }
            loadMore0(currentCnt, new WeakReference<TweetLoaderListener>(listener));
        });
    }

    private void loadData0(WeakReference<TweetLoaderListener> listener) {
        if (listener == null || listener.get() == null) {
            return;
        }
        String tweetStr = null;
        try {
            tweetStr = HttpUtils.SyncRequestGet(Constants.TWEETS_URL, randomTimeout(1000, 2000));
        } catch (IOException ioe) {
            LogUtils.e("loadData0 IOException:" + ioe.toString());
        }
        if (Common.isNullOrEmpty(tweetStr)) {
            LogUtils.i("Request tweet from the internet failed! Use local assets instead.");
            tweetStr = AssetsUtils.ReadAssetTxtContent(AppHolder.getContext(), Constants.ASSET_TWEETS_PATH);
        }
        List<Tweet> tweetAll;
        try {
            Gson gson = new Gson();
            tweetAll = gson.fromJson(tweetStr, new TypeToken<List<Tweet>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            tweetAll = new ArrayList<>();
        }
        int cnt = tweetAll.size();
        for (int i = cnt - 1; i >= 0; i--) {
            if (tweetAll.get(i) == null || tweetAll.get(i).isEmptyTweet()) {
                tweetAll.remove(i);
            }
        }
        synchronized (this) {
            mTweetList.clear();
            mTweetList.addAll(tweetAll);
        }

        cnt = tweetAll.size();
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < Constants.LOAD_TWEET_COUNT && i < cnt; i++) {
            tweets.add(tweetAll.get(i));
        }

        LogUtils.i("Initialize tweet list data finished!");
        if (listener == null || listener.get() == null) {
            return;
        }
        listener.get().onLoadFinished(tweets);
    }

    private synchronized void loadMore0(int currentCnt, WeakReference<TweetLoaderListener> listener) {
        int max = Math.min(mTweetList.size(), currentCnt + Constants.LOAD_TWEET_COUNT);
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            tweets.add(mTweetList.get(i));
        }
        if (listener == null || listener.get() == null) {
            return;
        }
        listener.get().onLoadFinished(tweets);
    }

    public synchronized boolean hasMore(int currentCnt) {
        return currentCnt < mTweetList.size();
    }

    public static int randomTimeout(int max) {
        Random random = new Random();
        int randomTimeout = random.nextInt(2000);
        return randomTimeout;
    }

    public static int randomTimeout(int min, int max) {
        Random random = new Random();
        int randomTimeout = min + random.nextInt(max - min);
        return randomTimeout;
    }
}

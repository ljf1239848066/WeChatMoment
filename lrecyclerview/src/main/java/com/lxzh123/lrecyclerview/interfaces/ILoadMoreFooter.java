package com.lxzh123.lrecyclerview.interfaces;

import android.view.View;

/**
 * description: 加载更多FooterView需要实现的接口
 * author:     Created by lxzh on 2021/4/19
 */
public interface ILoadMoreFooter {
    /**
     * 状态回调，回复初始设置
     */
    void onReset();

    /**
     * 状态回调，加载中
     */
    void onLoading();

    /**
     * 状态回调，加载完成
     */
    void onComplete();

    /**
     * 状态回调，已全部加载完成
     */
    void onNoMore();

    /**
     * 加载更多的View
     */
    View getFootView();

    void setOnClickLoadMoreListener(final OnLoadMoreListener listener);

    enum State {
        Normal/**正常*/
        , ManualLoadMore/**手动点击加载*/
        , NoMore/**加载到最底了*/
        , Loading/**加载中..*/
    }
}


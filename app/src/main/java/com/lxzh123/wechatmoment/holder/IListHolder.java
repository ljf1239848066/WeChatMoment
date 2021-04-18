package com.lxzh123.wechatmoment.holder;

public interface IListHolder {
    /**
     * 指定布局 方式三，支持动态指定布局
     * @return
     */
    int getLayoutId();

    void initView();

    void refresh();
}

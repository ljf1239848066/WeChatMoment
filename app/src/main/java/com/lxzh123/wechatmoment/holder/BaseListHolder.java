package com.lxzh123.wechatmoment.holder;

import android.view.View;

import androidx.annotation.IdRes;

import com.lxzh123.wechatmoment.adapter.BaseListAdapter;

/**
 * description: 基础列表视图缓存器
 * author:      Created by lxzh on 2021/4/19.
 */
public abstract class BaseListHolder implements IListHolder{

    protected BaseListAdapter mOwner;
    private View mView;
    private int mIndex;

    public int getIndex() {
        return mIndex;
    }

    public BaseListHolder(BaseListAdapter owner, View root, int index) {
        this.mOwner = owner;
        this.mView = root;
        this.mIndex = index;
        initView();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    public abstract void refresh();

    public final <T extends View> T findViewById(@IdRes int id) {
        return mView.findViewById(id);
    }
}

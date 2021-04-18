package com.lxzh123.wechatmoment.holder;

import android.view.View;

import androidx.annotation.IdRes;

public abstract class BaseListHolder {

    private View mView;
    private int mIndex;

    public int getIndex() {
        return mIndex;
    }

    public BaseListHolder(View root, int index) {
        this.mView = root;
        this.mIndex = index;
    }

    public abstract void refresh();

    public final <T extends View> T findViewById(@IdRes int id) {
        return mView.findViewById(id);
    }
}

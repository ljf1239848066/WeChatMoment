package com.lxzh123.wechatmoment.holder;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.lxzh123.wechatmoment.listener.BaseListener;
import com.lxzh123.wechatmoment.model.BaseBean;

/**
 * description: 基础RecyclerView列表项缓存器
 * author:      Created by lxzh on 2021/4/18.
 */
public abstract class BaseRecyclerHolder extends RecyclerView.ViewHolder implements IListHolder{
    protected Context mContext;
    View mView;
    BaseBean mItem;
    public int mIndex;
    protected BaseListener mListener;

    static boolean mBlockChanged = false;

    public static boolean isBlockChanged() {
        return mBlockChanged;
    }

    public static void setBlockChanged(boolean blockChanged) {
        BaseRecyclerHolder.mBlockChanged = blockChanged;
    }

    public View getRootView() {
        return mView;
    }

    protected void setExtraDataSet(String[] dataSets) {

    }

    public void setListener(BaseListener listener) {
        this.mListener = listener;
    }

    public BaseRecyclerHolder(View root) {
        super(root);
    }

    public BaseRecyclerHolder(View root, Context context) {
        this(root);
        mContext = context;
        mView = root;
        initView();
    }

    public void bindView(BaseBean item, int index) {
        mItem = item;
        mIndex = index;
        refresh();
    }

    public void bindView(BaseBean item, int index, String[] dataSets) {
        setExtraDataSet(dataSets);
        bindView(item, index);
    }

    public final <T extends View> T findViewById(int id) {
        return mView.findViewById(id);
    }

    public enum HolderType {
        Tweet,
        Comment
    }
}

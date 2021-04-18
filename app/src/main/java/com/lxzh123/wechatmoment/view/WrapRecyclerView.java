package com.lxzh123.wechatmoment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.lxzh123.wechatmoment.adapter.RecyclerWrapAdapter;

import java.util.ArrayList;

/**
 * description: 带头尾的 RecyclerView
 * author:      Created by lxzh on 2021/4/18.
 */
public class WrapRecyclerView extends RecyclerView {

    private ArrayList<View> mHeaderViews = new ArrayList<>();

    private ArrayList<View> mFootViews = new ArrayList<>();

    private Adapter mAdapter;

    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addHeaderView(View view) {
        mHeaderViews.clear();
        mHeaderViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof RecyclerWrapAdapter)) {
                mAdapter = new RecyclerWrapAdapter(mHeaderViews, mFootViews, mAdapter);
            }
        }
    }

    public void addFootView(View view) {
        mFootViews.clear();
        mFootViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof RecyclerWrapAdapter)) {
                mAdapter = new RecyclerWrapAdapter(mHeaderViews, mFootViews, mAdapter);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        Adapter tmpAdapter = adapter;
        if (mHeaderViews.isEmpty() && mFootViews.isEmpty()) {
        } else {
            tmpAdapter = new RecyclerWrapAdapter(mHeaderViews, mFootViews, adapter);
        }
        mAdapter = tmpAdapter;
        super.setAdapter(tmpAdapter);
    }
}
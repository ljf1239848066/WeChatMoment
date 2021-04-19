package com.lxzh123.wechatmoment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lxzh123.wechatmoment.holder.BaseRecyclerHolder;
import com.lxzh123.wechatmoment.holder.BaseRecyclerHolder.HolderType;
import com.lxzh123.wechatmoment.holder.RecyclerViewHolderFactory;
import com.lxzh123.wechatmoment.model.BaseBean;
import com.lxzh123.wechatmoment.model.Comment;
import com.lxzh123.wechatmoment.model.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * description: RecyclerView 公共适配器
 * author:      Created by lxzh on 2021/4/19.
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerHolder> {
    Context mContext;
    List<BaseBean> mItems;

    public void setItems(List<BaseBean> items) {
        this.mItems.clear();
        if (items != null) {
            this.mItems.addAll(items);
        }
    }

    public BaseRecyclerAdapter(Context context, List<BaseBean> items) {
        this.mContext = context;
        if (items == null) {
            this.mItems = new ArrayList<>();
        } else {
            this.mItems = items;
        }
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HolderType holderType = HolderType.values()[viewType];
        View view = LayoutInflater.from(mContext).inflate(RecyclerViewHolderFactory.getLayoutId(holderType), parent, false);
        BaseRecyclerHolder viewHolder = RecyclerViewHolderFactory.buildHolder(holderType, view, mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        BaseBean item = mItems.get(position);
        holder.bindView(item, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        BaseBean item = mItems.get(position);
        HolderType holderType = HolderType.Tweet;
        if(item instanceof Tweet){
            holderType = HolderType.Tweet;
        } else if(item instanceof Comment) {
            holderType = HolderType.Comment;
        }
        return holderType.ordinal();
    }
}

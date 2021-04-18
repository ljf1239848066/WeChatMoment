package com.lxzh123.wechatmoment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lxzh123.wechatmoment.holder.BaseRecyclerHolder;
import com.lxzh123.wechatmoment.holder.BaseRecyclerHolder.HolderType;
import com.lxzh123.wechatmoment.holder.ConfigViewHolderFactory;
import com.lxzh123.wechatmoment.listener.BaseListener;
import com.lxzh123.wechatmoment.model.BaseBean;
import com.lxzh123.wechatmoment.model.Comment;
import com.lxzh123.wechatmoment.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerHolder> {
    Context mContext;
    List<BaseBean> mItems;
    private BaseListener listener;

    public void setListener(BaseListener listener) {
        this.listener = listener;
    }

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
//        LogUtils.d("onCreateViewHolder viewType:" + viewType);
        HolderType holderType = HolderType.values()[viewType];
        View view = LayoutInflater.from(mContext).inflate(ConfigViewHolderFactory.getLayoutId(holderType), parent, false);
        BaseRecyclerHolder viewHolder = ConfigViewHolderFactory.buildHolder(holderType, view, mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
//        LogUtils.d("onBindViewHolder position:" + position);
        BaseBean item = mItems.get(position);
        holder.bindView(item, position);
        holder.setListener(listener);
    }

    @Override
    public int getItemCount() {
//        LogUtils.d("getItemCount size:" + mItems.size());
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
//        LogUtils.d("getItemViewType position:" + position);
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

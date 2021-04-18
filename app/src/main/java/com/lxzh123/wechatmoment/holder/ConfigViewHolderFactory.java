package com.lxzh123.wechatmoment.holder;

import android.content.Context;
import android.view.View;

import com.lxzh123.wechatmoment.R;

public class ConfigViewHolderFactory {
    public static int getLayoutId(BaseRecyclerHolder.HolderType holderType) {
        int layoutId = 0;
        switch (holderType) {
            case Tweet:
                layoutId = R.layout.layout_tweet_item;
                break;
            case Comment:
                layoutId = R.layout.layout_tweet_comment_item;
                break;
        }
        return layoutId;
    }

    public static BaseRecyclerHolder buildHolder(BaseRecyclerHolder.HolderType holderType, View parent, Context context) {
        BaseRecyclerHolder viewHolder = null;
        switch (holderType) {
            case Tweet:
                viewHolder = new TweetViewHolder(parent, context);
                break;
            case Comment:
                viewHolder = new TweetViewHolder(parent, context);
                break;
        }
        return viewHolder;
    }
}

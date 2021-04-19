package com.lxzh123.wechatmoment.holder;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.adapter.BaseListAdapter;
import com.lxzh123.wechatmoment.model.Comment;
import com.lxzh123.wechatmoment.view.CommentClickableSpan;
import com.lxzh123.wechatmoment.view.CustomLinkTextView;

/**
 * description: 微信朋友圈评论列表缓存器
 * author:      Created by lxzh on 2021/4/19.
 */
public class TweetCommentHolder extends BaseListHolder{
    public CustomLinkTextView linkTextView;

    private Comment mItem;

    public TweetCommentHolder(BaseListAdapter owner, View root, Comment item, int index) {
        super(owner, root, index);
        this.mItem = item;
    }

    @Override
    public void initView() {
        linkTextView = findViewById(R.id.tv_comment_content);
    }

    @Override
    public void refresh() {
        Comment comment = mItem;
        Spanned htmlSpanned = Html.fromHtml(comment.getLinkableString());
        SpannableString spannableString = new SpannableString(htmlSpanned);
        //set click span
        ClickableSpan clickableSpan = new CommentClickableSpan(this.mOwner.getContext(),
                comment.getSender().getUsername());
        spannableString.setSpan(clickableSpan, 0, comment.getSender().getNick().length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        linkTextView.setText(spannableString);
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}

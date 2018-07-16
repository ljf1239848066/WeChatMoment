package com.lxzh123.wechatmoment.view;

import android.content.Context;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lxzh123.wechatmoment.R;

/**
 * description: 自定义TextView的链接点击效果
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class CommentClickableSpan extends ClickableSpan {

    private final static String TAG="CommentClickableSpan";

    private Context context;
    private String username;

    public CommentClickableSpan(Context context, String username) {
        this.context = context;
        this.username=username;
    }

    @Override
    public void onClick(View view) {
        Log.i(TAG,username);
        Toast.makeText(this.context,"Clicked username:"+username,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        // 设置超链接文字颜色
        ds.setColor(this.context.getResources().getColor(R.color.tweet_nickname_color));
        // 去掉超链接的下划线
        ds.setUnderlineText(false);
    }
}

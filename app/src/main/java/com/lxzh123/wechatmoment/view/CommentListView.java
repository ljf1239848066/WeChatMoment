package com.lxzh123.wechatmoment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lxzh123.wechatmoment.R;

/**
 * 自定义ListView, 支持作为子级list进行嵌套使用，嵌套过程中全部展开而不显示滚动条
 */
public class CommentListView extends ListView {
    private Context context;

    public CommentListView(Context context) {
        super(context);
        this.context = context;
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 重新计算列表所有元素的高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ListAdapter listAdapter = this.getAdapter();
        if (listAdapter == null) return;
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, this);
            listItem.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            totalHeight += listItem.getMeasuredHeight();
        }
        totalHeight += this.context.getResources().getDimension(R.dimen.tweet_item_margin) * 2;
        ViewGroup.LayoutParams params = this.getLayoutParams();
        params.height = totalHeight + (this.getDividerHeight() * (listAdapter.getCount() - 1));
        this.setLayoutParams(params);
    }
}

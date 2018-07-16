package com.lxzh123.wechatmoment.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.model.Comment;

import java.util.List;

/**
 * description: 微信朋友圈评论列表适配器
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class TweeCommentAdapter extends BaseAdapter {

    private final static String TAG = "TweeCommentAdapter";

    private Context context;
    private List<Comment> comments;

    public TweeCommentAdapter(Context context,List<Comment> comments){
        this.context=context;
        this.comments=comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.layout_tweet_comment_item, null);
            viewHolder=new ViewHolder();

            viewHolder.linkTextView = (CustomLinkTextView) convertView.findViewById(
                    R.id.tv_comment_content);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        Comment comment = comments.get(i);
        Spanned htmlSpanned = Html.fromHtml(comment.getLinkableString());
        SpannableString spannableString = new SpannableString(htmlSpanned);
        //set click span
        ClickableSpan clickableSpan = new CommentClickableSpan(this.context,
                comment.getSender().getUsername());
        spannableString.setSpan(clickableSpan, 0, comment.getSender().getNick().length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //ReplacementSpan replacementSpan=new RoundBackgroundColorSpan(
        //Color.parseColor("#FFFFFFFF"),Color.parseColor("#005555"));
        //spannableString.setSpan(replacementSpan,0,comment.getSender().getNick().length(),
        //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        viewHolder.linkTextView.setText(spannableString);
        viewHolder.linkTextView.setMovementMethod(LinkMovementMethod.getInstance());

//        Log.i(TAG, "getView:" + comment);
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
    }

    static class ViewHolder{
        CustomLinkTextView linkTextView;
    }
}

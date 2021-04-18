package com.lxzh123.wechatmoment.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.activity.ImageViewerPagerActivity;
import com.lxzh123.wechatmoment.loader.GlideImageLoader;
import com.lxzh123.wechatmoment.model.Tweet;
import com.lxzh123.wechatmoment.model.TweetImage;
import com.lxzh123.wechatmoment.utils.Common;
import com.lxzh123.wechatmoment.view.CommentListView;
import com.lxzh123.wechatmoment.view.ninegridview.NineGirdImageContainer;
import com.lxzh123.wechatmoment.view.ninegridview.NineGridBean;
import com.lxzh123.wechatmoment.view.ninegridview.NineGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 微信朋友圈列表适配器
 * author:      Created by lxzh on 2021/4/18.
 */
public class TweetAdapter extends BaseAdapter implements NineGridView.onItemClickListener {

    private final static String TAG = "TweetAdapter";

    private Context mContext;
    private List<Tweet> mTweets;

    public TweetAdapter(Context context) {
        this.mContext = context;
        mTweets = new ArrayList<Tweet>();
    }

    public List<Tweet> getTweets() {
        return mTweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.mTweets = tweets;
    }

    @Override
    public int getCount() {
        return mTweets.size();
    }

    @Override
    public Object getItem(int i) {
        return mTweets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        TweetViewHolder viewHolder;
        Tweet tweet = mTweets.get(i);
        // load images
        int imgCnt = tweet.getImages().size();

        if (convertView == null || ((TweetViewHolder) convertView.getTag()).imgCnt != imgCnt) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_tweet_item, null);

            viewHolder = new TweetViewHolder(convertView, imgCnt, i);
        } else {
            viewHolder = (TweetViewHolder) convertView.getTag();
        }

        viewHolder.ivTweetAvatar.setImageResource(R.drawable.potral);
        viewHolder.tvNickname.setText(tweet.getSender().getNick());

        if (Common.isNullOrEmpty(tweet.getContent())) {
            //无文本内容时设置影藏, INVISIBLE 控件内容不可见, 但依旧占据空间
            viewHolder.tvTweetContent.setVisibility(View.GONE);
        } else {
            viewHolder.tvTweetContent.setVisibility(View.VISIBLE);
        }
        viewHolder.tvTweetContent.setText(tweet.getContent());

        if (imgCnt > 0) {
            //int columnCnt=imgCnt <= Common.TWEET_IMG_GRIDE_MAX_COLUMN ?
            //        imgCnt : (imgCnt==4?2:Common.TWEET_IMG_GRIDE_MAX_COLUMN);
            // 设置显示列数，默认3列
            //gvTweetImgs.setColumnCount(columnCnt);

            //设置单张图片显示时的宽高比，默认1.0f
            //gvTweetImgs.setSingleImageRatio(0.8f);
            //设置图片显示间隔大小，默认3dp
            viewHolder.gvTweetImages.setSpcaeSize(3);
            //gvTweetImgs.setSingleImageSize(194);
            //设置各类点击监听
            viewHolder.gvTweetImages.setOnItemClickListener(this);

            List<NineGridBean> imgBeans = new ArrayList<NineGridBean>();
            List<TweetImage> imgs = tweet.getImages();
            for (int j = 0; j < imgCnt; j++) {
                NineGridBean bean = new NineGridBean(imgs.get(j).getUrl(), imgs.get(j).getUrl());
                imgBeans.add(bean);
            }
            if (!viewHolder.gvTweetImages.hasImageLoader()) {
                viewHolder.gvTweetImages.setImageLoader(new GlideImageLoader());
            }
            viewHolder.gvTweetImages.setDataList(imgBeans);
            viewHolder.gvTweetImages.setVisibility(View.VISIBLE);
        } else {
            viewHolder.gvTweetImages.setDataList(null);
            viewHolder.gvTweetImages.setVisibility(View.GONE);
        }

        // load comments
        if (tweet.getComments().size() > 0) {
            TweeCommentAdapter adapter = new TweeCommentAdapter(this.mContext, tweet.getComments());
            viewHolder.lvTweetComments.setAdapter(adapter);
            viewHolder.lvTweetComments.setVisibility(View.VISIBLE);
        } else {
            viewHolder.lvTweetComments.setVisibility(View.GONE);
        }
        int size = viewHolder.gvTweetImages.getDataList().size();
        Log.i(TAG, "getView:" + tweet.getContent());
        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
        int size = ((NineGridView) imageContainer.getParent()).getDataSize();
        int[] ids = new int[size];
        for (int i = 0; i < size; i++) {
            ids[i] = GlideImageLoader.IMG_HOLDER_IDS[i];
        }
        Intent intent = new Intent(mContext, ImageViewerPagerActivity.class);
        intent.putExtra(ImageViewerPagerActivity.EXTRA_SELECTED_IMAGE_IDX_TAG, position);
        intent.putExtra(ImageViewerPagerActivity.EXTRA_IMAGES_IDS_TAG, ids);
        mContext.startActivity(intent);
        Log.i(TAG, "startActivity:ImageViewerPagerActivity");
    }

    @Override
    public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

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

    private class TweetViewHolder {
        private ImageView ivTweetAvatar;
        private TextView tvNickname;
        private TextView tvTweetContent;
        private NineGridView gvTweetImages;
        private CommentListView lvTweetComments;
        private int imgCnt;
        private int idx;

        public TweetViewHolder(View convertView, int imgCnt, int idx) {
            this.ivTweetAvatar = convertView.findViewById(R.id.iv_tweet_avatar);
            this.tvNickname = convertView.findViewById(R.id.tv_tweet_nickname);
            this.tvTweetContent = convertView.findViewById(R.id.tv_tweet_text_content);
            this.gvTweetImages = convertView.findViewById(R.id.gv_tweet_image_content);
            this.lvTweetComments = convertView.findViewById(R.id.lv_tweet_comment);
            this.imgCnt = imgCnt;
            this.idx = idx;
        }
    }
}

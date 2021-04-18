package com.lxzh123.wechatmoment.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.activity.ImageViewerPagerActivity;
import com.lxzh123.wechatmoment.adapter.TweeCommentAdapter;
import com.lxzh123.wechatmoment.loader.GlideImageLoader;
import com.lxzh123.wechatmoment.model.Tweet;
import com.lxzh123.wechatmoment.model.TweetImage;
import com.lxzh123.wechatmoment.utils.Common;
import com.lxzh123.wechatmoment.utils.LogUtils;
import com.lxzh123.wechatmoment.view.CommentListView;
import com.lxzh123.wechatmoment.view.ninegridview.NineGirdImageContainer;
import com.lxzh123.wechatmoment.view.ninegridview.NineGridBean;
import com.lxzh123.wechatmoment.view.ninegridview.NineGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 微信朋友圈记录视图缓存器
 * author:      Created by lxzh on 2021/4/18.
 */
public class TweetViewHolder extends BaseRecyclerHolder implements NineGridView.onItemClickListener {
    private ImageView ivTweetAvatar;
    private TextView tvNickname;
    private TextView tvTweetContent;
    private NineGridView gvTweetImages;
    private CommentListView lvTweetComments;
    private int imgCnt;
    private int idx;

    public TweetViewHolder(View root, Context context) {
        super(root, context);
    }

    /**
     * 指定布局 方式三，支持动态指定布局
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {
        this.ivTweetAvatar = findViewById(R.id.iv_tweet_avatar);
        this.tvNickname = findViewById(R.id.tv_tweet_nickname);
        this.tvTweetContent = findViewById(R.id.tv_tweet_text_content);
        this.gvTweetImages = findViewById(R.id.gv_tweet_image_content);
        this.lvTweetComments = findViewById(R.id.lv_tweet_comment);
    }

    @Override
    public void refresh() {
        Tweet tweet = (Tweet) mItem;
        this.imgCnt = tweet.getImages().size();

        ivTweetAvatar.setImageResource(R.drawable.potral);
        tvNickname.setText(tweet.getSender().getNick());

        if (Common.isNullOrEmpty(tweet.getContent())) {
            //无文本内容时设置影藏, INVISIBLE 控件内容不可见, 但依旧占据空间
            tvTweetContent.setVisibility(View.GONE);
        } else {
            tvTweetContent.setVisibility(View.VISIBLE);
        }
        tvTweetContent.setText(tweet.getContent());

        if (imgCnt > 0) {
            //int columnCnt=imgCnt <= Common.TWEET_IMG_GRIDE_MAX_COLUMN ?
            //        imgCnt : (imgCnt==4?2:Common.TWEET_IMG_GRIDE_MAX_COLUMN);
            // 设置显示列数，默认3列
            //gvTweetImgs.setColumnCount(columnCnt);

            //设置单张图片显示时的宽高比，默认1.0f
            //gvTweetImgs.setSingleImageRatio(0.8f);
            //设置图片显示间隔大小，默认3dp
            gvTweetImages.setSpcaeSize(3);
            //gvTweetImgs.setSingleImageSize(194);
            //设置各类点击监听
            gvTweetImages.setOnItemClickListener(this);

            List<NineGridBean> imgBeans = new ArrayList<NineGridBean>();
            List<TweetImage> imgs = tweet.getImages();
            for (int j = 0; j < imgCnt; j++) {
                NineGridBean bean = new NineGridBean(imgs.get(j).getUrl(), imgs.get(j).getUrl());
                imgBeans.add(bean);
            }
            if (!gvTweetImages.hasImageLoader()) {
                gvTweetImages.setImageLoader(new GlideImageLoader());
            }
            gvTweetImages.setDataList(imgBeans);
            gvTweetImages.setVisibility(View.VISIBLE);
        } else {
            gvTweetImages.setDataList(null);
            gvTweetImages.setVisibility(View.GONE);
        }

        // load comments
        if (tweet.getComments().size() > 0) {
            TweeCommentAdapter adapter = new TweeCommentAdapter(this.mContext, tweet.getComments());
            lvTweetComments.setAdapter(adapter);
            lvTweetComments.setVisibility(View.VISIBLE);
        } else {
            lvTweetComments.setVisibility(View.GONE);
        }
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
        LogUtils.i("startActivity:ImageViewerPagerActivity");
    }

    @Override
    public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

    }
}

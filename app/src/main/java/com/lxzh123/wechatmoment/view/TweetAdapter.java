package com.lxzh123.wechatmoment.view;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.activity.ImageViewerPagerActivity;
import com.lxzh123.wechatmoment.model.Tweet;
import com.lxzh123.wechatmoment.utils.AssetsUtil;
import com.lxzh123.wechatmoment.utils.Common;
import com.lxzh123.wechatmoment.utils.GlideImageLoader;
import com.lxzh123.wechatmoment.utils.HttpUtil;
import com.lxzh123.wechatmoment.utils.TestUtil;

import com.alibaba.fastjson.JSON;
import com.lxzh123.wechatmoment.view.ninegridview.NineGirdImageContainer;
import com.lxzh123.wechatmoment.view.ninegridview.NineGridBean;
import com.lxzh123.wechatmoment.view.ninegridview.NineGridView;

import okhttp3.OkHttpClient;

/**
 * description: 微信朋友圈列表适配器
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class TweetAdapter extends BaseAdapter implements NineGridView.onItemClickListener{

    private final static String TAG = "TweetAdapter";

    private Context context;
    private List<Tweet> tweets;
    private OkHttpClient client;

    public TweetAdapter(Context context){
        this.context=context;
        client=new OkHttpClient();
        tweets=new ArrayList<Tweet>();
    }

    /**
     * 初始化状态列表
     */
    public void InitData(){
        String tweetStr=null;
//        try {
//            tweetStr = HttpUtil.SyncRequestGet(client, TestUtil.TWEETS_URL);
//        }catch (IOException ioe){
//            Log.e(TAG, ioe.getStackTrace().toString());
//        }
        if(Common.isNullOrEmpty(tweetStr)){
            Log.i(TAG,"Request tweet from the internet failed! Use local assets instead.");
            tweetStr=AssetsUtil.ReadAssetTxtContent(this.context, "Test/tweet/jsmith_tweets.json");
        }
        tweets=JSON.parseArray(tweetStr,Tweet.class);
        int cnt=tweets.size();
        for (int i=cnt-1;i>=0;i--) {
            if(tweets.get(i)==null||tweets.get(i).IsEmptyTweet()){
                tweets.remove(i);
            }
        }
        Log.i(TAG,"Initilize tweet list data finished!");
        Log.i(TAG,"tweets list data count="+cnt);
    }

    @Override
    public int getCount() {
        return tweets.size();
    }

    @Override
    public Object getItem(int i) {
        return tweets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        TweetViewHolder viewHolder;
        Tweet tweet = tweets.get(i);
        // load images
        int imgCnt = tweet.getImages().size();

        if(convertView==null||((TweetViewHolder)convertView.getTag()).imgCnt!=imgCnt){
            LayoutInflater layoutInflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_tweet_item, null);

            viewHolder=new TweetViewHolder(convertView,imgCnt,i);

        }else{
            viewHolder=(TweetViewHolder) convertView.getTag();
        }

        viewHolder.ivTweetAvatar.setImageResource(R.drawable.potral);
        viewHolder.tvNickname.setText(tweet.getSender().getNick());

        if(Common.isNullOrEmpty(tweet.getContent())){
            //无文本内容时设置影藏, INVISIBLE 控件内容不可见, 但依旧占据空间
            viewHolder.tvTweetContent.setVisibility(View.GONE);
        }else{
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
            viewHolder.gvTweetImgs.setSpcaeSize(3);
            //gvTweetImgs.setSingleImageSize(194);
            //设置各类点击监听
            viewHolder.gvTweetImgs.setOnItemClickListener(this);

            List<NineGridBean> imgBeans=new ArrayList<NineGridBean>();
            List<String> imgs=tweet.getImages();
            for(int j=0;j<imgCnt;j++){
                NineGridBean bean=new NineGridBean(imgs.get(j),imgs.get(j));
                imgBeans.add(bean);
            }
            if(!viewHolder.gvTweetImgs.hasImageLoader()){
                viewHolder.gvTweetImgs.setImageLoader(new GlideImageLoader());
            }
            viewHolder.gvTweetImgs.setDataList(imgBeans);
            viewHolder.gvTweetImgs.setVisibility(View.VISIBLE);
        }else {
            viewHolder.gvTweetImgs.setDataList(null);
            viewHolder.gvTweetImgs.setVisibility(View.GONE);
        }

        // load comments
        if (tweet.getComments().size() > 0) {
            TweeCommentAdapter adapter = new TweeCommentAdapter(this.context, tweet.getComments());
            viewHolder.lvTweetComments.setAdapter(adapter);
            viewHolder.lvTweetComments.setVisibility(View.VISIBLE);
        }else{
            viewHolder.lvTweetComments.setVisibility(View.GONE);
        }
        int size=viewHolder.gvTweetImgs.getDataList().size();
        Log.i(TAG,"getView:"+tweet.getContent());
        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {
        int size=((NineGridView)imageContainer.getParent()).getDataSize();
        int[] ids=new int[size];
        for(int i=0;i<size;i++){
            ids[i]=GlideImageLoader.IMG_HOLDER_IDS[i];
        }
        Intent intent=new Intent(context, ImageViewerPagerActivity.class);
        intent.putExtra(ImageViewerPagerActivity.EXTRA_SELECTED_IMAGE_IDX_TAG,position);
        intent.putExtra(ImageViewerPagerActivity.EXTRA_IMAGES_IDS_TAG,ids);
        context.startActivity(intent);
        Log.i(TAG,"startActivity:ImageViewerPagerActivity");
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

    private class TweetViewHolder{
        private ImageView ivTweetAvatar;
        private TextView tvNickname;
        private TextView tvTweetContent;
        private NineGridView gvTweetImgs;
        private CommentListView lvTweetComments;
        private int imgCnt;
        private int idx;

        public TweetViewHolder(View convertView,int imgCnt, int idx){
            this.ivTweetAvatar = (ImageView) convertView.findViewById(R.id.iv_tweet_avatar);
            this.tvNickname = (TextView) convertView.findViewById(R.id.tv_tweet_nickname);
            this.tvTweetContent = (TextView) convertView.findViewById(R.id.tv_tweet_text_content);
            this.gvTweetImgs = (NineGridView) convertView.findViewById(R.id.gv_tweet_image_content);
            this.lvTweetComments = (CommentListView) convertView.findViewById(R.id.lv_tweet_comment);
            this.imgCnt=imgCnt;
            this.idx=idx;
        }
    }
}

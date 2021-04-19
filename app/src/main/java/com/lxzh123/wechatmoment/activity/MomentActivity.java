package com.lxzh123.wechatmoment.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.lxzh123.lrecyclerview.decoration.DividerDecoration;
import com.lxzh123.lrecyclerview.recyclerview.LRecyclerView;
import com.lxzh123.lrecyclerview.recyclerview.LRecyclerViewAdapter;
import com.lxzh123.lrecyclerview.recyclerview.ProgressStyle;
import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.adapter.BaseRecyclerAdapter;
import com.lxzh123.wechatmoment.loader.TweetLoader;
import com.lxzh123.wechatmoment.model.BaseBean;
import com.lxzh123.wechatmoment.model.Tweet;
import com.lxzh123.wechatmoment.utils.LogUtils;
import com.lxzh123.wechatmoment.utils.WindowUtils;
import com.lxzh123.wechatmoment.view.ClickLoadingFooter;
import com.lxzh123.wechatmoment.view.MomentsRefreshHeader;

import java.util.List;

/**
 * description: 微信朋友圈
 * author:      Created by lxzh on 2021/4/19.
 */
public class MomentActivity extends BaseTitleActivity implements TweetLoader.TweetLoaderListener {
    private final static int INIT_TWEET_FINISHED = 0;

    private MomentsRefreshHeader mHeader;
    private BaseRecyclerAdapter tweetAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private LRecyclerView rvTweetList;
    private ImageView ibProfile;
    private TweetLoader mLoader;

    /** 已经获取到多少条数据了 */
    private int mCurrentCounter = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_moment;
    }

    @Override
    protected void initView() {
        super.initView();
        WindowUtils.setStatusBarTransparent(getWindow());

        mHeader = new MomentsRefreshHeader(this);
        rvTweetList = findViewById(R.id.rv_tweet_list);
        ibProfile = mHeader.getHeaderView().findViewById(R.id.iv_self_portrait);
    }

    @Override
    protected void initData() {
        super.initData();
        setCustomTitle("");


        rvTweetList.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvTweetList.setRefreshHeader(mHeader);

        tweetAdapter = new BaseRecyclerAdapter(this, null);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(tweetAdapter);
        rvTweetList.setAdapter(mLRecyclerViewAdapter);

        DividerDecoration divider = new DividerDecoration.Builder(this).setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.split)
                .build();

        //mRecyclerView.setHasFixedSize(true);
        rvTweetList.addItemDecoration(divider);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTweetList.setLayoutManager(new LinearLayoutManager(this));

        rvTweetList.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        rvTweetList.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        rvTweetList.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        //是否禁用自动加载更多功能,false为禁用, 默认开启自动加载更多功能
        rvTweetList.setLoadMoreEnabled(true);

        //设置头部加载颜色
        rvTweetList.setHeaderViewColor(R.color.colorAccent, R.color.dark, android.R.color.white);
        //设置底部加载颜色
//        rvTweetList.setFooterViewColor(R.color.colorAccent, R.color.dark, android.R.color.white);
        //设置底部加载文字提示
//        rvTweetList.setFooterViewHint("拼命加载中", "我是有底线的", "网络不给力啊，点击再试一次吧");
        ClickLoadingFooter loadingFooter = new ClickLoadingFooter(this);
        rvTweetList.setLoadMoreFooter(loadingFooter, true);
        refresh();
    }

    @Override
    protected void initAction() {
        super.initAction();
        ibProfile.setOnClickListener(v -> {
            Toast.makeText(getBaseContext(), "Hello!", Toast.LENGTH_SHORT).show();
        });
        rvTweetList.setOnRefreshListener(() -> {
            LogUtils.d("onRefresh");
            mCurrentCounter = 0;
            refresh();
        });
        rvTweetList.setOnLoadMoreListener(() -> {
            LogUtils.d("onLoadMore mCurrentCounter:" + mCurrentCounter);
            if (mLoader.hasMore(mCurrentCounter)) {
                LogUtils.d("onLoadMore 1");
                loadMore();
            } else {
                LogUtils.d("onLoadMore 2");
                rvTweetList.setNoMore(true);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void refresh() {
        if (mLoader == null) {
            mLoader = new TweetLoader();
        }
        LogUtils.d("refresh start asyncLoadData");
        mLoader.asyncLoadData(this);
    }

    private void loadMore() {
        if (mLoader == null) {
            mLoader = new TweetLoader();
            mLoader.asyncLoadData(this);
        } else {
            LogUtils.d("loadMore start asyncLoadMore cnt:" + tweetAdapter.getItemCount());
            mLoader.asyncLoadMore(tweetAdapter.getItemCount(), this);
        }
    }

    @Override
    public void onLoadFinished(List<Tweet> tweets) {
        Message message = handler.obtainMessage(INIT_TWEET_FINISHED);
        message.obj = tweets;
        message.sendToTarget();
        LogUtils.d("onLoadFinished tweets:" + tweets.size());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == INIT_TWEET_FINISHED) {
                LogUtils.d("handleMessage msg.obj:" + msg.obj);
                List<BaseBean> tweets = (List<BaseBean>)msg.obj;
                mCurrentCounter = tweets.size();
                tweetAdapter.setItems(tweets);
                tweetAdapter.notifyDataSetChanged();
                rvTweetList.refreshComplete(tweets.size());
//                rvTweetList.setAdapter(mLRecyclerViewAdapter);
            }
            super.handleMessage(msg);
        }
    };

}

package com.lxzh123.wechatmoment.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.*;

import com.lxzh123.wechatmoment.*;
import com.lxzh123.wechatmoment.view.TweetAdapter;


/**
 * description: 微信朋友圈
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class MomentActivity extends AppCompatActivity {

    private TweetAdapter tweetAdapter;
    private ListView lvTweetList;
    private ImageButton ibProfile;
    private final static int INIT_TWEET_FINISHED=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);

        Button btnBack = (Button) findViewById(R.id.btn_title_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MomentActivity.this.finish();
            }
        });

        lvTweetList = (ListView) findViewById(R.id.lv_tweet_list);

        tweetAdapter = new TweetAdapter(getBaseContext());
        lvTweetList.setAdapter(tweetAdapter);
        lvTweetList.setDividerHeight(1);
        lvTweetList.addHeaderView(getLayoutInflater().inflate(R.layout.layout_moment_header, null));

        ibProfile = (ImageButton) findViewById(R.id.iv_self_portrait);

        ibProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Hello!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(handler);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

//    public void onEventMainThread(Object obj) {}
//
//    public void onEventPostThread(Object obj) {}
//
//    public void onEventBackgroundThread(Object obj) {}
//
//    public void onEventAsync(Object obj) {}

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent(Object obj) {
        tweetAdapter.InitData();
//        handler.sendEmptyMessage(INIT_TWEET_FINISHED);
        ((Handler)obj).sendEmptyMessage(INIT_TWEET_FINISHED);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==INIT_TWEET_FINISHED){
                tweetAdapter.notifyDataSetChanged();
            }
            super.handleMessage(msg);
        }
    };

}

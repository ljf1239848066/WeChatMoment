package com.lxzh123.wechatmoment.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lxzh123.wechatmoment.utils.AssetsUtils;
import com.lxzh123.wechatmoment.common.Constants;

import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;

/**
 * alibaba fastjson test, which will execute on an Android device.
 *
 * @see <a href="http://square.github.io/okhttp/">An HTTP & HTTP/2 client for Android and Java applications</a>
 */
@RunWith(AndroidJUnit4.class)
public class OkHttpTest {

    @Test
    public void getUserTest() throws Exception{
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String userStr= AssetsUtils.ReadAssetTxtContent(appContext, Constants.ASSET_USER_INFO_PATH);

        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder().url(Constants.USER_INFO_URL).build();
        String userRequestStr="";
        try(Response response=client.newCall(request).execute()){
            userRequestStr=response.body().string();
        }
        assertEquals(userStr,userRequestStr);
    }

    @Test
    public void hetTweetTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String tweetStr = AssetsUtils.ReadAssetTxtContent(appContext, Constants.ASSET_TWEETS_PATH);

        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder().url(Constants.TWEETS_URL).build();
        String tweetRequestStr="";
        try(Response response=client.newCall(request).execute()){
            tweetRequestStr=response.body().string();
        }
        assertEquals(tweetStr,tweetRequestStr);
    }
}

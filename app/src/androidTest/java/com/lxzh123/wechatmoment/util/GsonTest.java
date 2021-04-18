package com.lxzh123.wechatmoment.util;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxzh123.wechatmoment.common.Constants;
import com.lxzh123.wechatmoment.model.Tweet;
import com.lxzh123.wechatmoment.model.User;
import com.lxzh123.wechatmoment.utils.AssetsUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * alibaba fastjson test, which will execute on an Android device.
 *
 * @see <a href="https://github.com/alibaba/fastjson/wiki/Samples-DataBind">Samples DataBind</a>
 */
@RunWith(AndroidJUnit4.class)
public class GsonTest {

    @Test
    public void gsonUserTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String userStr = AssetsUtils.ReadAssetTxtContent(appContext, Constants.ASSET_USER_INFO_PATH);
        /**
         * As class fields cannot have horizontal lines, so i have to replace it.
         */
        userStr = userStr.replace("profile-image", "profile");
        Gson gson = new Gson();
        User user = gson.fromJson(userStr, User.class);
        assertEquals("jsmith", user.getUsername());
        assertEquals("John Smith", user.getNick());
        assertEquals("http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png", user.getAvatar());
        assertEquals("http://img2.findthebest.com/sites/default/files/688/media/images/Mingle_159902_i0.png", user.getProfile());
    }

    @Test
    public void gsonTweetTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String tweetStr = AssetsUtils.ReadAssetTxtContent(appContext, Constants.ASSET_TWEETS_PATH);
        Gson gson = new Gson();
        List<Tweet> tweets = gson.fromJson(tweetStr, new TypeToken<List<Tweet>>() {
        }.getType());
        Tweet tweet = tweets.get(0);
        assertEquals("沙发！", tweet.getContent());
        assertEquals(3, tweet.getImages().size());
        assertEquals("jport", tweet.getSender().getUsername());
        assertEquals("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w", tweet.getSender().getAvatar());
    }
}

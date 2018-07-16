package com.lxzh123.wechatmoment.model;

import com.lxzh123.wechatmoment.utils.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 朋友圈状态
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class Tweet {
    /**
     * 发状态用户
     */
    private User sender;
    /**
     * 状态文本内容,可选
     */
    private String content;
    /**
     * 状态附图(数量:0-9),可选
     */
    private List<String> images;
    /**
     * 状态评论(数量：不限),可选
     */
    private List<Comment> comments;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * 用于SimpleAdpater绑定List<Map<String xxx,Object obj>>的标签文本
     */
    public final static String TWEET_COMMENT_TAG="COMMENT_TAG";

    public Tweet() {
        this.sender = new User();
        this.content = "";
        this.images = new ArrayList<String>();
        this.comments = new ArrayList<Comment>();
    }

    public Tweet(User sender, String content, List<String> images, List<Comment> comments) {
        this.sender = sender;
        this.content = content;
        this.images = images;
        this.comments = comments;
    }

    /**
     * tweet which does not contain a content and images considered as an empty tweet
     * @return true: empty tweet false: non-empty tweet
     */
    public boolean IsEmptyTweet() {
        return Common.isNullOrEmpty(content) && images.size() == 0;
    }
}

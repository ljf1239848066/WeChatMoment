package com.lxzh123.wechatmoment.model;

/**
 * description: 朋友圈状态评论
 * author:      Created by lxzh on 2021/4/18.
 */
public class Comment extends BaseBean{
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论用户
     */
    private User sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Comment() {
        this.content = "";
        this.sender = null;
    }

    public Comment(String content, User sender) {
        this.content = content;
        this.sender = sender;
    }

    /**
     * 评论列表里用户名以自定义超链接显示, 该函数用于拼接用户昵称与评论内容
     *
     * @return 带超链接的文本, 格式为:[ nick(ID:xxxx): content]
     */
    public String getLinkableString() {
        return String.format("<a href='ID:%s'>%s</a>: %s", sender.getUsername(), sender.getNick(), content);
    }
}

package com.lxzh123.wechatmoment.model;

/**
 * description: 朋友圈用户信息
 * author:      Created by lxzh on 2021/4/19.
 */
public class User {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nick;
    /**
     * 用户头像Url
     */
    private String avatar;
    /**
     * 用户个人照片Url
     */
    private String profile;

    public User() {
        this.username = "";
        this.nick = "";
        this.avatar = "";
        this.profile = "";
    }

    public User(String username, String nick, String avatar) {
        this.username = username;
        this.nick = nick;
        this.avatar = avatar;
        this.profile = "";
    }

    public User(String username, String nick, String avatar, String profile) {
        this.username = username;
        this.nick = nick;
        this.avatar = avatar;
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}

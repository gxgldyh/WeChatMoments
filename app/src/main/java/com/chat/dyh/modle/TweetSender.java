package com.chat.dyh.modle;

/**
 * Created by dengyonghong on 2017/12/12.
 * 朋友圈发起者
 */

public class TweetSender {
    private String username;//姓名
    private String nick;//呢称
    private String avatar;//头像地址

    public TweetSender(String username, String nick, String avatar) {
        this.username = username;
        this.nick = nick;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public String getNick() {
        return nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

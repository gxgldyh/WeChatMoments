package com.chat.dyh.modle;

/**
 * Created by dengyonghong on 2017/12/12.
 */

public class TweetComment {
    private String content;
    private TweetSender sender;

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(TweetSender sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public TweetSender getSender() {
        return sender;
    }
}

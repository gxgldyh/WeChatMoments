package com.chat.dyh.modle;

import com.chat.dyh.bean.CommentItem;
import com.chat.dyh.bean.FavortItem;
import com.chat.dyh.bean.User;

import java.util.List;

/**
 * Created by dengyonghong on 2017/12/12.
 */

public class TweetItmeInfo {
    private String content;//内容
    private List<PhotoInfo> images;//图片链接
    private TweetSender sender;
    private List<TweetComment> comments;
    private List<CommentItem> commentsDatas;//评论
    private User user;
    private List<FavortItem> favortItems;//点赞

    public String getContent() {
        return content;
    }

    public List<PhotoInfo> getImages() {
        return images;
    }

    public TweetSender getSender() {
        return sender;
    }

    public List<TweetComment> getComments() {
        return comments;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImages(List<PhotoInfo> images) {
        this.images = images;
    }

    public void setSender(TweetSender sender) {
        this.sender = sender;
    }

    public void setComments(List<TweetComment> comments) {
        this.comments = comments;
    }

    public void setCommentsDatas(List<CommentItem> commentsDatas) {
        this.commentsDatas = commentsDatas;
    }

    public List<CommentItem> getCommentsDatas() {
        return commentsDatas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<FavortItem> getFavortItems() {
        return favortItems;
    }

    public void setFavortItems(List<FavortItem> favortItems) {
        this.favortItems = favortItems;
    }
}

package com.chat.dyh.presenter;

import android.util.Log;

import com.chat.dyh.Config;
import com.chat.dyh.bean.CommentItem;
import com.chat.dyh.bean.FavortItem;
import com.chat.dyh.bean.User;
import com.chat.dyh.modle.PhotoInfo;
import com.chat.dyh.modle.TweetComment;
import com.chat.dyh.modle.TweetItmeInfo;
import com.chat.dyh.modle.TweetSender;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dengyonghong on 2017/12/12.
 */

public class TweetInterfaceImpl implements TweetContract.Presenter{

    private static int commentId = 0;
    private static int favortId = 0;
    private TweetContract.View mView;
    public static final String[] HEADIMG = {
            "http://img.wzfzl.cn/uploads/allimg/140820/co140R00Q925-14.jpg",
            "http://www.feizl.com/upload2007/2014_06/1406272351394618.png",
            "http://v1.qzone.cc/avatar/201308/30/22/56/5220b2828a477072.jpg%21200x200.jpg",
            "http://v1.qzone.cc/avatar/201308/22/10/36/521579394f4bb419.jpg!200x200.jpg",
            "http://v1.qzone.cc/avatar/201408/20/17/23/53f468ff9c337550.jpg!200x200.jpg",
            "http://cdn.duitang.com/uploads/item/201408/13/20140813122725_8h8Yu.jpeg",
            "http://img.woyaogexing.com/touxiang/nv/20140212/9ac2117139f1ecd8%21200x200.jpg",
            "http://p1.qqyou.com/touxiang/uploadpic/2013-3/12/2013031212295986807.jpg"};


    public static List<PhotoInfo> PHOTOS = new ArrayList<>();//图片集合
    public static List<User> SENDERS= new ArrayList<>();//发送者集合
    static {
        User sender0 = new User("dyh","dengyonghong",HEADIMG[0]);
        User sender1 = new User("dyh1","邓勇红",HEADIMG[1]);
        User sender2 = new User("dyh2","刘二",HEADIMG[2]);
        User sender3 = new User("dyh3","张五常",HEADIMG[3]);
        User sender4 = new User("dyh4","Naoki",HEADIMG[4]);
        User sender5 = new User("dyh5","胡歌",HEADIMG[5]);
        User sender6 = new User("dyh6","赵丽颖",HEADIMG[6]);
        User sender7 = new User("dyh7","刘珊珊",HEADIMG[7]);
        SENDERS.add(sender0);
        SENDERS.add(sender1);
        SENDERS.add(sender2);
        SENDERS.add(sender3);
        SENDERS.add(sender4);
        SENDERS.add(sender5);
        SENDERS.add(sender6);
        SENDERS.add(sender7);

        PhotoInfo p1 = new PhotoInfo();
        p1.url = "http://f.hiphotos.baidu.com/image/pic/item/faf2b2119313b07e97f760d908d7912396dd8c9c.jpg";
        p1.w = 640;
        p1.h = 792;

        PhotoInfo p2 = new PhotoInfo();
        p2.url = "http://g.hiphotos.baidu.com/image/pic/item/4b90f603738da977c76ab6fab451f8198718e39e.jpg";
        p2.w = 640;
        p2.h = 792;

        PhotoInfo p3 = new PhotoInfo();
        p3.url = "http://e.hiphotos.baidu.com/image/pic/item/902397dda144ad343de8b756d4a20cf430ad858f.jpg";
        p3.w = 950;
        p3.h = 597;

        PhotoInfo p4 = new PhotoInfo();
        p4.url = "http://a.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa0fbc1ebfb68f8c5495ee7b8b.jpg";
        p4.w = 533;
        p4.h = 800;

        PhotoInfo p5 = new PhotoInfo();
        p5.url = "http://b.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f4134e61e0f90211f95cad1c85e36.jpg";
        p5.w = 700;
        p5.h = 467;

        PhotoInfo p6 = new PhotoInfo();
        p6.url = "http://c.hiphotos.baidu.com/image/pic/item/7dd98d1001e939011b9c86d07fec54e737d19645.jpg";
        p6.w = 700;
        p6.h = 467;

        PhotoInfo p7 = new PhotoInfo();
        p7.url = "http://pica.nipic.com/2007-10-17/20071017111345564_2.jpg";
        p7.w = 1024;
        p7.h = 640;

        PhotoInfo p8 = new PhotoInfo();
        p8.url = "http://pic4.nipic.com/20091101/3672704_160309066949_2.jpg";
        p8.w = 1024;
        p8.h = 768;

        PhotoInfo p9 = new PhotoInfo();
        p9.url = "http://pic4.nipic.com/20091203/1295091_123813163959_2.jpg";
        p9.w = 1024;
        p9.h = 640;

        PhotoInfo p10 = new PhotoInfo();
        p10.url = "http://pic31.nipic.com/20130624/8821914_104949466000_2.jpg";
        p10.w = 1024;
        p10.h = 768;

        PHOTOS.add(p1);
        PHOTOS.add(p2);
        PHOTOS.add(p3);
        PHOTOS.add(p4);
        PHOTOS.add(p5);
        PHOTOS.add(p6);
        PHOTOS.add(p7);
        PHOTOS.add(p8);
        PHOTOS.add(p9);
        PHOTOS.add(p10);
    }

    public TweetInterfaceImpl(TweetContract.View view) {
        this.mView = view;
    }
    @Override
    public void loadData(int pageIndex,int loadType) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Config.TEST_URL)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //这里是从服务器获取数据后解析json放到列表中，但用你们提供的两个链接都得不到正确数据，用我测试链接能正常拿到（怀疑服务器问题），
                // 为了演示这步中我从本地获取测试数据
//                List<TweetItmeInfo> dataList = parseJSONWithGSON(response.body().string());

            }
        });
        List<TweetItmeInfo> dataList = getListData(pageIndex);
        mView.update2loadData(loadType,pageIndex,dataList);
    }

    private List<TweetItmeInfo> parseJSONWithGSON(String jsonData) {
        Log.i("TweetInterfaceImpl","jsonData:  " + jsonData);
        Gson gson = new Gson();
        List<TweetItmeInfo> dataList = gson.fromJson(jsonData, new TypeToken<List<TweetItmeInfo>>() {}.getType());
        return dataList;
    }

    private List<TweetItmeInfo> getListData(int pageIndex){
        List<TweetItmeInfo> dataList = new ArrayList<TweetItmeInfo>();
        for(int i=0;i<5;i++){
            TweetItmeInfo itmeInfo = new TweetItmeInfo();
            itmeInfo.setContent("朋友圈第 "+pageIndex+" 页," + "第 " + (i+1) + " 条");
            itmeInfo.setImages(createImages());
            itmeInfo.setUser(SENDERS.get(getRandomNum(SENDERS.size())));
            itmeInfo.setCommentsDatas(createComments());
            itmeInfo.setFavortItems(createFavortItemList());
            dataList.add(itmeInfo);
        }
        return dataList;
    }


    private List<PhotoInfo> createImages(){
        List<PhotoInfo> photos = new ArrayList<PhotoInfo>();
        int size = getRandomNum(PHOTOS.size());
        if (size > 0) {
            if (size > 9) {
                size = 9;
            }
            for (int i = 0; i < size; i++) {
                PhotoInfo photo = PHOTOS.get(getRandomNum(PHOTOS.size()));
                if (!photos.contains(photo)) {
                    photos.add(photo);
                } else {
                    i--;
                }
            }
        }
        return photos;
    }

    private List<CommentItem> createComments(){
        List<CommentItem> items = new ArrayList<CommentItem>();
        int size = getRandomNum(10);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                items.add(createComment());
            }
        }
        return items;
    }
    public static CommentItem createComment() {
        CommentItem item = new CommentItem();
        item.setId(String.valueOf(commentId++));
        item.setContent("哈哈,红包nice");
        User user = getUser();
        item.setUser(user);
        if (getRandomNum(10) % 2 == 0) {
            while (true) {
                User replyUser = getUser();
                if (!user.getId().equals(replyUser.getId())) {
                    item.setToReplyUser(replyUser);
                    break;
                }
            }
        }
        return item;
    }

    public static List<FavortItem> createFavortItemList() {
        int size = getRandomNum(SENDERS.size());
        List<FavortItem> items = new ArrayList<FavortItem>();
        List<String> history = new ArrayList<String>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                FavortItem newItem = createFavortItem();
                String userid = newItem.getUser().getId();
                if (!history.contains(userid)) {
                    items.add(newItem);
                    history.add(userid);
                } else {
                    i--;
                }
            }
        }
        return items;
    }

    public static FavortItem createFavortItem() {
        FavortItem item = new FavortItem();
        item.setId(String.valueOf(favortId++));
        item.setUser(getUser());
        return item;
    }

    public static int getRandomNum(int max) {
        Random random = new Random();
        int result = random.nextInt(max);
        return result;
    }

    public static User getUser() {
        return SENDERS.get(getRandomNum(SENDERS.size()));
    }
}

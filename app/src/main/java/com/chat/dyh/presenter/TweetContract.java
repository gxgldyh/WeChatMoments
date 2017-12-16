package com.chat.dyh.presenter;

import com.chat.dyh.modle.TweetItmeInfo;

import java.util.List;

/**
 * Created by dengyonghong on 2017/12/12.
 */

public interface TweetContract {

    interface View extends BaseView{

        void update2loadData(int loadType,int pageIndex, List<TweetItmeInfo> datas);
    }

    interface Presenter extends BasePresenter{
        void loadData(int pageIndex,int loadType);


    }

}

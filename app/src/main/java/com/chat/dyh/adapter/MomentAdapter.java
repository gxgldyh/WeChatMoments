package com.chat.dyh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chat.dyh.R;
import com.chat.dyh.bean.CommentItem;
import com.chat.dyh.bean.FavortItem;
import com.chat.dyh.modle.PhotoInfo;
import com.chat.dyh.modle.TweetItmeInfo;
import com.chat.dyh.presenter.TweetInterfaceImpl;
import com.chat.dyh.utils.GlideCircleTransform;
import com.chat.dyh.utils.UrlUtils;
import com.chat.dyh.view.ExpandTextView;
import com.chat.dyh.view.MultiImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengyonghong on 2017/12/13.
 */

public class MomentAdapter extends BaseRecycleViewAdapter {
    public final static int TYPE_HEAD = 0;

    private static final int STATE_IDLE = 0;

    private TweetInterfaceImpl mTweetPresenter;
    private Context mContext;

    public void setmTweetPresenter(TweetInterfaceImpl mTweetPresenter) {
        this.mTweetPresenter = mTweetPresenter;
    }

    public MomentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEAD;
        }
        int itemType = CircleViewHolder.TYPE_IMAGE;


        return itemType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if(viewType == TYPE_HEAD){
            View headView = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_circle, parent, false);
            viewHolder = new HeaderViewHolder(headView);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_circle_item, parent, false);
            viewHolder = new ImageViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position)==TYPE_HEAD){

        }else {
            final int circlePosition = position - 1;
            final CircleViewHolder holder = (CircleViewHolder) viewHolder;
            final TweetItmeInfo circleItem = (TweetItmeInfo) datas.get(circlePosition);
            String name = circleItem.getUser().getName();//发送者姓名
            String headImg = circleItem.getUser().getHeadUrl();//发送者头像地址
            final String content = circleItem.getContent();
            final List<CommentItem> commentsDatas = circleItem.getCommentsDatas();
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.default_avatar)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(mContext).load(headImg).apply(options).into(holder.headIv);
            holder.commentList.setDatas(commentsDatas);
            holder.commentList.setVisibility(View.VISIBLE);
            holder.nameTv.setText(name);
            if(!TextUtils.isEmpty(content)){
                holder.contentTv.setExpandStatusListener(new ExpandTextView.ExpandStatusListener() {
                    @Override
                    public void statusChange(boolean isExpand) {
                    }
                });

                holder.contentTv.setText(UrlUtils.formatUrlString(content));
            }

            final List<PhotoInfo> photos = circleItem.getImages();
            if (photos != null && photos.size() > 0) {
                ((ImageViewHolder)holder).multiImageView.setVisibility(View.VISIBLE);
                ((ImageViewHolder)holder).multiImageView.setList(photos);
                ((ImageViewHolder)holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        List<String> photoUrls = new ArrayList<String>();
                        for(PhotoInfo photoInfo : photos){
                            photoUrls.add(photoInfo.url);
                        }


                    }
                });
            } else {
                ((ImageViewHolder)holder).multiImageView.setVisibility(View.GONE);
            }
            final List<FavortItem> favortDatas = circleItem.getFavortItems();
            holder.praiseListView.setDatas(favortDatas);
            holder.praiseListView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size()+1;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}

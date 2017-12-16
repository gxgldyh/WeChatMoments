package com.chat.dyh.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dengyonghong on 2017/12/16.
 */
public class DivItemDecoration extends RecyclerView.ItemDecoration {
    private int divHeight;
    private boolean hasHead;
    public DivItemDecoration(int divHeight, boolean hasHead){
        this.divHeight = divHeight;
        this.hasHead = hasHead;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if(hasHead && position == 0){
            return;
        }
        outRect.bottom = divHeight;
    }
}
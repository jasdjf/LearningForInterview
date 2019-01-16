package com.jasdjf.loadpicture;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhuangwei on 2018/3/19.
 */

public class PictureItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public PictureItemDecoration(int space){
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;
        outRect.top = space;
        /*if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = space;
        }*/
    }
}

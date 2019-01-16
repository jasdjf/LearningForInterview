package com.jasdjf.loadpicture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by zhuangwei on 2018/3/19.
 */

public class LoadPictureAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mData;
    private int windowWidth;
    private int pictureWidth;
    private int pictureHeight;
    private PictureViewHolder viewHolder;
    private int index;
    private ImageLoader imageLoader;

    public LoadPictureAdapter(Context mContext,List<String> mData,int windowWidth){
        this.mContext = mContext;
        this.mData = mData;
        this.windowWidth = windowWidth;
        imageLoader = new ImageLoader(mContext,windowWidth);
    }

    public List<String> getDataList(){
        return mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,parent,false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //index = position;
        viewHolder = (PictureViewHolder) holder;
        //viewHolder.pictureImage.setAspectRatio(0.9f);
        /*Glide.with(mContext).load(mData.get(position).pictureUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                pictureWidth = resource.getWidth();
                pictureHeight = resource.getHeight();
                viewHolder.imageView.getLayoutParams().width = windowWidth/2;
                Log.e("jasdjf","pictureWidth="+pictureWidth+",pictureHeight="+pictureHeight);
                int height = (int)((((float)windowWidth/2)/pictureWidth)*pictureHeight);
                Log.e("jasdjf","height="+height);
                viewHolder.imageView.getLayoutParams().height = height;
                viewHolder.imageView.setImageBitmap(resource);
                //viewHolder.pictureImage.setAspectRatio((float) pictureWidth/pictureHeight);
                //viewHolder.pictureImage.setImageURI(mData.get(index).pictureUrl);
            }
        });*/
        //viewHolder.imageView.getLayoutParams().height = 100+position*20;

        //Log.e("jasdjf","Height="+viewHolder.imageView.getLayoutParams().height);
        //Glide.with(mContext).load(mData.get(position).pictureUrl).into(viewHolder.imageView);

        //viewHolder.pictureImage.getLayoutParams().width = windowWidth/2;
        //viewHolder.pictureImage.setAspectRatio(0.7f);
        //Log.e("jasdjf",position+"");
        //UitlsToos.setControllerListener(viewHolder.pictureImage,mData.get(position).pictureUrl,windowWidth/2);
        //viewHolder.pictureImage.setImageURI(mData.get(index).pictureUrl);
        //Log.e("jasdjf","aaaa////"+mData.get(position));
        imageLoader.displayImage(viewHolder.imageView,mData.get(position));
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder{

        //public SimpleDraweeView pictureImage;
        public ImageView imageView;

        public PictureViewHolder(View itemView) {
            super(itemView);
            //pictureImage = (SimpleDraweeView)itemView.findViewById(R.id.fresco_image);
            //设置宽高比例
            //pictureImage.setAspectRatio(0.9f);
            //设置对其方式
            //pictureImage.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}

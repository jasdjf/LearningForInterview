package com.jasdjf.testanimation.drawable_animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jasdjf.testanimation.R;

public class DrawableAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageDrawableAnimation;
    private Button mBtnStart;
    private Button mBtnStop;
    private AnimationDrawable animationDrawable = null;
    private FrameAnimationSurfaceView mFrameAnimationSurfaceview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_animation);
        initView();
    }

    private void initView() {
        //mImageDrawableAnimation = (ImageView) findViewById(R.id.image_drawable_animation);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStop = (Button) findViewById(R.id.btn_stop);

        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);

        //mImageDrawableAnimation.setBackgroundResource(R.drawable.drawable_animation);
        //animationDrawable = (AnimationDrawable) mImageDrawableAnimation.getBackground();
        mFrameAnimationSurfaceview = (FrameAnimationSurfaceView) findViewById(R.id.frame_animation_surfaceview);
        //mFrameAnimationSurfaceview.setBitmapResourceIDs(R.array.bitmap_resource_ids);
        //mFrameAnimationSurfaceview.setRepeatMode(true);//设置是否重复播放
        //mFrameAnimationSurfaceview.setDuration(50);//设置每帧画面显示的时间
        //mFrameAnimationSurfaceview.setPlayMode(FrameAnimationSurfaceView.REVERSE);//设置是正序还是倒序播放
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                /*if (animationDrawable != null) {
                    animationDrawable.start();
                }*/
                mFrameAnimationSurfaceview.startAnimation();
                break;
            case R.id.btn_stop:
                /*if (animationDrawable != null && animationDrawable.isRunning()) {
                    animationDrawable.stop();
                }*/
                mFrameAnimationSurfaceview.stopAnimation();
                break;
        }
    }
}

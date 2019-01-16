package com.jasdjf.testanimation.view_animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.jasdjf.testanimation.R;

public class ViewAnimationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageTranslate;
    private ImageView mImageScale;
    private ImageView mImageAlpha;
    private ImageView mImageRotate;
    private ImageView mImageSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
        initView();
    }

    private void initView() {
        mImageTranslate = (ImageView) findViewById(R.id.image_translate);
        mImageScale = (ImageView) findViewById(R.id.image_scale);
        mImageAlpha = (ImageView) findViewById(R.id.image_alpha);
        mImageRotate = (ImageView) findViewById(R.id.image_rotate);
        mImageSet = (ImageView) findViewById(R.id.image_set);

        mImageTranslate.setOnClickListener(this);
        mImageScale.setOnClickListener(this);
        mImageAlpha.setOnClickListener(this);
        mImageRotate.setOnClickListener(this);
        mImageSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_translate:
                //在xml文件中配置平移动画
                //TranslateAnimation translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.view_translate);
                //mImageTranslate.startAnimation(translateAnimation);
                viewTranslate();
                break;
            case R.id.image_scale:
                //ScaleAnimation scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this,R.anim.view_scale);
                //mImageScale.startAnimation(scaleAnimation);
                viewScale();
                break;
            case R.id.image_alpha:
                //AlphaAnimation alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this,R.anim.view_alpha);
                //mImageAlpha.startAnimation(alphaAnimation);
                viewAlpha();
                break;
            case R.id.image_rotate:
                //RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(this,R.anim.view_rotate);
                //mImageRotate.startAnimation(rotateAnimation);
                viewRotate();
                break;
            case R.id.image_set:
                //Animation animation = AnimationUtils.loadAnimation(this,R.anim.view_set);
                //mImageSet.startAnimation(animation);
                viewSet();
                break;
        }
    }

    /**
     * 在代码中配置平移动画
     */
    private void viewTranslate() {
        //fromXDelta:视图在水平方向x 移动的开始位置(此位置表示相对于原位置的距离)
        //toXDelta:视图在水平方向x 移动的结束位置(此位置表示相对于原位置的距离)
        //fromYDelta:视图在水平方向y 移动的开始位置(此位置表示相对于原位置的距离)
        //toYDelta:视图在水平方向y 移动的结束位置(此位置表示相对于原位置的距离)
        //TranslateAnimation translateAnimation = new TranslateAnimation(100,500,0,500);
        //以下配置方式相当于在xml中的如下配置：
        //android:fromXDelta="0" android:toXDelta="150%" android:fromYDelta="0" android:toYDelta="500%"
        //TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE,0,Animation.RELATIVE_TO_SELF,2.5f,Animation.ABSOLUTE,0,Animation.RELATIVE_TO_SELF,5);
        //以下配置方式相当于在xml中的如下配置：
        //android:fromXDelta="0" android:toXDelta="150%p" android:fromYDelta="0" android:toYDelta="500%p"
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_PARENT, 2.5f, Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_PARENT, 5);
        translateAnimation.setDuration(2000);//动画持续时间
        mImageTranslate.startAnimation(translateAnimation);
    }

    /**
     * 在代码中配置缩放动画
     */
    private void viewScale() {
        //此方式默认缩放中心点为0,0
        //ScaleAnimation scaleAnimation = new ScaleAnimation(0,2.0f,0,2.0f);
        //此方式设置缩放中心点为20,30，单位为px
        //ScaleAnimation scaleAnimation = new ScaleAnimation(0,2.0f,0,2.0f,20,30);
        //以下配置方式相当于在xml中的如下配置：
        //android:pivotX="20%" android:pivotY="30%"
        //ScaleAnimation scaleAnimation = new ScaleAnimation(0,2.0f,0,2.0f,Animation.RELATIVE_TO_SELF,0.2f,Animation.RELATIVE_TO_SELF,0.3f);
        //以下配置方式相当于在xml中的如下配置：
        //android:pivotX="20%p" android:pivotY="30%p"
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 2.0f, 0, 2.0f, Animation.RELATIVE_TO_PARENT, 0.2f, Animation.RELATIVE_TO_PARENT, 0.3f);
        scaleAnimation.setDuration(2000);//动画持续时间
        mImageScale.startAnimation(scaleAnimation);
    }

    /**
     * 在代码中配置透明动画
     */
    private void viewAlpha() {
        //取值没有什么限制，小于0的都表示透明，大于1的都表示不透明，取小于0或大于1的值只会影响插值器，影响各个时间点上的值
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        mImageAlpha.startAnimation(alphaAnimation);
    }

    /**
     * 在代码中配置旋转动画
     */
    private void viewRotate() {
        //此方式默认旋转中心点为0,0
        //RotateAnimation rotateAnimation = new RotateAnimation(45,135);
        //此方式设置旋转中心点为20,30，单位为px
        //RotateAnimation rotateAnimation = new RotateAnimation(45,135,20,30);
        //以下配置方式相当于在xml中的如下配置：
        //android:pivotX="50%" android:pivotY="50%"
        RotateAnimation rotateAnimation = new RotateAnimation(45, 135, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //以下配置方式相当于在xml中的如下配置：
        //android:pivotX="50%p" android:pivotY="50%p"
        //RotateAnimation rotateAnimation = new RotateAnimation(45,135,Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);
        rotateAnimation.setDuration(2000);
        mImageRotate.startAnimation(rotateAnimation);
    }

    /**
     * 代码中配置组合动画
     */
    private void viewSet(){
        //定义动画集
        //构造函数的参数含义:是否让set中的所有动画共用set的插值器，true表示共用，false表示各动画使用各自的插值器
        AnimationSet animationSet = new AnimationSet(false);

        //定义旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        //定义缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,0.5f,1.0f,0.5f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setStartOffset(1000);

        //定义平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(0,300,0,0);
        translateAnimation.setDuration(10000);

        //定义透明动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.5f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setStartOffset(3000);

        //将动画添加到集合中
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);

        mImageSet.startAnimation(animationSet);
    }
}

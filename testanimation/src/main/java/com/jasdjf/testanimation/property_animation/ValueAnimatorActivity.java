package com.jasdjf.testanimation.property_animation;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jasdjf.testanimation.R;

public class ValueAnimatorActivity extends AppCompatActivity {

    private ImageView mImageValueAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animator);
        initView();
    }

    private void initView() {
        mImageValueAnimator = (ImageView) findViewById(R.id.image_value_animator);
        mImageValueAnimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int width = mImageValueAnimator.getWidth();
                //这里只是让width值从初始值变为300之后又变成初始值，但是仅仅只是值的改变，没有影响到view
                ValueAnimator valueAnimator = ValueAnimator.ofInt(width,300,width);
                valueAnimator.setDuration(3000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //在每次值改变时，将其赋值到view上，然后重新绘制，这样就能够体现出view的改变了
                        mImageValueAnimator.getLayoutParams().width = (int) animation.getAnimatedValue();
                        mImageValueAnimator.requestLayout();
                    }
                });
                valueAnimator.start();
            }
        });
    }
}

package com.jasdjf.testanimation.viewpropertyanimator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jasdjf.testanimation.R;

public class ViewPropertyAnimatorActivity extends AppCompatActivity implements View.OnClickListener {
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
        mImageSet = (ImageView) findViewById(R.id.image_set);

        mImageSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_set:
                //下面的写法不能达到预期的连续动画的效果，所有动画只能同时播放，设置的delay是所有动画开始的delay，而不是一个一个的delay
                mImageSet.animate().rotation(360).setDuration(1000);
                mImageSet.animate().scaleX(2.0f).setDuration(2000).setStartDelay(1000);
                mImageSet.animate().scaleY(2.0f).setDuration(2000).setStartDelay(1000);
                mImageSet.animate().translationX(300).setDuration(10000);
                mImageSet.animate().alpha(0.5f).setDuration(1000).setStartDelay(3000);
                break;
        }
    }
}

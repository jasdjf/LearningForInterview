package com.jasdjf.testanimation.interpolator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.jasdjf.testanimation.R;

/**
 * 加速插值器，动画做加速运动
 */
public class AccelerateInterpolatorActivity extends AppCompatActivity implements View.OnClickListener {

    private InterpolatorView mViewInterpolator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linearinterpolator);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_interpolator:
                mViewInterpolator.startAnimation(new AccelerateInterpolator());
                break;
        }
    }

    private void initView() {
        mViewInterpolator = (InterpolatorView) findViewById(R.id.view_interpolator);
        mViewInterpolator.setOnClickListener(this);
    }
}

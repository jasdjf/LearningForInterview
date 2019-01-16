package com.jasdjf.testanimation.interpolator;

import android.animation.TimeInterpolator;

/**
 * 自定义插值器主要是重写getInterpolation方法，在其中定义动画的运动方式(加速减速等)
 */
public class CustomInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {//input值从0开始匀速向1增加，代表动画从开始到结束的过程。此方法返回的结果是估值器中用到的fraction
        if(input<0.5f){
            return (float) Math.sin(Math.PI * input)/2;
        } else {
            return (2f-(float) Math.sin(Math.PI * input))/2;
        }
    }
}

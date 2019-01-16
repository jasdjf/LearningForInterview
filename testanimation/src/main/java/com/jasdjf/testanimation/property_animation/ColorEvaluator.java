package com.jasdjf.testanimation.property_animation;

import android.animation.TypeEvaluator;

public class ColorEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        int startColor = Integer.parseInt(((String) startValue).substring(3),16);
        int endColor = Integer.parseInt(((String) endValue).substring(3),16);
        int color = (int) (startColor - fraction * (startColor-endColor));
        return String.format("#FF%06X",color);
    }
}

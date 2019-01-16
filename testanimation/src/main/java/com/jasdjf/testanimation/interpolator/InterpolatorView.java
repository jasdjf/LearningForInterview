package com.jasdjf.testanimation.interpolator;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jasdjf.testanimation.property_animation.PointEvaluator;

public class InterpolatorView extends View {

    private Paint mPaint;
    private Point mPoint;
    private int radius = 50;

    public InterpolatorView(Context context) {
        this(context,null);
    }

    public InterpolatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InterpolatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mPoint==null){
            canvas.drawCircle(getWidth()/2,radius,radius,mPaint);
        } else {
            canvas.drawCircle(mPoint.x,mPoint.y,radius,mPaint);
        }
    }

    public void startAnimation(TimeInterpolator interpolator){
        Point startPoint = new Point(getWidth()/2,radius);
        Point endPoint = new Point(getWidth()/2,getHeight()-radius);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.start();
    }
}

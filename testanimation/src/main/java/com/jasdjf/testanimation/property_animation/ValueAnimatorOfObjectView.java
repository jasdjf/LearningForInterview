package com.jasdjf.testanimation.property_animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ValueAnimatorOfObjectView extends View {

    private Paint mPaint;
    private Point mPoint;
    private int radius = 50;

    public ValueAnimatorOfObjectView(Context context) {
        this(context,null);
    }

    public ValueAnimatorOfObjectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ValueAnimatorOfObjectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mPoint==null){
            canvas.drawCircle(radius,radius,radius,mPaint);
        } else {
            canvas.drawCircle(mPoint.x,mPoint.y,radius,mPaint);
        }
    }

    public void startAnimation(){
        Point startPoint = new Point(radius,radius);
        Point endPoint = new Point(getWidth()-radius,getHeight()-radius);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }
}

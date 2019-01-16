package com.jasdjf.testanimation.property_animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ObjectAnimatorOfObjectView extends View {

    private Paint mPaint;
    private Point mPoint;
    private int radius = 50;
    public String mColor;

    public ObjectAnimatorOfObjectView(Context context) {
        this(context,null);
    }

    public ObjectAnimatorOfObjectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ObjectAnimatorOfObjectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    public void setColor(String color){
        mColor = color;
        mPaint.setColor(Color.parseColor(mColor));
    }
    public String getColor(){
        return mColor;
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
        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(this,"color",new ColorEvaluator(),"#FFFF0000","#FF0000FF");
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator).with(objectAnimator);
        animatorSet.setDuration(5000);
        animatorSet.start();
    }
}

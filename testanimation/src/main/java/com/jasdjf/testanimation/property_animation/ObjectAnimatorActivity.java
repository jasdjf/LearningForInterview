package com.jasdjf.testanimation.property_animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jasdjf.testanimation.R;

public class ObjectAnimatorActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageTranslateObject;
    private ImageView mImageScaleObject;
    private ImageView mImageAlphaObject;
    private ImageView mImageRotateObject;
    private ImageView mImageSetObject;
    private ImageView mImageSetObjectInXml;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        initView();
    }

    private void initView() {
        mImageTranslateObject = (ImageView) findViewById(R.id.image_translate_object);
        mImageScaleObject = (ImageView) findViewById(R.id.image_scale_object);
        mImageAlphaObject = (ImageView) findViewById(R.id.image_alpha_object);
        mImageRotateObject = (ImageView) findViewById(R.id.image_rotate_object);
        mImageSetObject = (ImageView) findViewById(R.id.image_set_object_in_java);
        mImageSetObjectInXml = (ImageView) findViewById(R.id.image_set_object_in_xml);

        mImageTranslateObject.setOnClickListener(this);
        mImageScaleObject.setOnClickListener(this);
        mImageAlphaObject.setOnClickListener(this);
        mImageRotateObject.setOnClickListener(this);
        mImageSetObject.setOnClickListener(this);
        mImageSetObjectInXml.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_translate_object:
                float x = mImageTranslateObject.getTranslationX();
                ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(mImageTranslateObject, "translationX", x, 800, x);
                translateAnimator.setDuration(3000);
                translateAnimator.start();
                break;
            case R.id.image_scale_object:
                float scaleX = mImageScaleObject.getScaleX();
                ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mImageScaleObject, "scaleX", scaleX, 2, scaleX);
                scaleAnimator.setDuration(3000);
                scaleAnimator.start();
                break;
            case R.id.image_alpha_object:
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mImageAlphaObject, "alpha", 1, 0, 1);
                alphaAnimator.setDuration(3000);
                alphaAnimator.start();
                break;
            case R.id.image_rotate_object:
                ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(mImageRotateObject, "rotation", 0, 360);
                rotateAnimator.setDuration(3000);
                rotateAnimator.start();
                break;
            case R.id.image_set_object_in_java:
                AnimatorSet animatorSet = new AnimatorSet();
                ObjectAnimator mTranslateAnimator = ObjectAnimator.ofFloat(mImageSetObject, "translationX", 0, 800, 0);
                ObjectAnimator mScaleAnimator = ObjectAnimator.ofFloat(mImageSetObject, "scaleX", 1, 2, 1);
                ObjectAnimator mAlphaAnimator = ObjectAnimator.ofFloat(mImageSetObject, "alpha", 1, 0, 1);
                ObjectAnimator mRotateAnimator = ObjectAnimator.ofFloat(mImageSetObject, "rotation", 0, 360);
                animatorSet.play(mTranslateAnimator).with(mRotateAnimator).before(mScaleAnimator).before(mAlphaAnimator);
                animatorSet.setDuration(10000);
                animatorSet.start();
                break;
            case R.id.image_set_object_in_xml:
                AnimatorSet setInXml = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.property_set);
                setInXml.setTarget(mImageSetObjectInXml);
                setInXml.start();
                break;
        }
    }
}

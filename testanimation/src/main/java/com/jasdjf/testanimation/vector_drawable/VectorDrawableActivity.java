package com.jasdjf.testanimation.vector_drawable;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jasdjf.testanimation.R;

/**
 * 矢量图动画
 */
public class VectorDrawableActivity extends AppCompatActivity {

    private EditText mVectorDrawable;
    private boolean isRunning = false;
    private ImageView mVectorFocaltech;
    private ImageView mVectorSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable);
        initView();
    }

    private void initView() {
        mVectorDrawable = (EditText) findViewById(R.id.vector_drawable);
        mVectorDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (isRunning) {
                    ((Animatable)(mVectorDrawable.getDrawable())).stop();
                } else {
                    ((Animatable)(mVectorDrawable.getDrawable())).start();
                }
                isRunning = !isRunning;*/
                mVectorDrawable.setActivated(!mVectorDrawable.isActivated());
            }
        });
        mVectorSearch = (ImageView) findViewById(R.id.vector_search);
        mVectorSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Animatable) (mVectorSearch.getDrawable())).start();
            }
        });
        mVectorFocaltech = (ImageView) findViewById(R.id.vector_focaltech);
        mVectorFocaltech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Animatable)(mVectorFocaltech.getDrawable())).start();
            }
        });
    }
}

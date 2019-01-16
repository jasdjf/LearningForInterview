package com.jasdjf.testanimation.interpolator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jasdjf.testanimation.R;

public class InterpolatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mViewInterpolator;
    private Button mBtnAccelerateInterpolator;
    private Button mBtnBounceInterpolator;
    private Button mBtnCustomInterpolator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator);
        initView();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_linear_interpolator:
                intent = new Intent(InterpolatorActivity.this, LinearInterpolatorActivity.class);
                break;
            case R.id.btn_accelerate_interpolator:
                intent = new Intent(InterpolatorActivity.this, AccelerateInterpolatorActivity.class);
                break;
            case R.id.btn_bounce_interpolator:
                intent = new Intent(InterpolatorActivity.this, BounceInterpolatorActivity.class);
                break;
            case R.id.btn_custom_interpolator:
                intent = new Intent(InterpolatorActivity.this, CustomInterpolatorActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void initView() {
        mViewInterpolator = (Button) findViewById(R.id.btn_linear_interpolator);
        mBtnAccelerateInterpolator = (Button) findViewById(R.id.btn_accelerate_interpolator);
        mBtnBounceInterpolator = (Button) findViewById(R.id.btn_bounce_interpolator);
        mBtnCustomInterpolator = (Button) findViewById(R.id.btn_custom_interpolator);
        mViewInterpolator.setOnClickListener(this);
        mBtnAccelerateInterpolator.setOnClickListener(this);
        mBtnBounceInterpolator.setOnClickListener(this);
        mBtnCustomInterpolator.setOnClickListener(this);
    }
}

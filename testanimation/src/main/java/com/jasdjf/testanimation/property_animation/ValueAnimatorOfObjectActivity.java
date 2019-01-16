package com.jasdjf.testanimation.property_animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jasdjf.testanimation.R;

public class ValueAnimatorOfObjectActivity extends AppCompatActivity implements View.OnClickListener {

    private ValueAnimatorOfObjectView mImageValueAnimatorOfobject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_animator_ofobject);
        initView();
    }

    private void initView() {
        mImageValueAnimatorOfobject = (ValueAnimatorOfObjectView) findViewById(R.id.image_value_animator_ofobject);

        mImageValueAnimatorOfobject.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_value_animator_ofobject:
                mImageValueAnimatorOfobject.startAnimation();
                break;
        }
    }
}

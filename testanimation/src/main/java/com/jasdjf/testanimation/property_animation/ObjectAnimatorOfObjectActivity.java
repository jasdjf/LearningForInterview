package com.jasdjf.testanimation.property_animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jasdjf.testanimation.R;

public class ObjectAnimatorOfObjectActivity extends AppCompatActivity implements View.OnClickListener {


    private ObjectAnimatorOfObjectView mImageObjectAnimatorOfobject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator_ofobject);
        initView();
    }

    private void initView() {
        mImageObjectAnimatorOfobject = (ObjectAnimatorOfObjectView) findViewById(R.id.image_object_animator_ofobject);
        mImageObjectAnimatorOfobject.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_object_animator_ofobject:
                mImageObjectAnimatorOfobject.startAnimation();
                break;
        }
    }
}

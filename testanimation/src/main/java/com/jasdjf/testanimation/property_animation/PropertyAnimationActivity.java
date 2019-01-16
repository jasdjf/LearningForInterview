package com.jasdjf.testanimation.property_animation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jasdjf.testanimation.R;

public class PropertyAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        initView();
    }

    private void initView() {
        Button mBtnValueAnimator = (Button) findViewById(R.id.btn_value_animator);
        Button mBtnObjectAnimator = (Button) findViewById(R.id.btn_object_animator);
        Button mBtnAnimatorOfobject = (Button) findViewById(R.id.btn_value_animator_ofobject);
        Button mBtnObjectAnimatorOfobject = (Button) findViewById(R.id.btn_object_animator_ofobject);

        mBtnValueAnimator.setOnClickListener(this);
        mBtnObjectAnimator.setOnClickListener(this);
        mBtnAnimatorOfobject.setOnClickListener(this);
        mBtnObjectAnimatorOfobject.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_value_animator:
                intent = new Intent(PropertyAnimationActivity.this, ValueAnimatorActivity.class);
                break;
            case R.id.btn_object_animator:
                intent = new Intent(PropertyAnimationActivity.this, ObjectAnimatorActivity.class);
                break;
            case R.id.btn_value_animator_ofobject:
                intent = new Intent(PropertyAnimationActivity.this, ValueAnimatorOfObjectActivity.class);
                break;
            case R.id.btn_object_animator_ofobject:
                intent = new Intent(PropertyAnimationActivity.this, ObjectAnimatorOfObjectActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}

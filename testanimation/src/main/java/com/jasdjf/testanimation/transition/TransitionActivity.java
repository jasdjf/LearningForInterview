package com.jasdjf.testanimation.transition;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jasdjf.testanimation.R;
import com.jasdjf.testanimation.transition.after_android_5.TransitionAfterActivity;
import com.jasdjf.testanimation.transition.before_android_5.TransitionBeforeActivity;

/**
 * 转场动画：分为Android 5.0之前和之后两种
 */
public class TransitionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnTransitionBefore;
    private Button mBtnTransitionAfter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        initView();
    }

    private void initView() {
        mBtnTransitionBefore = (Button) findViewById(R.id.btn_transition_before);
        mBtnTransitionAfter = (Button) findViewById(R.id.btn_transition_after);

        mBtnTransitionBefore.setOnClickListener(this);
        mBtnTransitionAfter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_transition_before:
                intent = new Intent(TransitionActivity.this,TransitionBeforeActivity.class);
                break;
            case R.id.btn_transition_after:
                intent = new Intent(TransitionActivity.this,TransitionAfterActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}

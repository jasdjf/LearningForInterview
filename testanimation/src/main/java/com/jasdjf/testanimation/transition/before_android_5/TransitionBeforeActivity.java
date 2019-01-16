package com.jasdjf.testanimation.transition.before_android_5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jasdjf.testanimation.R;

public class TransitionBeforeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnTransitionBeforeTranslate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_before);
        initView();
    }

    private void initView() {
        mBtnTransitionBeforeTranslate = (Button) findViewById(R.id.btn_transition_before_translate);
        mBtnTransitionBeforeTranslate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_transition_before_translate:
                intent = new Intent(TransitionBeforeActivity.this,TransistionBeforeTranslateActivity.class);
                break;
        }
        startActivity(intent);
        //overridePendingTransition(R.anim.activity_open_enter,R.anim.activity_open_exit);
    }
}

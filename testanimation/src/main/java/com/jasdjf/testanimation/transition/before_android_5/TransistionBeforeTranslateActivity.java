package com.jasdjf.testanimation.transition.before_android_5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jasdjf.testanimation.R;

/**
 * 不止translate可以用来过渡Activity，像scale、rotate、alpha同样也可以
 */
public class TransistionBeforeTranslateActivity extends AppCompatActivity {

    private Button mBtnNextActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared);
        initView();
    }

    private void initView() {
        mBtnNextActivity = (Button) findViewById(R.id.btn_next_activity);
        mBtnNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransistionBeforeTranslateActivity.this,TransitionBeforeActivity1.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.activity_close_enter,R.anim.activity_close_exit);
    }
}

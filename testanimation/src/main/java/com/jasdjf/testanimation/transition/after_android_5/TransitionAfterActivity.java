package com.jasdjf.testanimation.transition.after_android_5;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jasdjf.testanimation.R;

public class TransitionAfterActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mBtnTransitionInStyle;
    private Button mBtnTransitionShareElementInActivity;
    private TextView mTransitionTextview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_transition_after);
        initView();
        //Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        //Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        //Explode explode = (Explode) TransitionInflater.from(this).inflateTransition(R.transition.activity_explode);
        //getWindow().setExitTransition(fade);
        //getWindow().setReenterTransition(slide);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_transition_in_style:
                intent = new Intent(TransitionAfterActivity.this, TransitionInStyleActivity.class);
                //一定要加下面这两行代码的其中一行，否则转场动画实现不了
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                //ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                startActivity(intent, options.toBundle());
                break;
            case R.id.btn_transition_share_element_in_activity:
                intent = new Intent(TransitionAfterActivity.this, TransitionShareElementInActivity.class);
                ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(this, mTransitionTextview, "shared_element");
                startActivity(intent, option.toBundle());
                break;
        }

    }

    private void initView() {
        mBtnTransitionInStyle = (Button) findViewById(R.id.btn_transition_in_style);
        mBtnTransitionShareElementInActivity = (Button) findViewById(R.id.btn_transition_share_element_in_activity);
        mTransitionTextview = (TextView) findViewById(R.id.transition_textview);

        mBtnTransitionInStyle.setOnClickListener(this);
        mBtnTransitionShareElementInActivity.setOnClickListener(this);
    }
}

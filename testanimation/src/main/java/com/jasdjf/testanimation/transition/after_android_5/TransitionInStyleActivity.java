package com.jasdjf.testanimation.transition.after_android_5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jasdjf.testanimation.R;

public class TransitionInStyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_textview);
        //Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        //Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        //Explode explode = (Explode) TransitionInflater.from(this).inflateTransition(R.transition.activity_explode);
        //getWindow().setEnterTransition(fade);
    }
}

package com.jasdjf.testanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jasdjf.testanimation.drawable_animation.DrawableAnimationActivity;
import com.jasdjf.testanimation.interpolator.InterpolatorActivity;
import com.jasdjf.testanimation.property_animation.PropertyAnimationActivity;
import com.jasdjf.testanimation.reveal.RevealAnimationActivity;
import com.jasdjf.testanimation.ripple.RippleActivity;
import com.jasdjf.testanimation.transition.TransitionActivity;
import com.jasdjf.testanimation.vector_drawable.VectorDrawableActivity;
import com.jasdjf.testanimation.view_animation.ViewAnimationActivity;
import com.jasdjf.testanimation.viewpropertyanimator.ViewPropertyAnimatorActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_view_animation:
                intent = new Intent(MainActivity.this, ViewAnimationActivity.class);
                break;
            case R.id.btn_drawable_animation:
                intent = new Intent(MainActivity.this, DrawableAnimationActivity.class);
                break;
            case R.id.btn_property_animation:
                intent = new Intent(MainActivity.this, PropertyAnimationActivity.class);
                break;
            case R.id.btn_interpolator:
                intent = new Intent(MainActivity.this, InterpolatorActivity.class);
                break;
            case R.id.btn_view_porperty_animator:
                intent = new Intent(MainActivity.this, ViewPropertyAnimatorActivity.class);
                break;
            case R.id.btn_ripple:
                intent = new Intent(MainActivity.this, RippleActivity.class);
                break;
            case R.id.btn_reveal_animation:
                intent = new Intent(MainActivity.this, RevealAnimationActivity.class);
                break;
            case R.id.btn_transition:
                intent = new Intent(MainActivity.this, TransitionActivity.class);
                break;
            case R.id.btn_vector_drawable:
                intent = new Intent(MainActivity.this, VectorDrawableActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void initView() {
        Button mBtnViewAnimation = (Button) findViewById(R.id.btn_view_animation);
        Button mBtnDrawableAnimation = (Button) findViewById(R.id.btn_drawable_animation);
        Button mBtnPropertyAnimation = (Button) findViewById(R.id.btn_property_animation);
        Button mBtnInterpolator = (Button) findViewById(R.id.btn_interpolator);
        Button mBtnViewPorpertyAnimator = (Button) findViewById(R.id.btn_view_porperty_animator);
        Button mBtnRipple = (Button) findViewById(R.id.btn_ripple);
        Button mBtnRevealAnimation = (Button) findViewById(R.id.btn_reveal_animation);
        Button mBtnTransition = (Button) findViewById(R.id.btn_transition);
        Button mBtnVectorDrawable = (Button) findViewById(R.id.btn_vector_drawable);

        mBtnViewAnimation.setOnClickListener(this);
        mBtnDrawableAnimation.setOnClickListener(this);
        mBtnPropertyAnimation.setOnClickListener(this);
        mBtnInterpolator.setOnClickListener(this);
        mBtnViewPorpertyAnimator.setOnClickListener(this);
        mBtnRipple.setOnClickListener(this);
        mBtnRevealAnimation.setOnClickListener(this);
        mBtnTransition.setOnClickListener(this);
        mBtnVectorDrawable.setOnClickListener(this);
    }
}

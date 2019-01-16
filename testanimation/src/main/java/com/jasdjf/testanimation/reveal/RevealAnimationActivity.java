package com.jasdjf.testanimation.reveal;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.jasdjf.testanimation.R;

/**
 * Android 5.0以上版本的揭露动画
 */
public class RevealAnimationActivity extends AppCompatActivity {

    private FloatingActionButton mRevealFab;
    private boolean isAnimatorRunning = false;
    private boolean isShowing = false;
    private View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal_animation);
        initView();
    }

    private void initView() {
        mRevealFab = (FloatingActionButton) findViewById(R.id.reveal_fab);
        mRevealFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnimatorRunning){
                    return;
                }
                int[] location = new int[2];
                mRevealFab.getLocationInWindow(location);
                int x = location[0] + mRevealFab.getWidth()/2;
                int y = location[1] + mRevealFab.getHeight()/2;
                view = findViewById(R.id.reveal_view);//view必须是已经加载到window上的view，不能使通过LayoutInfalater加载的view
                if (!isShowing) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(view,x,y, 0,(float)Math.hypot(x,y));
                    animator.setDuration(1000);
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isAnimatorRunning = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isAnimatorRunning = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    view.setVisibility(View.VISIBLE);
                    animator.start();
                    isShowing = true;
                } else {
                    Animator animator = ViewAnimationUtils.createCircularReveal(view,x,y,(float)Math.hypot(x,y),0);
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isAnimatorRunning = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setVisibility(View.GONE);
                            isAnimatorRunning = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animator.setDuration(1000);
                    animator.start();
                    isShowing = false;
                }
            }
        });
    }
}

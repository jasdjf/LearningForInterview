package com.jasdjf.testobserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtnObserver;
    Boss boss;
    ZhangSanObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        boss = new Boss();
        observer = new ZhangSanObserver();
        boss.registerObserver(observer);
    }

    private void initView() {
        mBtnObserver = (Button) findViewById(R.id.btn_observer);
        mBtnObserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boss != null) {
                    boss.allNotify();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        boss.unregisterObserver(observer);
        super.onDestroy();
    }
}

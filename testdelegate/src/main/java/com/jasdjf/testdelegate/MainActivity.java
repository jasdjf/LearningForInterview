package com.jasdjf.testdelegate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * 类似C#实现
 * 原理主要是通过反射
 */
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
        LiSiObserver liSiObserver = new LiSiObserver();
        //委托时如果委托的函数有参数且参数不一样，那样就只能单个委托，不能多个函数委托到同一个通知者上
        //如果函数没有参数，则可以多个函数委托到同一个通知者上
        boss.registerHandler(observer,"update");//参数类型为int时，这里不能用Integer.class，必须用int.class
        boss.registerHandler(liSiObserver,"observer");
    }

    private void initView() {
        mBtnObserver = (Button) findViewById(R.id.btn_observer);
        mBtnObserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jasdjf","button clicked");
                boss.Notify();
            }
        });
    }

    @Override
    protected void onDestroy() {
        boss.unregisterHandler();
        super.onDestroy();
    }
}

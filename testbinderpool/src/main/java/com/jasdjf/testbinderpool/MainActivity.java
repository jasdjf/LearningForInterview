package com.jasdjf.testbinderpool;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnGetString;
    private Button mBtnCompute;
    BinderPool binderPool;
    private Button mBtnUnbind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnGetString = (Button) findViewById(R.id.btn_get_string);
        mBtnCompute = (Button) findViewById(R.id.btn_compute);
        mBtnUnbind = (Button) findViewById(R.id.btn_unbind);
        mBtnGetString.setOnClickListener(this);
        mBtnCompute.setOnClickListener(this);
        mBtnUnbind.setOnClickListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                binderPool = BinderPool.getInstance(MainActivity.this);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_string:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //binderPool = BinderPool.getInstance(MainActivity.this);
                        IBinder getStringBinder = binderPool.queryBinder(0);
                        IMyAidlInterface getStringImpl = GetStringImpl.asInterface(getStringBinder);
                        try {
                            Log.d("jasdjf", getStringImpl.getString());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_compute:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //binderPool = BinderPool.getInstance(MainActivity.this);
                        IBinder computeBinder = binderPool.queryBinder(1);
                        IMyAidlInterface2 computeImpl = ComputeImpl.asInterface(computeBinder);
                        try {
                            Log.d("jasdjf", "compute result:" + computeImpl.compulte(5, 6));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_unbind:
                binderPool.unBind();
                break;
        }
    }
}

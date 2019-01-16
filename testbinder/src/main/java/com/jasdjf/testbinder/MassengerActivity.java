package com.jasdjf.testbinder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MassengerActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnStart;
    private Button mBtnStop;
    private Button mBtnBind;
    private Button mBtnUnbind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massenger);
        initView();
    }

    private void initView() {
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnBind = (Button) findViewById(R.id.btn_bind);
        mBtnUnbind = (Button) findViewById(R.id.btn_unbind);
        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnBind.setOnClickListener(this);
        mBtnUnbind.setOnClickListener(this);
    }

    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("jasdjf", "MassengerActivity start onServiceConnected: ");
            //Handler handler = new Clienthandler();
            //handler.sendEmptyMessage(1);
            mServiceMessager = new Messenger(service);
            Message message = Message.obtain(null,1);
            message.replyTo = mClientMessenger;
            try {
                mServiceMessager.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {//系统会在与服务的连接意外中断时（例如当服务崩溃或被终止时）调用该方法
            Log.d("jasdjf", "MassengerActivity start onServiceDisconnected: ");
        }
    };

    private Messenger mServiceMessager;
    private Messenger mClientMessenger = new Messenger(new Clienthandler());

    class Clienthandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    Log.d("jasdjf","MassengerActivity message from service");
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MassengerActivity.this,MyServiceMassenger.class);
        switch (v.getId()){
            case R.id.btn_start:
                Log.d("jasdjf", "MassengerActivity start onClick: ");
                startService(intent);
                break;
            case R.id.btn_stop:
                stopService(intent);
                break;
            case R.id.btn_bind:
                Log.d("jasdjf", "MassengerActivity bind onClick: ");
                bindService(intent,con,BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(con);
                break;
        }
    }
}

package com.jasdjf.testbinder;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyServiceMassenger extends Service {
    @Override
    public void onCreate() {
        Log.d("jasdjf","MyServiceMassenger Service onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("jasdjf","MyServiceMassenger Service onStartCommand");
        //return
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("jasdjf","MyServiceMassenger Service onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("jasdjf","MyServiceMassenger Service onUnbind");
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("jasdjf","MyServiceMassenger Service onBind");
        return mServiceMessenger.getBinder();
    }

    private Messenger mClientMessenger;
    private Messenger mServiceMessenger = new Messenger(new Servicehandler());

    class Servicehandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mClientMessenger = msg.replyTo;
                    Log.d("jasdjf","MyServiceMassenger message from client");
                    Message message = Message.obtain(null,2);
                    if(message!=null && mClientMessenger!=null){
                        try {
                            Log.d("jasdjf","MyServiceMassenger send message to client");
                            mClientMessenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }
}

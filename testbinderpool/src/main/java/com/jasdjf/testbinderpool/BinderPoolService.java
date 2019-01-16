package com.jasdjf.testbinderpool;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class BinderPoolService extends Service {

    private BinderPool.BinderPoolImpl binderPoolImpl = new BinderPool.BinderPoolImpl();

    @Override
    public void onCreate() {
        Log.d("jasdjf","onCreate");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("jasdjf","onBind");
        return binderPoolImpl;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.d("jasdjf","unbindService");
        super.unbindService(conn);
    }

    @Override
    public void onDestroy() {
        Log.d("jasdjf","onDestroy");
        super.onDestroy();
    }
}

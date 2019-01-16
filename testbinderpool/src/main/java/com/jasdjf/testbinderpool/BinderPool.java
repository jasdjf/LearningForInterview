package com.jasdjf.testbinderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

public class BinderPool {
    private static volatile BinderPool mInstance;
    private Context mContext;
    private CountDownLatch mCountDownLatch;
    private IBinderPool mBinderPool;
    private boolean mServiceConnected = false;

    private BinderPool(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        connectService();
    }

    public static BinderPool getInstance(Context mContext) {
        if (mInstance == null) {
            synchronized (BinderPool.class) {
                if (mInstance == null) {
                    mInstance = new BinderPool(mContext);
                }
            }
        }
        return mInstance;
    }

    private synchronized void connectService() {
        mCountDownLatch = new CountDownLatch(1);
        Intent serviceIntent = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public IBinder queryBinder(int binderFlag) {
        IBinder iBinder = null;
        try {
            if (mBinderPool != null) {
                iBinder = mBinderPool.queryBinder(binderFlag);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return iBinder;
    }

    public void unBind() {
        if(mServiceConnected && mContext!=null){
            mContext.unbindService(mServiceConnection);
            mServiceConnected = false;
        }
    }

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBinderPool != null) {
                Log.d("jasdjf","binder died!");
                mBinderPool.asBinder().unlinkToDeath(mDeathRecipient,0);
                mBinderPool = null;
                connectService();
            }
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("jasdjf","onServiceConnected");
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mCountDownLatch.countDown();
            mServiceConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public static class BinderPoolImpl extends IBinderPool.Stub {

        @Override
        public IBinder queryBinder(int binderFlag) throws RemoteException {
            IBinder iBinder = null;
            switch (binderFlag) {
                case 0:
                    iBinder = new GetStringImpl();
                    break;
                case 1:
                    iBinder = new ComputeImpl();
                    break;
            }
            return iBinder;
        }
    }
}

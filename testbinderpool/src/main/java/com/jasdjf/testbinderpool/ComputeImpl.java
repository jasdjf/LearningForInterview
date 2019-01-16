package com.jasdjf.testbinderpool;

import android.os.RemoteException;
import android.util.Log;

public class ComputeImpl extends IMyAidlInterface2.Stub {
    @Override
    public int compulte(int a, int b) throws RemoteException {
        Log.d("jasdjf","a+b");
        return a + b;
    }
}

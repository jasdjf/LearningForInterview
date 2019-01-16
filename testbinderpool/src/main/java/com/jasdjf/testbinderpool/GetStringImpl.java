package com.jasdjf.testbinderpool;

import android.os.RemoteException;
import android.util.Log;

public class GetStringImpl extends IMyAidlInterface.Stub {
    @Override
    public String getString() throws RemoteException {
        Log.d("jasdjf","get string");
        return "string from GetStringImpl";
    }
}

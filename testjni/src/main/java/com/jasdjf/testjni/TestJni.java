package com.jasdjf.testjni;

/**
 * Created by zhuangwei on 2018/3/12.
 */

public class TestJni {
    static{
        System.loadLibrary("native-lib");
    }

    public static native int openDevice(String path);
    public static native int closeDevice();

    public static native int IICRead(byte writebuf[], int writebytelen, char readbuf[], int readbytelen);
    public static native int IICWrite(char writebuf[], int writebytelen);
}

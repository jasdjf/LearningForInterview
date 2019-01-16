package com.jasdjf.dynasicregisterso;

public class TestSo {
    static{
        System.loadLibrary("native-lib");
    }

    public static native String getString();

    public static native int getInt();

    public static native int countNum(int a,int b);
}

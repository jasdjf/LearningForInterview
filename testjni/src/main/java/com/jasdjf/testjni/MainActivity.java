package com.jasdjf.testjni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestJni.openDevice("/proc/ftxxxx-debug");
        char[] readBuf = new char[1];
        byte[] writeBuf = new byte[2];
        writeBuf[0] = 0x07;
        writeBuf[1] = (byte)0xa3;
        TestJni.IICRead(writeBuf,2,readBuf,readBuf.length);
        Log.d("jasdjf",String.format("0x%2X",(int)readBuf[0]));
        TestJni.closeDevice();
    }
}

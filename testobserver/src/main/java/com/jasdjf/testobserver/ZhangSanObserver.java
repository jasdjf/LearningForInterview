package com.jasdjf.testobserver;

import android.util.Log;

public class ZhangSanObserver implements MyObserver {
    @Override
    public void update() {
        Log.d("jasdjf","zhang san observered!");
    }
}

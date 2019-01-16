package com.jasdjf.finallyreturn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "jasdjf";

    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "testTryReturn():" + testTryReturn());
    }

    private int testTryReturn(){
        try{
            Log.d(TAG, "run in try");
            /*if(flag==0){
                throw new Exception("AAAAAAAAAA");
            }*/
            //return 0;
        } catch (Exception e){
            Log.d(TAG, "run in catch");
            //return 1;
        } finally {
            Log.d(TAG, "run in finally");
            return 2;
        }
    }
}

package com.jasdjf.updateuionnonuithread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "jasdjf";
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                textView.setText("Update textview on non ui thread");
                Log.d(TAG, "set text on non ui thread");
            }
        }).start();*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                textView.setText("Update textview on non ui thread");
                Log.d(TAG, "set text on non ui thread");
            }
        }).start();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onresume");
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                textView.setText("Update textview on non ui thread");
                Log.d(TAG, "set text on non ui thread");
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                textView.setText("Update textview on non ui thread");
                Log.d(TAG, "set text on non ui thread");
            }
        }).start();*/
    }
}

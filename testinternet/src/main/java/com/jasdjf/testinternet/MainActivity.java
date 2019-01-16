package com.jasdjf.testinternet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jasdjf.testinternet.volley.VolleyActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnVolley;
    private Button mBtnOkhttp;
    private Button mBtnRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnVolley = (Button) findViewById(R.id.btn_volley);
        mBtnOkhttp = (Button) findViewById(R.id.btn_okhttp);
        mBtnRetrofit = (Button) findViewById(R.id.btn_retrofit);

        mBtnVolley.setOnClickListener(this);
        mBtnOkhttp.setOnClickListener(this);
        mBtnRetrofit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btn_volley:
                intent = new Intent(MainActivity.this,VolleyActivity.class);
                break;
            case R.id.btn_okhttp:

                break;
            case R.id.btn_retrofit:

                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}

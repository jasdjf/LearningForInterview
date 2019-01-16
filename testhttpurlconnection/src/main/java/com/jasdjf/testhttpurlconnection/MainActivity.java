package com.jasdjf.testhttpurlconnection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mBtnGet = (Button) findViewById(R.id.btn_get);
        mBtnGet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://www.wanandroid.com/article/list/0/json");
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.connect();
                            if (httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                                InputStream inputStream = httpURLConnection.getInputStream();
                                int len = inputStream.available();
                                Log.d("jasdjf","len = "+len+"///////");
                                byte[] buf = new byte[1024];
                                int readLen;
                                StringBuilder builder = new StringBuilder();
                                String str;
                                while((readLen = inputStream.read(buf))!=-1){
                                    //Log.d("jasdjf","readLen = "+readLen+"///////");
                                    str = new String(buf,0,readLen);
                                    builder.append(str);
                                    Log.d("jasdjf",str);
                                    //Log.d("jasdjf",new String(buf,0,readLen));
                                }
                                Log.d("jasdjf",builder.toString());//log打印有最大字符数限制
                                Log.d("jasdjf",builder.length()+"");
                                inputStream.close();
                            } else {
                                Log.d("jasdjf","get failed!");
                            }
                            httpURLConnection.disconnect();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }
}

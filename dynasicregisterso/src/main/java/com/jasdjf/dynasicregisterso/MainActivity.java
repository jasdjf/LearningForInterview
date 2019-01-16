package com.jasdjf.dynasicregisterso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mText.setText(TestSo.getString()+"///////////////"+TestSo.getInt()+"/////////"+TestSo.countNum(698,2));
    }

    private void initView() {
        mText = (TextView) findViewById(R.id.text);
    }
}

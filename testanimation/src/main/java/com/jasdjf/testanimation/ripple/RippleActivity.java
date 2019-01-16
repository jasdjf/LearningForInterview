package com.jasdjf.testanimation.ripple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jasdjf.testanimation.R;

/**
 * Android 5.0以上版本才有的点击波纹动画
 */
public class RippleActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_1:
                Toast.makeText(this,"Button1 clicked!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_2:
                Toast.makeText(this,"Button2 clicked!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_3:
                Toast.makeText(this,"Button3 clicked!",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initView() {
        mButton1 = (Button) findViewById(R.id.button_1);
        mButton2 = (Button) findViewById(R.id.button_2);
        mButton3 = (Button) findViewById(R.id.button_3);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
    }
}

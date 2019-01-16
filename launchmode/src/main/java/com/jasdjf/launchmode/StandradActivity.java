package com.jasdjf.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by zhuangwei on 2018/2/22.
 */

public class StandradActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnNext = (Button) findViewById(R.id.next_activity);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StandradActivity.this,SingleTaskActivity.class);
                //Intent intent = new Intent(StandradActivity.this,SingleTopActivity.class);
                startActivity(intent);
            }
        });
    }
}

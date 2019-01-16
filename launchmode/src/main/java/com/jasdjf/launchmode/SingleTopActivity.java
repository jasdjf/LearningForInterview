package com.jasdjf.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by zhuangwei on 2018/2/22.
 */

public class SingleTopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singletop);
        Button btnNext = (Button) findViewById(R.id.next_activity);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleTopActivity.this,SingleTaskActivity.class);
                startActivity(intent);
            }
        });
        Button btnOther = (Button) findViewById(R.id.other_activity);
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleTopActivity.this,StandradActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG", "===========================================onResume=========================================================");
        Log.e("TAG", "onResume " + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        dumpTaskAffinity();
    }
}

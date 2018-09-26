package com.jasdjf.expandabletextview;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mRootLayout;
    private ListView mListView;
    private ExpandableTextView mExpandTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String strTest = "测试字符串@jasdjf 测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串http://www.baidu.com 测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串http://www.baidu.com 测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串@jasdjf 测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串测试字符串";

        initView();
        mExpandTextView.setContent(strTest);
        mExpandTextView.setOnLinkClickListener(new ExpandableTextView.OnLinkClickListener() {
            @Override
            public void onClick(String linkContent, ExpandableTextView.LinkType linkType) {
                switch (linkType) {//跟据链接类型做相应处理
                    case MENTION:
                        Toast.makeText(MainActivity.this, "你点击了链接类型是@，链接内容是：" + linkContent, Toast.LENGTH_SHORT).show();
                        break;
                    case URL:
                        Toast.makeText(MainActivity.this, "你点击了链接类型是网页链接，链接内容是：" + linkContent, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        /*List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add(strTest);
        }
        mListView.setAdapter(new MyAdapter(this,dataList));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"position="+position,Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void initView() {
        mRootLayout = (ConstraintLayout) findViewById(R.id.root_layout);
        //mListView = (ListView) findViewById(R.id.list_view);
        mExpandTextView = (ExpandableTextView) findViewById(R.id.expand_text_view);
    }

    class MyAdapter extends BaseAdapter {

        List<String> data;
        Context context;

        public MyAdapter(Context context, List<String> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            if (data != null && position < data.size()) {
                return data.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
                holder = new Holder();
                holder.expandableTextView = convertView.findViewById(R.id.expand_text_view);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            if (position < data.size()) {
                holder.expandableTextView.setContent(data.get(position));
                holder.expandableTextView.setOnLinkClickListener(new ExpandableTextView.OnLinkClickListener() {
                    @Override
                    public void onClick(String linkContent, ExpandableTextView.LinkType linkType) {
                        switch (linkType) {//跟据链接类型做相应处理
                            case MENTION:
                                Toast.makeText(MainActivity.this, "你点击了链接类型是@，链接内容是：" + linkContent, Toast.LENGTH_SHORT).show();
                                break;
                            case URL:
                                Toast.makeText(MainActivity.this, "你点击了链接类型是网页链接，链接内容是：" + linkContent, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }
            return convertView;
        }

        class Holder {
            ExpandableTextView expandableTextView;
        }
    }
}

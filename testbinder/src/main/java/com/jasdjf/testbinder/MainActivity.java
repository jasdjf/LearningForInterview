package com.jasdjf.testbinder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnStart;
    private Button mBtnStop;
    private Button mBtnBind;
    private Button mBtnUnbind;
    private Button mBtnNext;
    private Button mBtnSerializable;
    private Button mBtnParcelable;
    private Button mBtnNonSerializable;
    private Button mBtnNonParcelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnBind = (Button) findViewById(R.id.btn_bind);
        mBtnUnbind = (Button) findViewById(R.id.btn_unbind);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mBtnSerializable = (Button) findViewById(R.id.btn_serializable);
        mBtnParcelable = (Button) findViewById(R.id.btn_parcelable);
        mBtnNonSerializable = (Button) findViewById(R.id.btn_serializable_);
        mBtnNonParcelable = (Button) findViewById(R.id.btn_parcelable_);
        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnBind.setOnClickListener(this);
        mBtnUnbind.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mBtnSerializable.setOnClickListener(this);
        mBtnParcelable.setOnClickListener(this);
        mBtnNonSerializable.setOnClickListener(this);
        mBtnNonParcelable.setOnClickListener(this);
    }

    private IMyAidlInterface iMyAidlInterface;

    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("jasdjf", "start onServiceConnected: ");
            //MyService.Mybinder mybinder = (MyService.Mybinder) service;
            //mybinder.sayHello();
            try {
                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                service.linkToDeath(deathRecipient, 0);
                iMyAidlInterface.sayHello(123);
                List<Book> list = iMyAidlInterface.getBookList();
                for (int i = 0; i < list.size(); i++) {
                    Log.d("jasdjf", list.get(i).toString());
                }
                iMyAidlInterface.addBook(new Book(3, "jasdjf"));
                list = iMyAidlInterface.getBookList();
                for (int i = 0; i < list.size(); i++) {
                    Log.d("jasdjf", list.get(i).toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {//系统会在与服务的连接意外中断时（例如当服务崩溃或被终止时）调用该方法
            Log.d("jasdjf", "start onServiceDisconnected: ");
        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (iMyAidlInterface == null) {
                return;
            }
            iMyAidlInterface.asBinder().unlinkToDeath(deathRecipient, 0);
            iMyAidlInterface = null;
            Intent intent = new Intent(MainActivity.this, MyService.class);
            bindService(intent, con, BIND_AUTO_CREATE);
            Log.d("jasdjf", "binderDied: rebind");
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        switch (v.getId()) {
            case R.id.btn_start:
                Log.d("jasdjf", "start onClick: ");
                startService(intent);
                break;
            case R.id.btn_stop:
                stopService(intent);
                break;
            case R.id.btn_bind:
                Log.d("jasdjf", "bind onClick: ");
                bindService(intent, con, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(con);
                break;
            case R.id.btn_next:
                Intent acintent = new Intent(this, MassengerActivity.class);
                startActivity(acintent);
                break;
            case R.id.btn_serializable:
                try {
                    User user = new User("jasdjf","56456456156");
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/sdcard/cache.txt"));
                    oos.writeObject(user);
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_parcelable:
                try {
                    Book book = new Book(123,"jasdjf");
                    Parcel parcel = Parcel.obtain();
                    book.writeToParcel(parcel,0);
                    byte[] data = parcel.marshall();
                    FileOutputStream fos = new FileOutputStream("/sdcard/cache1.txt");
                    fos.write(data);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_serializable_:
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(new FileInputStream("/sdcard/cache.txt"));
                    User user = (User) ois.readObject();
                    ois.close();
                    Log.d("jasdjf",user.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_parcelable_:
                try {
                    Parcel parcel = Parcel.obtain();
                    FileInputStream fis = new FileInputStream("/sdcard/cache1.txt");
                    byte[] data = new byte[fis.available()];
                    fis.read(data);
                    fis.close();
                    parcel.unmarshall(data,0,data.length);
                    // 必须要有setDataPosition这一步，否则反序列化失败。这一步的作用为：将parcel当前读写指针移向开始位置
                    // 上一步unmarshall操作可能将该指针移动到了结尾，所以后面create使用前必须先将指针移向开头
                    parcel.setDataPosition(0);
                    Book book = Book.CREATOR.createFromParcel(parcel);
                    Log.d("jasdjf",book.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}

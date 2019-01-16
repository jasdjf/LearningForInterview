package com.jasdjf.testbinder;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyService extends Service {
    @Override
    public void onCreate() {
        Log.d("jasdjf","Service onCreate");
        try {
            mybinder.addBook(new Book(1,"Android"));
            mybinder.addBook(new Book(2,"IOS"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("jasdjf","Service onStartCommand");
        //return
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("jasdjf","Service onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("jasdjf","Service onUnbind");
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("jasdjf","Service onBind");
        /*int checkState = checkCallingOrSelfPermission("com.jasdjf.bind_remoteservice");
        if(checkState!=PackageManager.PERMISSION_GRANTED){
            return null;
        }*/
        return mybinder;
        //return null;
    }

    //private Mybinder mybinder = new Mybinder();

    /*class Mybinder extends Binder {
        void sayHello(){
            Log.d("jasdjf","Hello");
        }
    }*/

    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    IMyAidlInterface.Stub mybinder = new IMyAidlInterface.Stub() {
        @Override
        public void sayHello(int num) throws RemoteException {
            Log.d("jasdjf","Hello   "+num);
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d("jasdjf","add book");
            bookList.add(book);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d("jasdjf","getBookList");
            return bookList;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int checkState = checkCallingOrSelfPermission("com.jasdjf.bind_remoteservice");
            if(checkState!=PackageManager.PERMISSION_GRANTED){
                return false;
            }
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if(packages!=null && packages.length>0){
                packageName = packages[0];
            }
            if(packageName==null || !packageName.startsWith("com.jasdjf")){
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };
}

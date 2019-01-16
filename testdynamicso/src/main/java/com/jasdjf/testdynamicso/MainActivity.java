package com.jasdjf.testdynamicso;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "jasdjf";
    String toPath;
    String fileName = "libnative-lib.so";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toPath = getApplicationContext().getDir("libs",MODE_PRIVATE)+File.separator+"libs";
        Button btnDynamicSO = findViewById(R.id.test_hello);
        btnDynamicSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!copySoFile()){
                    return;
                }
                System.load(toPath+File.separator+fileName);
                Toast.makeText(MainActivity.this,sayHello(),Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this,"fd="+getDevice(),Toast.LENGTH_LONG).show();
            }
        });
        Button btnTestFD = findViewById(R.id.test_fd);
        btnTestFD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String formFilePath = Environment.getExternalStorageDirectory()+ File.separator+"so_file"+File.separator+ Build.CPU_ABI+File.separator+fileName;
                String toFilePath = toPath+File.separator+fileName;
                boolean isSoFileNeedCopy = needCopySoFile(formFilePath,toFilePath);
                boolean isSoFileLoaded = checkSoLoaded();
                if(isSoFileNeedCopy && isSoFileLoaded){
                    //重启apk
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                    android.os.Process.killProcess(android.os.Process.myPid());
                } else {
                    if(isSoFileNeedCopy){
                        Log.e(TAG, "need copy so file.");
                        if(!copySoFile()){
                            return;
                        }
                        Log.e(TAG, "copy file done.");
                    } else {
                        Log.e(TAG, "do not need copy so file.");
                    }
                }
                System.load(toPath+File.separator+fileName);
                Toast.makeText(MainActivity.this,"fd="+getDevice(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkSoLoaded(){
        int pid = android.os.Process.myPid();
        String  path = "/proc/"+pid+"/maps";
        File file = new File(path);
        if(!file.exists()){
            return false;
        } else {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String line = "";
                while((line=br.readLine())!=null){
                    Log.e(TAG, line);
                    if(line.toLowerCase().contains(fileName)){
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }
    }

    private boolean needCopySoFile(String srcFilePath,String desFilePath){
        if(!new File(desFilePath).exists() && new File(srcFilePath).exists()){
            return true;
        }
        String srcFileMD5 = getFileMD5(srcFilePath);
        String desFileMD5 = getFileMD5(desFilePath);
        if(srcFileMD5==null){
            return false;
        }
        if(desFileMD5==null){
            return false;
        }
        return !srcFileMD5.equals(desFileMD5);
    }

    private String getFileMD5(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            return null;
        }
        if(!file.isFile()){
            return null;
        }
        MessageDigest digest = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            digest = MessageDigest.getInstance("MD5");
            int len;
            byte[] buf = new byte[1024];
            while((len = fis.read(buf))!=-1){
                digest.update(buf,0,len);
            }
            BigInteger bi = new BigInteger(1,digest.digest());
            return bi.toString(16);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static native String sayHello();
    public static native int getDevice();

    private boolean copySoFile(){
        String formFilePath = Environment.getExternalStorageDirectory()+ File.separator+"so_file"+File.separator+fileName;
        String toFilePath = toPath+File.separator+fileName;
        Log.e(TAG, "toPath: "+toPath);
        Log.e(TAG, "formPath: "+formFilePath);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File file = new File(formFilePath);
            if(!file.exists()){
                Log.e(TAG, "so file does not exist.");
                return false;
            }
            file = new File(toPath);
            if(!file.exists()){
                if(!file.mkdirs()){
                    Log.e(TAG, "Create dir error!");
                    return false;
                }
            }
            fis = new FileInputStream(formFilePath);
            fos = new FileOutputStream(toFilePath);
            byte[] read_buf = new byte[1024];
            int len;
            while((len = fis.read(read_buf))!=-1){
                fos.write(read_buf,0,len);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

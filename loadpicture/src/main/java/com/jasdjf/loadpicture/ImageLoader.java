package com.jasdjf.loadpicture;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Request;
import okhttp3.Response;

public class ImageLoader {
    private static final int MAX_DISKCACHE_SIZE = 10 * 1024 * 1024;

    private LruCache<String,Bitmap> memoryCache;
    private DiskLruCache diskCache;
    private boolean isDiskCacheEnable;
    private Context mContext;
    private ExecutorService executorService;
    private int windowWidth;

    ImageLoader(Context context, int windowWidth){
        mContext = context;
        this.windowWidth = windowWidth;
        initMemoryCache();
        isDiskCacheEnable = initDiskCache();
        executorService = Executors.newFixedThreadPool(5);
    }

    private void initMemoryCache() {
        int maxMemoryCacheSize = (int)Runtime.getRuntime().maxMemory() / 8;
        memoryCache = new LruCache<String,Bitmap>(maxMemoryCacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    private boolean initDiskCache(){
        try {
            diskCache = DiskLruCache.open(getDiskCachePath(),getAppVersion(),1,MAX_DISKCACHE_SIZE);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void displayImage(ImageView imageView, String url){
        Bitmap bitmap = getFromMemoryCache(url);
        if (bitmap != null) {
            imageView.getLayoutParams().height = bitmap.getHeight();
            imageView.setImageBitmap(bitmap);
        } else {
            //executorService.submit(new GetBitmapRunnable(imageView,url));
            new BitmapWorkerTask(imageView,url,this).execute();
        }
    }

    private class GetBitmapRunnable implements Runnable{
        ImageView imageView;
        String bitmapUrl;
        GetBitmapRunnable(ImageView imageView, String url){
            this.imageView = imageView;
            bitmapUrl = url;
        }

        @Override
        public void run() {
            Bitmap bitmap = getFromDiskCache(bitmapUrl);
            if(bitmap!=null){
                runOnUIThread(imageView,bitmap);
                putIntoMemoryCache(bitmapUrl,bitmap);
            } else {
                bitmap = getFromIntnet(bitmapUrl);
                if (bitmap != null) {
                    runOnUIThread(imageView,bitmap);
                    putIntoMemoryCache(bitmapUrl,bitmap);
                    putIntoDiskCache(bitmapUrl,bitmap);
                }
            }
        }
    }

    public Bitmap getFromIntnet(String url){
        FileInputStream is = null;
        try {
            /*URL imageUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            is = connection.getInputStream();*/
            Request request = new Request.Builder().url(url).build();
            Response response = MainActivity.client.newCall(request).execute();
            is = (FileInputStream)response.body().byteStream();
            /* byte[] tmp = new byte[1024];
            //Log.d("jasdjf","buff.length="+buff.length);
            byte[] buff = new byte[0];
            int len;
           while((len = is.read(tmp))!=-1){
                byte[] tmp_buff = new byte[buff.length];
                System.arraycopy(buff,0,tmp_buff,0,buff.length);
                buff = new byte[buff.length+len];
                System.arraycopy(tmp_buff,0,buff,0,tmp_buff.length);
                System.arraycopy(tmp,0,buff,tmp_buff.length,len);
            }
            Log.d("jasdjf","len="+buff.length);
            Log.d("jasdjf","getFromIntnet:is=null:"+(is==null));*/
            FileDescriptor fileDescriptor = is.getFD();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            //BitmapFactory.decodeStream(is,null,options);
            //BitmapFactory.decodeByteArray(buff,0,buff.length,options);
            BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
            options.inSampleSize = computeSampleSize(options,windowWidth/2);
            Log.d("jasdjf","getFromIntnet:windowWidth="+windowWidth);
            Log.d("jasdjf","getFromIntnet:inSampleSize="+options.inSampleSize);
            options.inJustDecodeBounds = false;
            //Bitmap bitmap = BitmapFactory.decodeStream(is,null,options);
            //Bitmap bitmap = BitmapFactory.decodeByteArray(buff,0,buff.length,options);
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor,null,options);
            Log.d("jasdjf","getFromIntnet:"+(bitmap==null));
            return bitmap;
            //return transformBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void runOnUIThread(final ImageView imageView, final Bitmap bitmap){
        ((Activity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.getLayoutParams().height = bitmap.getHeight();
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    private int computeSampleSize(BitmapFactory.Options options, int reqWidth){
        final int width = options.outWidth;
        Log.d("jasdjf","computeSampleSize:outWidth="+width);
        int inSampleSize = 2;

        if (width > reqWidth) {

            final int halfWidth = width;

            while ((halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private Bitmap transformBitmap(Bitmap bitmap){
        if(bitmap==null){
            return null;
        }
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        float scaleCount = ((float)(windowWidth/2))/(float)bitmapWidth;
        Matrix matrix = new Matrix();
        matrix.preScale(scaleCount,scaleCount);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap,0,0,bitmapWidth,bitmapHeight,matrix,false);
        bitmap.recycle();
        return newBitmap;
    }

    public void putIntoDiskCache(String bitmapUrl,Bitmap bitmap){
        if(!isDiskCacheEnable){
            return;
        }
        ByteArrayOutputStream tmpOs = null;
        OutputStream os = null;
        try {
            String diskCacheKey = getDiskKeyFromMD5(bitmapUrl);
            if(diskCache.get(diskCacheKey)!=null){
                return;
            }
            DiskLruCache.Editor editor = diskCache.edit(diskCacheKey);
            os = editor.newOutputStream(0);
            tmpOs = new ByteArrayOutputStream();
            if(bitmap.compress(Bitmap.CompressFormat.JPEG,100,tmpOs)){
                if(writeToDiskCache(os,tmpOs)){
                    editor.commit();
                } else {
                    editor.abort();
                }
                diskCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (tmpOs != null) {
                try {
                    tmpOs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void putIntoDiskCache(String bitmapUrl,ByteArrayOutputStream bos){
        if(!isDiskCacheEnable){
            return;
        }
        ByteArrayOutputStream tmpOs = null;
        OutputStream os = null;
        try {
            String diskCacheKey = getDiskKeyFromMD5(bitmapUrl);
            if(diskCache.get(diskCacheKey)!=null){
                return;
            }
            DiskLruCache.Editor editor = diskCache.edit(diskCacheKey);
            os = editor.newOutputStream(0);
            tmpOs = new ByteArrayOutputStream();
            if (writeToDiskCache(os, tmpOs)) {
                editor.commit();
            } else {
                editor.abort();
            }
            diskCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (tmpOs != null) {
                try {
                    tmpOs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean writeToDiskCache(OutputStream os,ByteArrayOutputStream bos){
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(os);
            out.write(bos.toByteArray());
            out.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public Bitmap getFromDiskCache(String bitmapUrl){
        if(!isDiskCacheEnable){
            return null;
        }
        try {
            String diskCacheKey = getDiskKeyFromMD5(bitmapUrl);
            DiskLruCache.Snapshot snapshot = diskCache.get(diskCacheKey);
            if (snapshot != null) {
                InputStream is = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void putIntoMemoryCache(String bitmapUrl,Bitmap bitmap){
        if(getFromMemoryCache(bitmapUrl) == null){
            memoryCache.put(bitmapUrl,bitmap);
        }
    }

    private Bitmap getFromMemoryCache(String bitmapUrl){
        return memoryCache.get(bitmapUrl);
    }

    private File getDiskCachePath(){
        String cachePath;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()){
            if(mContext.getExternalCacheDir()!=null && mContext.getExternalCacheDir().exists()){
                cachePath = mContext.getExternalCacheDir().getAbsolutePath();
            } else {
                cachePath = mContext.getCacheDir().getAbsolutePath();
            }
        } else {
            cachePath = mContext.getCacheDir().getAbsolutePath();
        }
        cachePath += File.separator + "bitmap";
        return new File(cachePath);
    }

    private int getAppVersion(){
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(),0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private String getDiskKeyFromMD5(String url){
        String diskCacheKey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            diskCacheKey = byteToString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            diskCacheKey = String.valueOf(url.hashCode());
        }
        return diskCacheKey;
    }

    private String byteToString(byte[] buf){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String tmp = Integer.toHexString(buf[i] & 0xFF);
            if(tmp.length()==1){
                builder.append("0");
            }
            builder.append(tmp);
        }
        return builder.toString();
    }
}

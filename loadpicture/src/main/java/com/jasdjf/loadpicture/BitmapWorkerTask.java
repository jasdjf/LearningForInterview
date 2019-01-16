package com.jasdjf.loadpicture;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class BitmapWorkerTask extends AsyncTask<Void,Void,Bitmap> {

    private ImageView imageView;
    private String imageUrl;
    private ImageLoader imageLoader;

    public BitmapWorkerTask(ImageView imageView,String imageUrl,ImageLoader imageLoader) {
        this.imageView = imageView;
        this.imageUrl = imageUrl;
        this.imageLoader = imageLoader;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Log.d("jasdjf","do in background");
        Bitmap bitmap = null;
        Log.d("jasdjf",imageUrl);
        if (imageLoader!=null) {
            bitmap = imageLoader.getFromDiskCache(imageUrl);
            Log.d("jasdjf","1");
            if(bitmap == null){
                bitmap = imageLoader.getFromIntnet(imageUrl);
                Log.d("jasdjf","2");
                if(bitmap!=null){
                    imageLoader.putIntoMemoryCache(imageUrl,bitmap);
                    imageLoader.putIntoDiskCache(imageUrl,bitmap);
                }
            }
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.d("jasdjf","bitmap=null: "+(bitmap==null));
        if(bitmap!=null){
            imageView.getLayoutParams().height = bitmap.getHeight();
            imageView.setImageBitmap(bitmap);
        }
    }
}

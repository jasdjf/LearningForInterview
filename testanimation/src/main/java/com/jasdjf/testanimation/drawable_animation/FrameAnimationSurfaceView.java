package com.jasdjf.testanimation.drawable_animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jasdjf.testanimation.R;

public class FrameAnimationSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    public static final int RESTART = 0;
    public static final int REVERSE = 1;

    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing = false;
    private int mResourceID = -1;
    public int[] mBitmapResourceId = null;
    private int mCurrentBitmapResourceID = 0;
    private Thread mThread;
    private int mDuration = 0;
    private boolean mRepeatInfinite = false;
    private int mRepeatMode = RESTART;
    private int mRepeatCount = 0;

    public FrameAnimationSurfaceView(Context context) {
        this(context, null);
    }

    public FrameAnimationSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameAnimationSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        TypedArray typedArray = getResources().obtainAttributes(attrs,R.styleable.FrameAnimationSurfaceView);
        mRepeatCount = typedArray.getInt(R.styleable.FrameAnimationSurfaceView_repeateCount,mRepeatCount);
        mRepeatMode = typedArray.getInt(R.styleable.FrameAnimationSurfaceView_repeateMode,mRepeatMode);
        mDuration = typedArray.getInt(R.styleable.FrameAnimationSurfaceView_duration,mDuration);
        mResourceID = typedArray.getResourceId(R.styleable.FrameAnimationSurfaceView_resource,mResourceID);
        typedArray.recycle();
        mRepeatInfinite = mRepeatCount == -1;
        if(mResourceID!=-1){
            setBitmapResourceIDs(mResourceID);
        }
    }

    private void initView() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setZOrderOnTop(true);//surfaceview显示白画面
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        Bitmap bitmap = null;
        int repeatCount = 0;
        while (mIsDrawing) {
            try {
                if (mDuration > 0) {
                    Thread.sleep(mDuration);
                }
                mCanvas = mSurfaceHolder.lockCanvas();
                BitmapFactory.Options options = new BitmapFactory.Options();
                if (bitmap != null) {
                    options.inBitmap = bitmap;
                    bitmap = BitmapFactory.decodeResource(getResources(), mBitmapResourceId[mCurrentBitmapResourceID], options);
                } else {
                    bitmap = BitmapFactory.decodeResource(getResources(), mBitmapResourceId[mCurrentBitmapResourceID]);
                }
                if (bitmap != null) {
                    mCanvas.drawBitmap(bitmap, (getWidth() - bitmap.getWidth()) / 2,
                            (getHeight() - bitmap.getHeight()) / 2, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mRepeatMode == RESTART) {
                    if (mCurrentBitmapResourceID == mBitmapResourceId.length - 1) {
                        if (mRepeatInfinite || mRepeatCount>0) {
                            mCurrentBitmapResourceID = -1;
                            repeatCount++;
                            if(repeatCount>mRepeatCount){
                                mIsDrawing = false;
                            }
                        } else {
                            mIsDrawing = false;
                        }
                    }
                    mCurrentBitmapResourceID++;
                } else if(mRepeatMode == REVERSE){
                    if (mCurrentBitmapResourceID == 0) {
                        if (mRepeatInfinite || mRepeatCount>0) {
                            mCurrentBitmapResourceID = mBitmapResourceId.length;
                            repeatCount++;
                            if(repeatCount>mRepeatCount){
                                mIsDrawing = false;
                            }
                        } else {
                            mIsDrawing = false;
                        }
                    }
                    mCurrentBitmapResourceID--;
                }
                if (mCanvas != null) {
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                }
            }
        }
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    public void setBitmapResourceIDs(int resourceID) {
        TypedArray array = getResources().obtainTypedArray(resourceID);
        mBitmapResourceId = new int[array.length()];
        for (int i = 0; i < array.length(); i++) {
            mBitmapResourceId[i] = array.getResourceId(i, -1);
        }
        array.recycle();
    }

    public void setRepeatMode(boolean mRepeatInfinite){
        this.mRepeatInfinite = mRepeatInfinite;
    }

    public void setDuration(int time){
        mDuration = time;
    }

    public void setPlayMode(int playMode){
        mRepeatMode = playMode;
    }

    public void startAnimation() {
        if(mBitmapResourceId==null){
            return;
        }
        if (mThread == null || !mThread.isAlive()) {
            mIsDrawing = true;
            if(mRepeatMode==RESTART){
                mCurrentBitmapResourceID = 0;
            } else {
                mCurrentBitmapResourceID = mBitmapResourceId.length-1;
            }
            mThread = new Thread(this);
            mThread.start();
        }
    }

    public void stopAnimation() {
        mIsDrawing = false;
    }
}

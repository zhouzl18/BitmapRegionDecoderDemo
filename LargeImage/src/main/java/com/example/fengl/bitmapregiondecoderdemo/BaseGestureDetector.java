package com.example.fengl.bitmapregiondecoderdemo;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by fengl on 2016/4/17.
 */
public abstract class BaseGestureDetector {
    protected boolean mGestureInProgress;

    protected MotionEvent mPreMotionEvent;
    protected MotionEvent mCurMotionEvent;

    protected Context mContext;

    public BaseGestureDetector(Context context){
        mContext = context;
    }

    public boolean onTouchEvent(MotionEvent event){
        if(!mGestureInProgress){
            handleStartProgressEvent(event);
        }else{
            handleInProgressEvent(event);
        }
        return true;
    }

    protected abstract void handleInProgressEvent(MotionEvent event);

    protected abstract void handleStartProgressEvent(MotionEvent event);

    protected abstract void updateStateEvent(MotionEvent event);

    protected void resetState(){
        if(mPreMotionEvent != null){
            mPreMotionEvent.recycle();
            mPreMotionEvent = null;
        }
        if(mCurMotionEvent != null){
            mCurMotionEvent.recycle();
            mCurMotionEvent = null;
        }
        mGestureInProgress = false;
    }

}

package com.example.fengl.bitmapregiondecoderdemo;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * Created by fengl on 2016/4/17.
 */
public class MoveGestureDetector extends BaseGestureDetector{

    private PointF mPrePoint = new PointF();
    private PointF mCurPoint = new PointF();

    //仅仅为了减少创建内存
    private PointF mDeltaPoint = new PointF();
    //用于记录最终结果并返回
    private PointF mExtenalPoint = new PointF();
    private OnMoveGestureListener mListener;

    public MoveGestureDetector(Context context, OnMoveGestureListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected void handleInProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch(actionCode){
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mListener.onMoveEnd(this);
                resetState();
                break;

            case MotionEvent.ACTION_MOVE:
                updateStateEvent(event);
                boolean update = mListener.onMove(this);
                if(update){
                    mPreMotionEvent.recycle();
                    mPreMotionEvent = MotionEvent.obtain(event);
                }
                break;
        }
    }

    @Override
    protected void handleStartProgressEvent(MotionEvent event) {
        int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
        switch(actionCode){
            case MotionEvent.ACTION_DOWN:
                resetState();//防止没有接收到CANCEL OR UP,保险起见
                mPreMotionEvent = MotionEvent.obtain(event);
                updateStateEvent(event);
                break;

            case MotionEvent.ACTION_MOVE:
                mGestureInProgress = mListener.onMoveBegin(this);
                break;
        }
    }

    @Override
    protected void updateStateEvent(MotionEvent event) {
        final MotionEvent preEvent = mPreMotionEvent;
        mPrePoint = caculateFocalPointer(preEvent);
        mCurPoint = caculateFocalPointer(event);

        boolean mSkipThisMoveEvent = preEvent.getPointerCount() != event.getPointerCount();

        mExtenalPoint.x = mSkipThisMoveEvent ? 0 : mCurPoint.x - mPrePoint.x;
        mExtenalPoint.y = mSkipThisMoveEvent ? 0 : mCurPoint.y - mPrePoint.y;

    }

    /**
     * 根据event计算多指中心点
     * @param event
     * @return
     */
    private PointF caculateFocalPointer(MotionEvent event){
        final int count = event.getPointerCount();
        float x=0, y=0;
        for(int i=0; i<count; i++){
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= count;
        y /= count;
        return new PointF(x, y);
    }

    public float getMoveX(){
        return mExtenalPoint.x;
    }

    public float getMoveY(){
        return mExtenalPoint.y;
    }

    public interface OnMoveGestureListener{
        public boolean onMoveBegin(MoveGestureDetector detector);

        public boolean onMove(MoveGestureDetector detector);

        public void onMoveEnd(MoveGestureDetector detector);
    }

    public static class SimpleMoveGestureDetector implements OnMoveGestureListener{

        @Override
        public boolean onMoveBegin(MoveGestureDetector detector) {
            return true;
        }

        @Override
        public boolean onMove(MoveGestureDetector detector) {
            return false;
        }

        @Override
        public void onMoveEnd(MoveGestureDetector detector) {
        }
    }
}

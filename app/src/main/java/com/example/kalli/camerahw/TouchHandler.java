package com.example.kalli.camerahw;

import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kalli on 4/19/16.
 */
public class TouchHandler implements View.OnTouchListener{
    PhotoActivity photoActivity;
    GestureDetectorCompat myGestureListener;
    TouchHandler touchHandler;
    int count;

    public TouchHandler(PhotoActivity photoActivity){
        this.photoActivity = photoActivity;
        myGestureListener = new GestureDetectorCompat(this.photoActivity, new MyGestureListener());
count = 0;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){
        int maskedAction = motionEvent.getActionMasked();
        myGestureListener.onTouchEvent(motionEvent);
        switch(maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                for (int size = motionEvent.getPointerCount(), i = 0; i < size; i++) {
                    int id = motionEvent.getPointerId(i);
                    Log.d("message" , "i:" + i + " ID " + id + "count " + count);
                    photoActivity.addNewPath(id,
                            (int) motionEvent.getX(i),
                            (int) motionEvent.getY(i));
                }
                return true; //break;
            case MotionEvent.ACTION_MOVE:
                for (int size = motionEvent.getPointerCount(), i = 0; i < size; i++) {
                    int id = motionEvent.getPointerId(i);
                    photoActivity.updatePath(id,
                            (int) motionEvent.getX(i),
                            (int) motionEvent.getY(i));
                }
                return true; //break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                return true; //break;
        }
        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {

        @Override
        public boolean onDoubleTap(MotionEvent e){
            photoActivity.onDoubleTap(e);
            return super.onDoubleTap(e);

        }

        @Override
        public void onLongPress(MotionEvent e){
            photoActivity.onLongPress(e);
            super.onLongPress(e);

        }
    }
}

package com.android.tenchoklang.worldnews;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tench on 2/22/2018.
 */

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private static final String TAG = "RecyclerItemClickListen";

    interface OnRecyclerClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
        void onItemDoubleClick(View view, int position);
    }

    private final OnRecyclerClickListener mListener;
    //before we reponded to simple taps on widgets such as buttons, ListView...
    //but android actually provides a lot of ways that a user can interact with apps
    //including things like long tapping swiping and drawing patterns, and these are all handled
    //by the gesture detector, we here use the GestureDetectorCompat class so that our app is
    //compatable with earlier android versions
    private final GestureDetectorCompat mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        mListener = listener;

        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener(){
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                Log.d(TAG, "onSingleTapUp: Starts");
//                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
//                if(childView != null && mListener != null){
//                    Log.d(TAG, "onSingleTapUp: calling listener.onItemClick");
//                    //the callback to MainActivity's onItemClick
//                    mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
//                }
//                return true;
//
//            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: Starts");
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView != null && mListener != null){
                    Log.d(TAG, "onDoubleTap: calling listener.onDoubleClick");
                    //the callback to MainActivity's onItemClick
                    mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d(TAG, "onDoubleTap: Started");
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView != null && mListener != null){
                    Log.d(TAG, "onDoubleTap: calling listener.onDoubleClick");
                    //the callback to MainActivity's onItemClick
                    mListener.onItemDoubleClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
                return super.onDoubleTap(e);
            }
        });

    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        //if we return true here it tells the system that we have handled every touch listeners
        //So other touchListeners are ignored
        //if we return false, other touch listeners can also be activated
        if(mGestureDetector != null){
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: returned " + result);
            return result;
        } else{
            Log.d(TAG, "onInterceptTouchEvent: returned false");
            return false;
        }

    }



    }

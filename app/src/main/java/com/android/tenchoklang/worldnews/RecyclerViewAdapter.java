package com.android.tenchoklang.worldnews;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by tench on 1/26/2018.
 */

class RecyclerViewAdapter extends RecyclerView.Adapter{

    private static final String TAG = "RecyclerViewAdapter";

    //This inflates a view from whatever layout we want to be displayed in the recyclerView
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    //This method is called by the recyclerview when it wants new data to be stored in a
    //viewholder so that it can display it,
    // as items scroll of the screen the recycler view will provide a recycled Viewholder object
    //and tell us the position of the data object that it needs to display
    //what we have to do in this method is get that item from the data and put its values into the
    //Viewholder widget
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: New View Requested");

    }

    //get the size of the data
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: Called");
        return 0;
    }
}

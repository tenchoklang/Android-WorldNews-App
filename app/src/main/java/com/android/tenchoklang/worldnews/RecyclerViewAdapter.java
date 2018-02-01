package com.android.tenchoklang.worldnews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tench on 1/26/2018.
 */

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NewsDetailViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    private List<NewsDetail> newsDetailList;
    private Context context;

    static class NewsDetailViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "FlickrImageViewHolder";
        TextView outlet = null;
        TextView title = null;
        TextView datePublished = null;
        ImageView thumbnail = null;
        TextView description = null;

        public NewsDetailViewHolder(View viewForRecycler) {//itemView is the inflatedLayout
            super(viewForRecycler);
            Log.d(TAG, "FlickrImageViewHolder: Constructor called");
            this.outlet = (TextView) viewForRecycler.findViewById(R.id.outlet);
            this.title = (TextView) viewForRecycler.findViewById(R.id.title);
            this.datePublished = (TextView) viewForRecycler.findViewById(R.id.datePublished);
            this.thumbnail = (ImageView) viewForRecycler.findViewById(R.id.thumbnail);
            this.description = (TextView) viewForRecycler.findViewById(R.id.description);
        }
    }



    public RecyclerViewAdapter(Context context, List<NewsDetail> newsDetailList){
        this.context = context;
        this.newsDetailList = newsDetailList;
    }

    //This inflates a view from whatever layout we want to be displayed in the recyclerView
    @Override
    public NewsDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: New View created");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_for_recycler,parent,false);
        return new NewsDetailViewHolder(view);
    }


    //This method is called by the recyclerview when it wants new data to be stored in a
    //viewholder so that it can display it,
    // as items scroll of the screen the recycler view will provide a recycled Viewholder object
    //and tell us the position of the data object that it needs to display
    //what we have to do in this method is get that item from the data and put its values into the
    //Viewholder widget
    @Override
    public void onBindViewHolder(NewsDetailViewHolder holder, int position) {
        //called by the layout manager when it wants new data in a recycled view
        Log.d(TAG, "onBindViewHolder: New View Data Requested");
        NewsDetail  newNewsDetail= newsDetailList.get(position);

        holder.outlet.setText(newNewsDetail.getOutlet());
        holder.title.setText(newNewsDetail.getTitle());
        holder.datePublished.setText("Date: " +newNewsDetail.getDatePublished());
        Picasso.with(context).load(newNewsDetail.getUrlToImage()).placeholder(R.drawable.placeholder).into(holder.thumbnail);
        holder.description.setText(newNewsDetail.getDescription());

        Log.d(TAG, "onBindViewHolder: " + newNewsDetail.getTitle() + "-->" + position);

    }

    //get the size of the data
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: Called");
        return (newsDetailList != null && newsDetailList.size() !=0) ? newsDetailList.size(): 0;
    }

    //this method will be called once the download is finished, from the MainActivity
    public void loadNewData(List<NewsDetail> newsDetailList){
        this.newsDetailList = newsDetailList;
        notifyDataSetChanged();//notifies the recycler that the data has changed
    }
}

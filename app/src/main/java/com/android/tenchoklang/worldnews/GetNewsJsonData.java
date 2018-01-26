package com.android.tenchoklang.worldnews;

import android.os.AsyncTask;

import java.util.List;

/**
 * Created by tench on 1/25/2018.
 */

class GetNewsJsonData extends AsyncTask<List<NewsDetail>, Void, List<NewsDetail>>{

    interface OnDataAvailable{
        void onDataAvailable();
    }

    public GetNewsJsonData() {
    }

    @Override
    protected void onPostExecute(List<NewsDetail> newsDetails) {
        //super.onPostExecute(newsDetails);
    }

    @Override
    protected List<NewsDetail> doInBackground(List<NewsDetail>... params) {
        return null;
    }
}

package com.android.tenchoklang.worldnews;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tench on 1/25/2018.
 */

class GetNewsJsonData extends AsyncTask<String, Void, List<NewsDetail>> implements GetRawData.OnDownloadComplete{

    private static final String TAG = "GetNewsJsonData";
    private static final String apiKey = "6306fbe477654ab8929fa29582a45127";

    private List<NewsDetail> mNewsList = null;
    private String baseURL;//is the URL without any "filters" in our case its https://newsapi.org/v2/top-headlines
    private final OnDataAvailable mCallBack;

//https://newsapi.org/v2/everything?q=bitcoin
//https://newsapi.org/v2/top-headlines?country=us
//https://newsapi.org/v2/everything?q=bitcoin,50cent
//https://newsapi.org/v2/top-headlines?country=us
//https://newsapi.org/v2/top-headlines?country=us&q=trump&apiKey=6306fbe477654ab8929fa29582a45127

    private boolean mode;//top-headlines or everything
    private String country;//can't mix this param with the sources param.
    private String catergory;//you can't mix this param with the sources param.(business entertainment general health science sports technology)
    private String source;//you can't mix this param with the country or category params
    private String searchQuery;//Keywords or a phrase to search for


    interface OnDataAvailable{
        void onDataAvailable(List<NewsDetail> newsDetails, DownloadStatus status);
    }

    public GetNewsJsonData(OnDataAvailable callBack, String baseURL, boolean mode, String country) {
        this.mCallBack = callBack;
        this.baseURL = baseURL;
        this.mode = mode;
        this.country = country;
    }

    @Override
    protected void onPostExecute(List<NewsDetail> newsDetails) {
        //super.onPostExecute(newsDetails);
        Log.d(TAG, "onPostExecute: Starts");
        if(mCallBack!= null){
            //this is a callback to the mainactivity onDataAvailable
            //passing it the List<NewsDetail> and download status
            mCallBack.onDataAvailable(mNewsList, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute: Ends");
    }

    @Override
    protected List<NewsDetail> doInBackground(String... params) {
        String finalUrl = createURL(true, country, params[0]);

        //call GetRawData here
        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(finalUrl);//run this on this classes thread
        Log.d(TAG, "doInBackground: Ends");

        return null;
    }

    //remember to create a condition here to filter out what URL to use and what not to use
    //check for null
    private String createURL(boolean mode, String country, String searchQuery){
        Log.d(TAG, "createURL: Starts");

        if(searchQuery != ""){
            Log.d(TAG, "createURL: returns " + Uri.parse(baseURL).buildUpon()
                    .appendQueryParameter("country", country)
                    .appendQueryParameter("q", searchQuery)
                    .build().toString());

            return Uri.parse(baseURL).buildUpon()
                    .appendQueryParameter("country", country)
                    .appendQueryParameter("q", searchQuery)
                    .appendQueryParameter("apiKey", apiKey)
                    .build().toString();//.build() = constructs a uri with current attributes
        }
        else{
            return Uri.parse(baseURL).buildUpon()
                    .appendQueryParameter("country", country)
                    .appendQueryParameter("apiKey", apiKey)
                    .build().toString();
        }


    }


    //callback from GetRawData, when data download is complete
    //here is where List<NewsDetail> will be populated
    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {//if there is no error downloading, then proceed
            mNewsList = new ArrayList<>();//initialize the List

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray jsonArray = jsonData.getJSONArray("articles");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonArticle = jsonArray.getJSONObject(i);//gets the i index in the jsonArray

                    JSONObject jsonSource = jsonArticle.getJSONObject("source");
                    String id = jsonSource.getString("id");
                    String name = jsonSource.getString("name");

                    String author = jsonArticle.getString("author");
                    String title = jsonArticle.getString("title");
                    String description = jsonArticle.getString("description");
                    String url = jsonArticle.getString("url");
                    String urlToImage = jsonArticle.getString("urlToImage");

                    String publishedAt = jsonArticle.getString("publishedAt");//date published
                    publishedAt = publishedAt.substring(0,publishedAt.indexOf("T"));

                    NewsDetail newsObject = new NewsDetail(id, name, author, title, description, url, urlToImage, publishedAt);
                    mNewsList.add(newsObject);

                    Log.d(TAG, "onDownloadComplete: Completed index " + i + " " + newsObject.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing JSON data" + e.getMessage());
            }
        }
    }
}

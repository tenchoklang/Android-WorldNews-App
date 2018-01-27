package com.android.tenchoklang.worldnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GetNewsJsonData.OnDataAvailable {


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetNewsJsonData getNewsJsonData = new GetNewsJsonData(this,"https://newsapi.org/v2/top-headlines", true, "us");
        getNewsJsonData.execute("trump");
    }


    @Override
    public void onDataAvailable(List<NewsDetail> newsDetails, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: Starts");
        if(status == DownloadStatus.OK){
            Log.d(TAG, "onDataAvailable: ");
        }else{
            Log.e(TAG, "onDataAvailable: Failed with status " + status);
        }

        Log.d(TAG, "onDataAvailable: Ends");
    }
}

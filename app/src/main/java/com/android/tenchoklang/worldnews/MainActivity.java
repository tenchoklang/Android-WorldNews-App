

package com.android.tenchoklang.worldnews;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GetNewsJsonData.OnDataAvailable {


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        GetNewsJsonData getNewsJsonData = new GetNewsJsonData(this,"https://newsapi.org/v2/top-headlines", true, "us");
        getNewsJsonData.execute("trump");



        //https://www.nytimes.com/2018/01/26/us/politics/trump-davos-speech-fact-check.html
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


package com.android.tenchoklang.worldnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetRawData getRawData = new GetRawData();
        getRawData.execute("https://newsapi.org/v2/top-headlines?sources=abc-news&apiKey=6306fbe477654ab8929fa29582a45127");
    }
}

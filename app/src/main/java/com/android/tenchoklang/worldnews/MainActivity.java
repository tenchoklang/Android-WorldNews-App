

package com.android.tenchoklang.worldnews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetNewsJsonData.OnDataAvailable {


    static final String SEARCH_QUERY = "SEARCH_QUERY";//used as the "key" for shared preferences
    private static final String TAG = "MainActivity";
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        initTabListener();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyler view doesnt take care of handling the layouts, thats done by the layoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(this, new ArrayList<NewsDetail>());
        recyclerView.setAdapter(recyclerViewAdapter);


//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (scroll_down) {
//                    getSupportActionBar().hide();
//                } else {
//                    getSupportActionBar().show();
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 70) {
//                    //scroll down
//                    scroll_down = true;
//
//                } else if (dy < -5) {
//                    //scroll up
//                    scroll_down = false;
//                }
//            }
//        });


        //https://www.nytimes.com/2018/01/26/us/politics/trump-davos-speech-fact-check.html
    }

    //these methods are put into the onResume because when the activity returns back to this screen
    //onResume will be called, not onCreate
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String queryResult = sharedPreferences.getString(MainActivity.SEARCH_QUERY, "");//if no values return "" empty string

        GetNewsJsonData getNewsJsonData;
        getNewsJsonData = new GetNewsJsonData(this,"https://newsapi.org/v2/", true, "us");
        getNewsJsonData.execute(queryResult);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putString(MainActivity.SEARCH_QUERY, "").apply();//clear the previous query
    }

    @Override
    public void onDataAvailable(List<NewsDetail> newsDetails, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: Starts");
        if(status == DownloadStatus.OK){
            if(newsDetails.size() == 0){//if the API returns no data
                Toast.makeText(this,"No Data :(", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG, "onDataAvailable: Loading new data");
            recyclerViewAdapter.loadNewData(newsDetails);
        }else{
            Log.e(TAG, "onDataAvailable: Failed with status " + status);
        }

        Log.d(TAG, "onDataAvailable: Ends");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_actions, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchIcon:
                Log.d(TAG, "onOptionsItemSelected: App bar search clicked");
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    //INCLUDING MULTIPLE SOURCES
    //https://newsapi.org/v2/top-headlines?sources=abc-news,bbc-news&apiKey=6306fbe477654ab8929fa29582a45127
    private void initTabListener(){
        TabLayout tabLayout = findViewById(R.id.tablayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: " + tab.getText());
                GetNewsJsonData getNewsJsonData;
                switch (tab.getPosition()){
                    case 0://headlines
                        getNewsJsonData = new GetNewsJsonData(MainActivity.this,"https://newsapi.org/v2/", true, "us");
                        getNewsJsonData.execute("");
                        break;
                    case 1://trump
                        getNewsJsonData = new GetNewsJsonData(MainActivity.this,"https://newsapi.org/v2/", true, "us");
                        getNewsJsonData.execute(tab.getText().toString());
                        break;
                    case 2://politics
                        getNewsJsonData = new GetNewsJsonData(MainActivity.this,"https://newsapi.org/v2/", true, "us");
                        getNewsJsonData.execute(tab.getText().toString());
                        break;
                    case 3://sports
                        getNewsJsonData = new GetNewsJsonData(MainActivity.this,"https://newsapi.org/v2/", true, "us");
                        getNewsJsonData.execute(tab.getText().toString());
                        break;
                    case 4://movies
                        getNewsJsonData = new GetNewsJsonData(MainActivity.this,"https://newsapi.org/v2/", true, "us");
                        getNewsJsonData.execute(tab.getText().toString());
                        break;
                    case 5://celebrities
                        getNewsJsonData = new GetNewsJsonData(MainActivity.this,"https://newsapi.org/v2/", true, "us");
                        getNewsJsonData.execute(tab.getText().toString());
                        break;
                    case 6://random
                        getNewsJsonData = new GetNewsJsonData(MainActivity.this,"https://newsapi.org/v2/", true, "us");
                        getNewsJsonData.execute(tab.getText().toString());
                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabUnselected: " + tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabReselected: " + tab.getText());
            }
        });
    }

}


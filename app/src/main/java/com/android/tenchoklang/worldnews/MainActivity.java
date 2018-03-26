

package com.android.tenchoklang.worldnews;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

/*
TO ENABLE BACK THE DEFAULT SEARCH VIEW UNCOMMENT THE
1) onCreateOptionsMenu()
2) onOptionsItemSelected()
 */


public class MainActivity extends AppCompatActivity implements GetNewsJsonData.OnDataAvailable,
                                                                RecyclerItemClickListener.OnRecyclerClickListener,
                                                                MaterialSearchBar.OnSearchActionListener{


    static final String SEARCH_QUERY = "SEARCH_QUERY";//used as the "key" for shared preferences
    private static final String TAG = "MainActivity";
    private RecyclerViewAdapter recyclerViewAdapter;
    private MaterialSearchBar searchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.setPlaceHolder("SEARCH");
        searchBar.setHint("Enter What to Search");
        searchBar.setCardViewElevation(10);

        initTabListener();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //This listener hides the keyboard when a user tries to scroll recycler view
        recyclerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return false;
            }
        });


        //recyler view doesnt take care of handling the layouts, thats done by the layoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(this, new ArrayList<NewsDetail>());
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));

    }

    //these methods are put into the onResume because when the activity returns back to this screen
    //onResume will be called, not onCreate
    //If we put these codes in the on create then the code in the onCreate will not be executed
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String queryResult = sharedPreferences.getString(MainActivity.SEARCH_QUERY, "");//if no values return "" empty string

        Log.d(TAG, "onResume: &*)&&^&!@*#^)@!&^");
        
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


    /*
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
     */



    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putString(MainActivity.SEARCH_QUERY, text.toString()).apply();
        Log.d(TAG, "onSearchConfirmed: " + text);
        hideSoftKeyboard(this);
        onResume();
    }

    @Override
    public void onButtonClicked(int buttonCode) {

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

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: Starts " + recyclerViewAdapter.getNews(position).getUrl());

//        String url = recyclerViewAdapter.getNews(position).getUrl();
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        CustomTabsIntent customTabsIntent = builder.build();
//        customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
//
//        Toast.makeText(MainActivity.this, "normal tap at position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: Starts");
        Toast.makeText(MainActivity.this, "long click at position " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemDoubleClick(View view, int position) {
        Log.d(TAG, "onItemDoubleClick: Starts");
        String url = recyclerViewAdapter.getNews(position).getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));

        Toast.makeText(MainActivity.this, "double tap at position " + position, Toast.LENGTH_SHORT).show();
    }



    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}


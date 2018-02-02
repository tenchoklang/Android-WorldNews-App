package com.android.tenchoklang.worldnews;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity {


    private static final String TAG = "SearchActivity";
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //handleIntent(getIntent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        // Assumes current activity is the searchable activity
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
        searchView.setIconified(false); // Do not iconify the widget; expand it by defaul

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: Called");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putString(MainActivity.SEARCH_QUERY, query).apply();
                searchView.clearFocus();//solves problem with submitting query by keyboards enter key
                finish();//closes the activity and returns to what ever activity called it (MainActivity in our case)
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        setIntent(intent);
//        //handleIntent(intent);
//    }
//
//    //intent to MainActivty and put the search query in intent.putExtra()
//    void handleIntent(Intent intent){
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            Log.d(TAG, "handleIntent: "+ query);
//
//            Intent intentSearch = new Intent(this, MainActivity.class);
//            intentSearch.putExtra(MainActivity.SEARCH_QUERY, query);
//            startActivity(intentSearch);
//        }
//    }
}

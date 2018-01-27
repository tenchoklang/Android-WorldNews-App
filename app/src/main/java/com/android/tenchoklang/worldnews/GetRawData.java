package com.android.tenchoklang.worldnews;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tench on 1/25/2018.
 */
//the constants used to tell the download status
enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK}

class GetRawData extends AsyncTask<String, Void, String>{
    private static final String TAG = "GetRawData";
    private DownloadStatus mDownloadStatus;//refers to the ENUM
    private final OnDownloadComplete mCallBack;

    interface OnDownloadComplete{//used for callback in GetNewsJsonData
        void onDownloadComplete(String data, DownloadStatus status);
    }

    public GetRawData(OnDownloadComplete callBack) {
        this.mCallBack = callBack;
        mDownloadStatus = DownloadStatus.IDLE;
    }

    //runs together on the GetNewsJsonData thread
    void runInSameThread(String newsApiUrl){
        Log.d(TAG, "runInSameThread: Starts");
        if(mCallBack != null){
            mCallBack.onDownloadComplete(doInBackground(newsApiUrl), mDownloadStatus);
        }
        Log.d(TAG, "runInSameThread: Ends");
    }

    //This is not used when this class is running on the same thread as another class
    //used only when this is running on its own thread
    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: " + s);
    }


    //newApiUrl: is the final build up URL received from the GetNewsJsonData class
    @Override
    protected String doInBackground(String... newsApiUrl) {


        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;

        if(newsApiUrl == null){//
            Log.d(TAG, "doInBackground: NewsApiUrl is null");
            mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try{
            URL url = new URL(newsApiUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            Log.d(TAG, "doInBackground: Response code = " + responseCode);
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder result = new StringBuilder();

            for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()){
                result.append(line).append("\n");
            }
            mDownloadStatus = DownloadStatus.OK;

            return result.toString();//return the raw json data as a string

        }catch(MalformedURLException e){
            Log.d(TAG, "doInBackground: MalformedURL exception " + e.getMessage());
        }catch(IOException e){
            Log.d(TAG, "doInBackground: IOException " + e.getMessage());
        }catch(SecurityException e){
            Log.d(TAG, "doInBackground: Security Exception " + e.getMessage());
        }finally {//this is aways executed, called before the return
            if(connection != null){
                connection.disconnect();
            }
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch (IOException e){
                    Log.e(TAG, "doInBackground: Error closing stream" + e.getMessage());
                }
            }
        }

        return null;
    }
}

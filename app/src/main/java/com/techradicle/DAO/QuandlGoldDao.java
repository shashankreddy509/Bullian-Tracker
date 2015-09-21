package com.techradicle.DAO;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by shashankreddy509 on 8/24/15.
 * This call is used to get the Gold rates from server and store.
 */

public class QuandlGoldDao implements GoldDao {

    private final Map<String, String> goldData = new HashMap<>();

    @Override
    public Map<String, String> getLatest() {
        //In this method we will get the Current/Latest Gold Rate
        return goldData;
    }

    private void getGoldRates(JSONArray JsonInput) {
        try {
            String DataOfJsonArray;
            for (int i = 0; i < JsonInput.length(); i++) {
                DataOfJsonArray = JsonInput.getString(i);
                DataOfJsonArray = DataOfJsonArray.substring(1);
                DataOfJsonArray = DataOfJsonArray.substring(0, DataOfJsonArray.length() - 1);
                goldData.put(DataOfJsonArray.split(",")[0].replace("@", ""), DataOfJsonArray.split(",")[1]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getHistory() {
        //In this Method we will ge the history of gold Rates for last 5 days.
        try {
            String QUANDL_GOLD_RATE_ENDPOINT = "https://www.quandl.com/api/v1/datasets/BUNDESBANK/BBK01_WT5511.json?rows=";
            new GetGoldRates().execute(QUANDL_GOLD_RATE_ENDPOINT + "5").get();
        } catch (InterruptedException | ExecutionException ie) {
            ie.printStackTrace();
        }
        return goldData;
    }

    private String getJsonData(String url) {
        try {
            URL mUrl = new URL(url);
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
            mHttpURLConnection.connect();
            if (mHttpURLConnection.getResponseCode() == 200) {
                InputStream is = mHttpURLConnection.getInputStream();
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(is));
                StringBuilder responseStrBuilder = new StringBuilder();
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);
                streamReader.close();
                return responseStrBuilder.toString();
            } else {
                return "";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private class GetGoldRates extends AsyncTask<String, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(String[] params) {
            try {
                getGoldRates(new JSONObject(getJsonData(params[0])).getJSONArray("data"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return goldData;
        }
    }
}

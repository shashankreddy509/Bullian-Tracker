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
 * This class is used to fetch the data from server and parse the data and store.
 */
public class QuandlCurrencyDao implements CurrencyDao {

    private final Map<String, String> currencyData = new HashMap<>();
    private final String QUANDL_EXCHANGE_RATE_ENDPOINT = "https://www.quandl.com/api/v1/datasets/CURRFX/USDINR.json?rows=";
    private String strFrom = "";

    @Override
    public Map<String, String> getLatest() {
        strFrom = "Current";
        new GetCurrency().execute(QUANDL_EXCHANGE_RATE_ENDPOINT + "1");
        return currencyData;
    }

    private void getExchangeRates(JSONArray JsonInput, String type) {
        try {
            String DataOfJsonArray;
            for (int i = 0; i < JsonInput.length(); i++) {
                DataOfJsonArray = JsonInput.getString(i);
                DataOfJsonArray = DataOfJsonArray.substring(1);
                DataOfJsonArray = DataOfJsonArray.substring(0, DataOfJsonArray.length() - 1);

                if (type.equalsIgnoreCase("Current")) {
                    //here we need to add to Dashboard data instead of gold data.
                    currencyData.put(DataOfJsonArray.split(",")[0].replace("@", ""), DataOfJsonArray.split(",")[1]);
                } else {
                    currencyData.put(DataOfJsonArray.split(",")[0].replace("@", ""), DataOfJsonArray.split(",")[1]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getHistory() {
        strFrom = "History";
        try {
            new GetCurrency().execute(QUANDL_EXCHANGE_RATE_ENDPOINT + "5").get();
        } catch (InterruptedException | ExecutionException ie) {
            ie.printStackTrace();
        }
        return currencyData;
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
                return responseStrBuilder.toString();
            } else {
                return "";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private class GetCurrency extends AsyncTask<String, Void, Map<String, String>> {
        @Override
        protected Map<String, String> doInBackground(String[] params) {
            try {
                getExchangeRates(new JSONObject(getJsonData(params[0])).getJSONArray("data"), strFrom);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return currencyData;
        }
    }
}

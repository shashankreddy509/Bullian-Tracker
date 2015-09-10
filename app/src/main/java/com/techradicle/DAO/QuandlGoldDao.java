package com.techradicle.DAO;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shashankreddy509 on 8/24/15.
 * This call is used to get the Gold rates from server and store.
 */

public class QuandlGoldDao implements GoldDao {

    private final BarChart mBarChart;
    private final Map<String, String> goldData = new HashMap<>();
    private final String QUANDL_GOLD_RATE_ENDPOINT = "https://www.quandl.com/api/v1/datasets/BUNDESBANK/BBK01_WT5511.json?rows=";
    private String strFrom = "";
    private BarData data;

    public QuandlGoldDao(BarChart mBarChart) {
        this.mBarChart = mBarChart;
    }

    @Override
    public Map<String, String> getLatest() {
        //In this method we will get the Current/Latest Gold Rate

        strFrom = "Current";
        new GetGoldRates().execute(QUANDL_GOLD_RATE_ENDPOINT + "1");
        return goldData;
    }

    private void getGoldRates(JSONArray JsonInput, String type) {
        try {
            String DataOfJsonArray;
            for (int i = 0; i < JsonInput.length(); i++) {
                DataOfJsonArray = JsonInput.getString(i);
                DataOfJsonArray = DataOfJsonArray.substring(1);
                DataOfJsonArray = DataOfJsonArray.substring(0, DataOfJsonArray.length() - 1);

                if (type.equalsIgnoreCase("Current")) {
                    //here we need to add to Dashboard data instead of gold data.
                    goldData.put(DataOfJsonArray.split(",")[0].replace("@", ""), DataOfJsonArray.split(",")[1]);
                    DashboardDao.labelsDashboard.add(DataOfJsonArray.split(",")[0].replace("@", ""));
                    DashboardDao.priceDashboard.add(DataOfJsonArray.split(",")[1]);
                } else {
                    goldData.put(DataOfJsonArray.split(",")[0].replace("@", ""), DataOfJsonArray.split(",")[1]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getHistory() {
        //In this Method we will ge the history of gold Rates for last 5 days.
        strFrom = "History";
        new GetGoldRates().execute(QUANDL_GOLD_RATE_ENDPOINT + "5");
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
                return responseStrBuilder.toString();
            } else {
                return "";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private class GetGoldRates extends AsyncTask<String, Void, BarData> {

        @Override
        protected BarData doInBackground(String[] params) {
            try {
                getGoldRates(new JSONObject(getJsonData(params[0])).getJSONArray("data"), strFrom);
                for (int i = 0; i < goldData.size(); i++) {
                    ArrayList<BarEntry> price = new ArrayList<>();
                    if (goldData.size() > 0) {
                        String[] str = goldData.keySet().toArray(new String[goldData.size()]);
                        for (int j = 0; j < str.length; j++) {
                            price.add(new BarEntry(Float.parseFloat(goldData.get(str[j])), j));
                        }
                        BarDataSet dataSet = new BarDataSet(price, "Gold Price");
                        data = new BarData(str, dataSet);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(BarData s) {
            mBarChart.setData(s);
            mBarChart.animateY(5000);
        }
    }
}

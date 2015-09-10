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
 * Created by shashankreddy509 on 9/10/15.
 * This class is used to fetch,parse and store the latest data of the gold and Exchange rates.
 */
public class QuandlDashboardDao implements DashboardDao {

    private final BarChart mBarChart;
    private final Map<String, String> mDashBoardData = new HashMap<>();
    private final String QUANDL_EXCHANGE_RATE_ENDPOINT = "https://www.quandl.com/api/v1/datasets/CURRFX/USDINR.json?rows=1";
    private final String QUANDL_GOLD_RATE_ENDPOINT = "https://www.quandl.com/api/v1/datasets/BUNDESBANK/BBK01_WT5511.json?rows=1";
    private BarData data;

    public QuandlDashboardDao(BarChart mBarChart) {
        this.mBarChart = mBarChart;
    }

    @Override
    public Map<String, String> getLatest() {
        new GetDashBoardData().execute(QUANDL_EXCHANGE_RATE_ENDPOINT, QUANDL_GOLD_RATE_ENDPOINT);
        return null;
    }

    private void parseDashboardData(JSONArray JsonInput) {
        try {
            String DataOfJsonArray;
            for (int i = 0; i < JsonInput.length(); i++) {
                DataOfJsonArray = JsonInput.getString(i);
                DataOfJsonArray = DataOfJsonArray.substring(1);
                DataOfJsonArray = DataOfJsonArray.substring(0, DataOfJsonArray.length() - 1);
                mDashBoardData.put(DataOfJsonArray.split(",")[0].replace("@", ""), DataOfJsonArray.split(",")[1]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private class GetDashBoardData extends AsyncTask<String, Void, BarData> {

        @Override
        protected BarData doInBackground(String[] params) {
            try {
                parseDashboardData(new JSONObject(getJsonData(params[0])).getJSONArray("data"));
                parseDashboardData(new JSONObject(getJsonData(params[1])).getJSONArray("data"));
                for (int i = 0; i < mDashBoardData.size(); i++) {
                    ArrayList<BarEntry> price = new ArrayList<>();
                    if (mDashBoardData.size() > 0) {
                        String[] str = mDashBoardData.keySet().toArray(new String[mDashBoardData.size()]);
                        for (int j = 0; j < str.length; j++) {
                            price.add(new BarEntry(Float.parseFloat(mDashBoardData.get(str[j])), j));
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

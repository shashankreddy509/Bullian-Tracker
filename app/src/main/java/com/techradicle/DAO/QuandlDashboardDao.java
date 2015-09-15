package com.techradicle.DAO;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yahoofinance.YahooFinance;


/**
 * Created by shashankreddy509 on 9/10/15.
 * This class is used to fetch,parse and store the latest data of the gold and Exchange rates.
 */
public class QuandlDashboardDao implements DashboardDao {

    private final TextView tvGold, tvUSD, tvStocks;
    private final Map<String, String> mDashBoardData = new HashMap<>();
    private final List<String> mStringList = new ArrayList<>();
    private BarData data;
    private BigDecimal price;

    public QuandlDashboardDao(TextView tvGold, TextView tvUSD, TextView tvStocks) {
        this.tvGold = tvGold;
        this.tvUSD = tvUSD;
        this.tvStocks = tvStocks;
    }

    @Override
    public Map<String, String> getLatest() {
        String QUANDL_EXCHANGE_RATE_ENDPOINT = "https://www.quandl.com/api/v1/datasets/CURRFX/USDINR.json?rows=1";
        String QUANDL_GOLD_RATE_ENDPOINT = "https://www.quandl.com/api/v1/datasets/BUNDESBANK/BBK01_WT5511.json?rows=1";
        new GetDashBoardData().execute(QUANDL_EXCHANGE_RATE_ENDPOINT, QUANDL_GOLD_RATE_ENDPOINT);
        return mDashBoardData;
    }

    private void parseDashboardData(JSONArray JsonInput) {
        try {
            String DataOfJsonArray;
            for (int i = 0; i < JsonInput.length(); i++) {
                DataOfJsonArray = JsonInput.getString(i);
                DataOfJsonArray = DataOfJsonArray.substring(1);
                DataOfJsonArray = DataOfJsonArray.substring(0, DataOfJsonArray.length() - 1);
                mStringList.add(DataOfJsonArray.split(",")[1]);
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
                price = YahooFinance.get("AAPL").getQuote(true).getPrice();
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
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(BarData s) {
            tvGold.setText(mStringList.get(1));
            tvUSD.setText(mStringList.get(0));
            tvStocks.setText(price + "");
        }
    }
}

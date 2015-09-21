package com.techradicle.DAO;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Created by shashankreddy509 on 9/18/15.
 * This is the implementation class for fetching the data for Stocks.
 */
public class YahooStocksDao implements StocksDao {

    private final Map<String, String> mStockData = new HashMap<>();

    @Override
    public Map<String, String> getHistory() {
        try {
            new GetStocksData().execute().get();
        } catch (InterruptedException | ExecutionException ie) {
            ie.printStackTrace();
        }
        return mStockData;
    }

    private class GetStocksData extends AsyncTask<String, Void, Map<String, String>> {
        @Override
        protected Map<String, String> doInBackground(String... params) {
            try {
                Stock goog = YahooFinance.get("GOOG", true);
                String mGoogleHistory = goog.getHistory().toString();
                String[] mGoogleHistoryarray = mGoogleHistory.split("GOOG@");
                for (int i = 1; i < 6; i++) {
                    mGoogleHistory = mGoogleHistoryarray[i].split(":")[1].split(",")[0].split("-")[1];
                    mStockData.put(mGoogleHistoryarray[i].split(":")[0], mGoogleHistory);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mStockData;
        }
    }
}
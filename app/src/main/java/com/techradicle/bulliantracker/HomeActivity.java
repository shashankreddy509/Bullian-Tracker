package com.techradicle.bulliantracker;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.techradicle.DAO.CurrencyDao;
import com.techradicle.DAO.GoldDao;
import com.techradicle.DAO.QuandlCurrencyDao;
import com.techradicle.DAO.QuandlGoldDao;
import com.techradicle.DAO.StocksDao;
import com.techradicle.DAO.YahooStocksDao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;


public class HomeActivity extends AppCompatActivity {

    private GoldDao mGoldDao;
    private CurrencyDao mCurrencyDao;
    private BarData data;
    private BarChart mBarChart;
    private StocksDao mStocksDao;


    public void dashBoardClick(View view) {
        finish();
    }

    public void getCurrencyHistory(View view) {
        getCurrencyHistory(mCurrencyDao.getHistory());
    }

    public void getStocksHistory(View view) {
        getStockHistory(mStocksDao.getHistory());
    }

    public void getGoldHistory(View view) {
        getGoldHistory(mGoldDao.getHistory());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mBarChart = (BarChart) findViewById(R.id.chart1);
        mGoldDao = new QuandlGoldDao();
        mCurrencyDao = new QuandlCurrencyDao();
        mStocksDao = new YahooStocksDao();
        String strValue = this.getIntent().getExtras().getString("Type");
        if (strValue != null && strValue.equalsIgnoreCase("")) {
            if (strValue.equalsIgnoreCase("Gold")) {
                getGoldHistory(mGoldDao.getHistory());
            } else if (strValue.equalsIgnoreCase("Stock")) {
                getStockHistory(mStocksDao.getHistory());
            } else if (strValue.equalsIgnoreCase("USD")) {
                getCurrencyHistory(mCurrencyDao.getHistory());
            }
        }
    }

    private void BindChart(BarData mBarData) {
        mBarChart.setData(mBarData);
        mBarChart.animateY(5000);
    }

    private void getGoldHistory(Map<String, String> mData) {
        for (int i = 0; i < mData.size(); i++) {
            ArrayList<BarEntry> price = new ArrayList<>();
            if (mData.size() > 0) {
                String[] str = mData.keySet().toArray(new String[mData.size()]);
                for (int j = 0; j < str.length; j++) {
                    price.add(new BarEntry(Float.parseFloat(mData.get(str[j])), j));
                }
                BarDataSet dataSet = new BarDataSet(price, "Gold Price");
                data = new BarData(str, dataSet);
            }
        }
        BindChart(data);
    }

    private void getCurrencyHistory(Map<String, String> mData) {
        for (int i = 0; i < mData.size(); i++) {
            ArrayList<BarEntry> price = new ArrayList<>();
            if (mData.size() > 0) {
                String[] str = mData.keySet().toArray(new String[mData.size()]);
                for (int j = 0; j < str.length; j++) {
                    price.add(new BarEntry(Float.parseFloat(mData.get(str[j])), j));
                }
                BarDataSet dataSet = new BarDataSet(price, "Gold Price");
                data = new BarData(str, dataSet);
            }
        }
        BindChart(data);
    }

    private void getStockHistory(Map<String, String> mData) {
        for (int i = 0; i < mData.size(); i++) {
            ArrayList<BarEntry> price = new ArrayList<>();
            if (mData.size() > 0) {
                String[] str = mData.keySet().toArray(new String[mData.size()]);
                for (int j = 0; j < str.length; j++) {
                    price.add(new BarEntry(Float.parseFloat(mData.get(str[j])), j));
                }
                BarDataSet dataSet = new BarDataSet(price, "Gold Price");
                data = new BarData(str, dataSet);
            }
        }
        BindChart(data);
    }
}

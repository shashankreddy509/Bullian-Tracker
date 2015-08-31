package com.techradicle.bulliantracker;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.techradicle.DAO.CurrencyDataFetch;
import com.techradicle.DAO.DashboardDao;
import com.techradicle.DAO.GoldRatesFetch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {

    CurrencyDataFetch mCurrencyDataFetch;
    List<String> labels, prices;
    private BarChart chart1;

    private void getDashBoardData() {

        ArrayList<BarEntry> price = new ArrayList<>();
        if (DashboardDao.labelsDashboard.size() > 0 && DashboardDao.priceDashboard.size() > 0) {

            for (int i = 0; i < DashboardDao.priceDashboard.size(); i++) {
                price.add(new BarEntry(Float.parseFloat(DashboardDao.priceDashboard.get(i)), i));
            }

            BarDataSet dataSet = new BarDataSet(price, "Gold Price");
            BarData data = new BarData(DashboardDao.labelsDashboard, dataSet);
            chart1.setData(data);
            chart1.animateY(5000);
        }
        Log.d("PrintMessage", "Chart Created");
    }

    public void dashBoardClick(View view) {
        getDashBoardData();
    }

    public void getCurrencyHistory(View view) {
        ArrayList<BarEntry> price = new ArrayList<>();
        labels = mCurrencyDataFetch.getLabels();
        prices = mCurrencyDataFetch.getPrice();
        if (labels.size() > 0 && price.size() > 0) {

            for (int i = 0; i < price.size(); i++) {
                price.add(new BarEntry(Float.parseFloat(prices.get(i)), i));
            }

            BarDataSet dataSet = new BarDataSet(price, "Exchange Rate");
            BarData data = new BarData(labels, dataSet);
            chart1.setData(data);
            chart1.animateY(5000);
        }
    }

    public void getStocksHistory(View view) {

    }

    public void getGoldHistory(View view) {
        ArrayList<BarEntry> price = new ArrayList<>();
        if (GoldRatesFetch.labels.size() > 0 && GoldRatesFetch.price.size() > 0) {

            for (int i = 0; i < GoldRatesFetch.price.size(); i++) {
                price.add(new BarEntry(Float.parseFloat(GoldRatesFetch.price.get(i)), i));
            }

            BarDataSet dataSet = new BarDataSet(price, "Gold Price");
            BarData data = new BarData(GoldRatesFetch.labels, dataSet);
            chart1.setData(data);
            chart1.animateY(5000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GoldRatesFetch goldDataFetch = new GoldRatesFetch(getApplicationContext());
        CurrencyDataFetch currencyDataFetch = new CurrencyDataFetch(getApplicationContext());
        chart1 = (BarChart) findViewById(R.id.chart1);

        DashboardDao.labelsDashboard.clear();
        DashboardDao.priceDashboard.clear();
        GoldRatesFetch.labels.clear();
        GoldRatesFetch.price.clear();
        goldDataFetch.GetGoldHistory();
        goldDataFetch.GetCurrentGoldPrice();
        currencyDataFetch.getCurrent();
        currencyDataFetch.getHistory();

        getDashBoardData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

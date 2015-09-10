package com.techradicle.bulliantracker;

import com.github.mikephil.charting.charts.BarChart;
import com.techradicle.DAO.CurrencyDao;
import com.techradicle.DAO.DashboardDao;
import com.techradicle.DAO.GoldDao;
import com.techradicle.DAO.QuandlCurrencyDao;
import com.techradicle.DAO.QuandlDashboardDao;
import com.techradicle.DAO.QuandlGoldDao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private GoldDao mGoldDao;
    private CurrencyDao mCurrencyDao;
    private DashboardDao mDashboardDao;


    private void getDashBoardData() {
        mDashboardDao.getLatest();
        Log.d("PrintMessage", "Chart Created");
    }

    public void dashBoardClick(View view) {
        getDashBoardData();
    }

    public void getCurrencyHistory(View view) {
        mCurrencyDao.getHistory();
//        ArrayList<BarEntry> price = new ArrayList<>();
////
////        HashMap<String, String> currencyData = mQuandlCurrencyDao.getCurrencyData();
////
//        if (mCurrenctData.size() > 0) {
//            String[] str = mCurrenctData.keySet().toArray(new String[mCurrenctData.size()]);
//            for (int j = 0; j < str.length; j++) {
//                price.add(new BarEntry(Float.parseFloat(mCurrenctData.get(str[j])), j));
//            }
//            BarDataSet dataSet = new BarDataSet(price, "Exchange Rate");
//            BarData data = new BarData(str, dataSet);
//            chart1.setData(data);
//            chart1.animateY(5000);
//        }
    }

    public void getStocksHistory(View view) {

    }

    public void getGoldHistory(View view) {
        mGoldDao.getHistory();
//        ArrayList<BarEntry> price = new ArrayList<>();
//        if (mGoldData.size() > 0) {
//            String[] str = mGoldData.keySet().toArray(new String[mGoldData.size()]);
//            for (int j = 0; j < str.length; j++) {
//                price.add(new BarEntry(Float.parseFloat(mGoldData.get(str[j])), j));
//            }
//            BarDataSet dataSet = new BarDataSet(price, "Gold Price");
//            BarData data = new BarData(str, dataSet);
//            chart1.setData(data);
//            chart1.animateY(5000);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mGoldDao = new QuandlGoldDao((BarChart) findViewById(R.id.chart1));
        mCurrencyDao = new QuandlCurrencyDao((BarChart) findViewById(R.id.chart1));
        mDashboardDao = new QuandlDashboardDao((BarChart) findViewById(R.id.chart1));
        mDashboardDao.getLatest();
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
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

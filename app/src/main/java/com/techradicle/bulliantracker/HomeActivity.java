package com.techradicle.bulliantracker;

import com.github.mikephil.charting.charts.BarChart;
import com.techradicle.DAO.CurrencyDao;
import com.techradicle.DAO.GoldDao;
import com.techradicle.DAO.QuandlCurrencyDao;
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


    private void getDashBoardData() {
        Log.d("PrintMessage", "Chart Created");
    }

    public void dashBoardClick(View view) {
        getDashBoardData();
    }

    public void getCurrencyHistory(View view) {
        mCurrencyDao.getHistory();
    }

    public void getStocksHistory(View view) {

    }

    public void getGoldHistory(View view) {
        mGoldDao.getHistory();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mGoldDao = new QuandlGoldDao((BarChart) findViewById(R.id.chart1));
        mCurrencyDao = new QuandlCurrencyDao((BarChart) findViewById(R.id.chart1));
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

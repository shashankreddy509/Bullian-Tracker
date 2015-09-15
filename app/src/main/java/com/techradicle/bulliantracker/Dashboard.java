package com.techradicle.bulliantracker;

import com.techradicle.DAO.DashboardDao;
import com.techradicle.DAO.QuandlDashboardDao;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by shashankreddy509 on 9/15/15.
 */
public class Dashboard extends Activity {

    private Button btnHome;
    private Button btnGold;
    private Button btnUSD;
    private Button btnStocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        btnGold = (Button) findViewById(R.id.btnGold);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnStocks = (Button) findViewById(R.id.btnStocks);
        btnUSD = (Button) findViewById(R.id.btnStocks);

        DashboardDao dashboardDao = new QuandlDashboardDao((TextView) findViewById(R.id.tvGoldRate), (TextView) findViewById(R.id.tvUSD), (TextView) findViewById(R.id.tvStock));
        dashboardDao.getLatest();

    }
}

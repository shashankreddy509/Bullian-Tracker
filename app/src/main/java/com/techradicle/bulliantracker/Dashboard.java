package com.techradicle.bulliantracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import yahoofinance.YahooFinance;

/**
 * Created by shashankreddy509 on 9/15/15.
 * This is the Home activity of the application.
 * It displays the present rates of Gold, Stocks and USD.
 */
public class Dashboard extends Activity {

    private final List<String> mStringList = new ArrayList<>();
    private final String QUANDL_EXCHANGE_RATE_ENDPOINT = "https://www.quandl.com/api/v1/datasets/CURRFX/USDINR.json?rows=1";
    private final String QUANDL_GOLD_RATE_ENDPOINT = "https://www.quandl.com/api/v1/datasets/BUNDESBANK/BBK01_WT5511.json?rows=1";
    private TextView tvGoldRates;
    private TextView tvUSD;
    private TextView tvStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Button btnGold = (Button) findViewById(R.id.btnGold);
        Button btnHome = (Button) findViewById(R.id.btnHome);
        Button btnStocks = (Button) findViewById(R.id.btnStocks);
        Button btnUSD = (Button) findViewById(R.id.btnUSD);

        tvGoldRates = (TextView) findViewById(R.id.tvGoldRate);
        tvStock = (TextView) findViewById(R.id.tvStock);
        tvUSD = (TextView) findViewById(R.id.tvUSD);

        new GetDashBoardData().execute(QUANDL_EXCHANGE_RATE_ENDPOINT, QUANDL_GOLD_RATE_ENDPOINT);


        btnGold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intMain = new Intent(getApplicationContext(), HomeActivity.class);
                intMain.putExtra("Type", "Gold");
                startActivity(intMain);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetDashBoardData().execute(QUANDL_EXCHANGE_RATE_ENDPOINT, QUANDL_GOLD_RATE_ENDPOINT);
            }
        });

        btnStocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intMain = new Intent(getApplicationContext(), HomeActivity.class);
                intMain.putExtra("Type", "Stock");
                startActivity(intMain);
            }
        });

        btnUSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intMain = new Intent(getApplicationContext(), HomeActivity.class);
                intMain.putExtra("Type", "USD");
                startActivity(intMain);
            }
        });

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

    private class GetDashBoardData extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String[] params) {
            try {
                parseDashboardData(new JSONObject(getJsonData(params[0])).getJSONArray("data"));
                parseDashboardData(new JSONObject(getJsonData(params[1])).getJSONArray("data"));
                mStringList.add(YahooFinance.get("AAPL").getQuote(true).getPrice() + "");
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return mStringList;
        }

        @Override
        protected void onPostExecute(List<String> s) {
            tvGoldRates.setText(s.get(1));
            tvUSD.setText(s.get(0));
            tvStock.setText(s.get(2));
        }
    }

}

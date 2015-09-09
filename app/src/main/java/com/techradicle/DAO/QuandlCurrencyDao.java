package com.techradicle.DAO;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by shashankreddy509 on 8/24/15.
 * This class is used to fetch the data from server and parse the data and store.
 */
public class QuandlCurrencyDao implements CurrencyDao {

    private final HashMap<String, String> currencyData = new HashMap<>();

    private final String QUANDL_EXCHANGE_RATE_ENDPOINT = "https://www.quandl.com/api/v1/datasets/CURRFX/USDINR.json?rows=";
    private final Context mContext;
    private JSONArray jsonArrayData;

    public QuandlCurrencyDao(Context context) {
        mContext = context;
    }

    @Override
    public HashMap<String, String> getLatest() {
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, QUANDL_EXCHANGE_RATE_ENDPOINT + "1", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArrayData = response.getJSONArray("data");

                            getExchangeRates(jsonArrayData, "Current");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        Volley.newRequestQueue(mContext).add(jsonRequest);
        return currencyData;
    }

    private void getExchangeRates(JSONArray JsonInput, String type) {
        try {
            String DataOfJsonArray;
            for (int i = 0; i < JsonInput.length(); i++) {
                DataOfJsonArray = JsonInput.getString(i);
                DataOfJsonArray = DataOfJsonArray.substring(1);
                DataOfJsonArray = DataOfJsonArray.substring(0, DataOfJsonArray.length() - 1);

                if (type.equalsIgnoreCase("Current")) {
                    //here we need to add to Dashboard data instead of gold data.
                    currencyData.put(DataOfJsonArray.split(",")[0].replace("@", ""), DataOfJsonArray.split(",")[1]);
                    DashboardDao.labelsDashboard.add(DataOfJsonArray.split(",")[0].replace("@", ""));
                    DashboardDao.priceDashboard.add(DataOfJsonArray.split(",")[1]);
                } else {
                    currencyData.put(DataOfJsonArray.split(",")[0].replace("@", ""), DataOfJsonArray.split(",")[1]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public HashMap<String, String> getHistory() {
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, QUANDL_EXCHANGE_RATE_ENDPOINT + "5", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArrayData = response.getJSONArray("data");
                            getExchangeRates(jsonArrayData, "History");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(mContext).add(jsonRequest);
        return currencyData;
    }

    public HashMap<String, String> getCurrencyData() {
        return this.currencyData;
    }
}

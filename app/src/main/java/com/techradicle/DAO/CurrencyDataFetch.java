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

/**
 * Created by shashankreddy509 on 8/24/15.
 * This class is used to fetch the data from server and parse the data and store.
 */
public class CurrencyDataFetch implements CurrencyDao {

    private Context mContext;
    private JSONArray jsonArrayData;

    public CurrencyDataFetch(Context context) {
        mContext = context;
    }

    @Override
    public void GetCurrentCurrency() {
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, CurrencyDataFetch.urlExchangePrice + "1", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArrayData = response.getJSONArray("data");

                            GetExchangeRates(jsonArrayData, "Current");

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
    }

    private void GetExchangeRates(JSONArray JsonInput, String type) {
        try {
            String DataOfJsonArray;
            for (int i = 0; i < JsonInput.length(); i++) {
                DataOfJsonArray = JsonInput.getString(i);
                DataOfJsonArray = DataOfJsonArray.substring(1);
                DataOfJsonArray = DataOfJsonArray.substring(0, DataOfJsonArray.length() - 1);

                if (type.equalsIgnoreCase("Current")) {
                    DashboardDao.labelsDashboard.add(DataOfJsonArray.split(",")[0].replace("@", ""));
                    DashboardDao.priceDashboard.add(DataOfJsonArray.split(",")[1]);
                } else {
                    assert CurrencyDataFetch.labels != null;
                    CurrencyDataFetch.labels.add(DataOfJsonArray.split(",")[0].replace("@", ""));
                    assert CurrencyDataFetch.price != null;
                    CurrencyDataFetch.price.add(DataOfJsonArray.split(",")[1]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void GetCurrencyHistory() {
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, CurrencyDao.urlExchangePrice + "5", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArrayData = response.getJSONArray("data");
                            GetExchangeRates(jsonArrayData, "History");
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
    }
}

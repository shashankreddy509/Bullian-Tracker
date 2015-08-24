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
 * This call is used to get the Gold rates from server and store.
 */

public class GoldRatesFetch implements GoldRatesDao {

    private final Context mContext;
    private JSONArray jsonArrayData;

    public GoldRatesFetch(Context context) {
        mContext = context;
    }

    @Override
    public void GetCurrentGoldPrice() {
        //In this method we will get the Current/Latest Gold Rate
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, GoldRatesDao.urlGoldPrice + "1", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArrayData = response.getJSONArray("data");

                            GetGoldRates(jsonArrayData, "Current");

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

    private void GetGoldRates(JSONArray JsonInput, String type) {
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
                    assert GoldRatesDao.labels != null;
                    GoldRatesDao.labels.add(DataOfJsonArray.split(",")[0].replace("@", ""));
                    assert GoldRatesDao.price != null;
                    GoldRatesDao.price.add(DataOfJsonArray.split(",")[1]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void GetGoldHistory() {
        //In this Method we will ge the history of gold Rates for last 5 days.
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, GoldRatesDao.urlGoldPrice + "5", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArrayData = response.getJSONArray("data");
                            GetGoldRates(jsonArrayData, "History");
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

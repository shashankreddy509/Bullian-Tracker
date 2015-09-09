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
 * This call is used to get the Gold rates from server and store.
 */

public class QuandlGoldDao implements GoldDao {

    private final Context mContext;

    private final HashMap<String, String> goldData = new HashMap<>();

    private final String QUANDL_GOLD_RATE_ENDPOINT = "https://www.quandl.com/api/v1/datasets/BUNDESBANK/BBK01_WT5511.json?rows=";

    public QuandlGoldDao(Context context) {
        mContext = context;
    }

    @Override
    public HashMap<String, String> getLatest() {
        //In this method we will get the Current/Latest Gold Rate
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, QUANDL_GOLD_RATE_ENDPOINT + "1", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            getGoldRates(response.getJSONArray("data"), "Current");
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
        return goldData;
    }

    private void getGoldRates(JSONArray JsonInput, String type) {
        try {
            String DataOfJsonArray;
            for (int i = 0; i < JsonInput.length(); i++) {
                DataOfJsonArray = JsonInput.getString(i);
                DataOfJsonArray = DataOfJsonArray.substring(1);
                DataOfJsonArray = DataOfJsonArray.substring(0, DataOfJsonArray.length() - 1);

                if (type.equalsIgnoreCase("Current")) {
                    //here we need to add to Dashboard data instead of gold data.
                    goldData.put(DataOfJsonArray.split(",")[0].replace("@", ""), DataOfJsonArray.split(",")[1]);
                    DashboardDao.labelsDashboard.add(DataOfJsonArray.split(",")[0].replace("@", ""));
                    DashboardDao.priceDashboard.add(DataOfJsonArray.split(",")[1]);
                } else {
                    goldData.put(DataOfJsonArray.split(",")[0].replace("@", ""), DataOfJsonArray.split(",")[1]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public HashMap<String, String> getHistory() {
        //In this Method we will ge the history of gold Rates for last 5 days.
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, QUANDL_GOLD_RATE_ENDPOINT + "5", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            getGoldRates(response.getJSONArray("data"), "History");
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
        return goldData;
    }

    public HashMap<String, String> getGoldData() {
        return this.goldData;
    }
}

package com.techradicle.bulliantracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Home extends AppCompatActivity {

    private ArrayList<BarEntry> Price;
    private ArrayList<String> labels;

    private JSONArray jsonArrayData;
    private BarChart chart1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Urls urls = new Urls();

        chart1 = (BarChart) findViewById(R.id.chart1);
        labels = new ArrayList<>();
        Price = new ArrayList<>();

        //Json Request
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, urls.GoldRatesUrl(5), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jsonArrayData = response.getJSONArray("data");
                            String DataOfJsonArray;
                            for (int i = 0; i < 2; i++) {
                                DataOfJsonArray = jsonArrayData.getString(i);
                                DataOfJsonArray = DataOfJsonArray.substring(1);
                                DataOfJsonArray = DataOfJsonArray.substring(0, DataOfJsonArray.length() - 1);
                                labels.add(DataOfJsonArray.split(",")[0].replace("@", ""));
                                Price.add(new BarEntry(Float.parseFloat(DataOfJsonArray.split(",")[1]), i));
                            }

                            BarDataSet dataSet = new BarDataSet(Price, "Gold Price");
                            BarData data = new BarData(labels, dataSet);
                            chart1.setData(data);

                            chart1.setDescription("# of times Alice called BOB");

                            chart1.animateY(5000);

                            System.out.println("Chart Created");
                        } catch (JSONException e) {
                            System.out.println("Caught Exception");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(this).add(jsonRequest);
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

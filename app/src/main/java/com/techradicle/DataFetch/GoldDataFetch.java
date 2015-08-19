package com.techradicle.DataFetch;

import com.github.mikephil.charting.data.BarEntry;
import com.techradicle.DAO.DashboardDao;
import com.techradicle.DAO.GoldRatesDao;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by shashankreddy509 on 8/18/15.
 */
@SuppressWarnings("ALL")
public class GoldDataFetch {

    public GoldDataFetch() {
        GoldRatesDao.price = new ArrayList<>();
        GoldRatesDao.labels = new ArrayList<>();
        DashboardDao.labelsDashboard = new ArrayList<>();
        DashboardDao.priceDashboard = new ArrayList<>();
    }

    public boolean FetchGoldData(JSONArray goldJsonData) {
        try {
            String DataOfJsonArray;
            for (int i = 0; i < goldJsonData.length(); i++) {
                DataOfJsonArray = goldJsonData.getString(i);
                DataOfJsonArray = DataOfJsonArray.substring(1);
                DataOfJsonArray = DataOfJsonArray.substring(0, DataOfJsonArray.length() - 1);
                GoldRatesDao.labels.add(DataOfJsonArray.split(",")[0].replace("@", ""));
                GoldRatesDao.price.add(new BarEntry(Float.parseFloat(DataOfJsonArray.split(",")[1]), i));
                if (i == 0) {
                    DashboardDao.labelsDashboard.add(DataOfJsonArray.split(",")[0].replace("@", ""));
                    DashboardDao.priceDashboard.add(new BarEntry(Float.parseFloat(DataOfJsonArray.split(",")[1]), 0));
                }
            }
            if (GoldRatesDao.price.size() > 0 && GoldRatesDao.labels.size() > 0) {
                if (GoldRatesDao.labels.size() == GoldRatesDao.price.size())
                    return true;
                else
                    return false;
            } else
                return false;
        } catch (Exception ex) {
            return false;
        }
    }
}

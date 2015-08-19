package com.techradicle.DAO;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by shashankreddy509 on 8/18/15.
 */
@SuppressWarnings("ALL")
public class GoldRatesDao {

    public static ArrayList<BarEntry> price;
    public static ArrayList<String> labels;

    public GoldRatesDao() {
        price = new ArrayList<>();
        labels = new ArrayList<>();
    }

}

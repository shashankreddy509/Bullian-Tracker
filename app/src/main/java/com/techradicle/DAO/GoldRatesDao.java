package com.techradicle.DAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shashankreddy509 on 8/18/15.
 * This interface is DAO for Gold rates.
 */

interface GoldRatesDao {

    List<String> price = new ArrayList<>();
    List<String> labels = new ArrayList<>();

    String urlGoldPrice = "https://www.quandl.com/api/v1/datasets/BUNDESBANK/BBK01_WT5511.json?rows=";


    void GetCurrentGoldPrice();

    void GetGoldHistory();

}

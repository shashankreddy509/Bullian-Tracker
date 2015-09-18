package com.techradicle.DAO;

import java.util.Map;

/**
 * Created by shashankreddy509 on 9/18/15.
 * This interface for Stock Data.
 */
public interface StocksDao {

    //This Method gets the history of Stocks rates for the past 5 days.
    Map<String, String> getHistory();
}

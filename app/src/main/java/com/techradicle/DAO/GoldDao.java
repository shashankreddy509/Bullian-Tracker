package com.techradicle.DAO;

import java.util.HashMap;

/**
 * Created by shashankreddy509 on 8/18/15.
 * This interface is DAO for Gold rates.
 */

interface GoldDao {

    //This Method Gets the latest gold Rates from the given service.
    HashMap<String, String> getLatest();

    //This Method gets the history of gold rates for the past 5 days.
    HashMap<String, String> getHistory();

}

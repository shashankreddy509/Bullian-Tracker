package com.techradicle.DAO;

import java.util.Map;

/**
 * Created by shashankreddy509 on 8/18/15.
 * This interface is DAO for Gold rates.
 */

public interface GoldDao {

    //This Method Gets the latest gold Rates from the given service.
    Map<String, String> getLatest();

    //This Method gets the history of gold rates for the past 5 days.
    Map<String, String> getHistory();

}

package com.techradicle.DAO;

/**
 * Created by shashankreddy509 on 8/18/15.
 * This interface is DAO for Gold rates.
 */

interface QuandlGoldDao {

    //This Method Gets the latest gold Rates from the given service.
    void getCurrent();

    //This Method gets the history of gold rates for the past 5 days.
    void getHistory();

}

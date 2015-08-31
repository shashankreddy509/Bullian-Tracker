package com.techradicle.DAO;

/**
 * Created by shashankreddy509 on 8/24/15.
 * This interface is DAO for Currency Data.
 */

public interface CurrencyDao {

    //Another API url
//    http://apilayer.net/api/live?access_key=d1d6655c6607f6aa395a9e99091ec709&currencies=EUR,GBP,INR&source=USD&format=1

    //This Method Gets the latest Gold Rates from the given service.
    void getCurrent();

    //This Method gets the history of Gold rates for the past 5 days.
    void getHistory();
}

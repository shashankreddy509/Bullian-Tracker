package com.techradicle.DAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shashankreddy509 on 8/24/15.
 * This interface is DAO object for Currency Data.
 */

public interface CurrencyDao {

    //Another API url
//    http://apilayer.net/api/live?access_key=d1d6655c6607f6aa395a9e99091ec709&currencies=EUR,GBP,INR&source=USD&format=1

    List<String> price = new ArrayList<>();
    List<String> labels = new ArrayList<>();

    String urlExchangePrice = "https://www.quandl.com/api/v1/datasets/CURRFX/USDINR.json?rows=";


    void GetCurrentCurrency();

    void GetCurrencyHistory();
}

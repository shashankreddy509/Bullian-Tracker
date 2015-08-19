package com.techradicle.bulliantracker;

/**
 * Created by shashankreddy509 on 8/14/15.
 */
@SuppressWarnings("ALL")
class Urls
{

    public String GoldRatesUrl(int count)
    {
//        return "https://www.quandl.com/api/v1/datasets/BUNDESBANK/BBK01_WT5511.csv";
        if(count == 0)
            return "https://www.quandl.com/api/v1/datasets/BUNDESBANK/BBK01_WT5511.json";
        else
            return "https://www.quandl.com/api/v1/datasets/BUNDESBANK/BBK01_WT5511.json?rows="+count;
    }

    public String GetCurrencyRates()
    {
        return "http://apilayer.net/api/live?access_key=d1d6655c6607f6aa395a9e99091ec709&currencies=EUR,GBP,CAD,PLN,INR&source=USD&format=1";
//        return "http://www.apilayer.net/api/live?access_key=d1d6655c6607f6aa395a9e99091ec709";
    }
}

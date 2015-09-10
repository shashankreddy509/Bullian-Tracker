package com.techradicle.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shashankreddy509 on 8/18/15.
 * This interface is DAO for
 */

public interface DashboardDao {

    List<String> priceDashboard = new ArrayList<>();
    List<String> labelsDashboard = new ArrayList<>();

    //This method gets the latest gold and currency rates from the given service.
    Map<String, String> getLatest();

}

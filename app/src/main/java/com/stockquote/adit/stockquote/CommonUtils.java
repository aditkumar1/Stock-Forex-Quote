package com.stockquote.adit.stockquote;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

/**
 * Created by adi on 12/9/2017.
 */

public class CommonUtils {
    public static String paasEndPointURL="https://aditstock-developer-edition.na73.force.com/stock/services/apexrest/request";
    public static String APIkey="KLFU6DPQBTRILH00";
    public static String listStockFetchUrl="https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&interval=1min&outputsize=compact&apikey="+APIkey+"&datatype=csv&symbol=";
    public static String stockFetchUrl ="https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&apikey="+APIkey+"&datatype=csv&symbol=";
    public static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    public synchronized static String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }
}

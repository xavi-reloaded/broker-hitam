package org.apiumtech.brokerhitam;

import cern.colt.list.DoubleArrayList;
import com.androidxtrem.commonsHelpers.FileHelper;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: xavi
 * Date: 6/9/13
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class StockHelper {

    public static String getDate(String str) {
        return str.split(",")[0];
    }

    public static double getValue(String str) {
        String s = str.split(",\"")[1];
        String replace = s.replace(".", "");
        replace = replace.replace("\"", "");
        replace = replace.replace(",", ".");
        return Double.parseDouble(replace);
    }

    public static DoubleArrayList getValueDoubleArrayList() throws IOException {
        String stockFile = StockConstants.getFileName(StockMarkets.IBEX);

        InputStream ibexFile = StockHelper.class.getResourceAsStream("/" + stockFile);
        String[] ibexRows = FileHelper.fileToString(ibexFile).split("\n");

        DoubleArrayList ibexValuesLists = new DoubleArrayList(1);
        for (String str : ibexRows ) {
            ibexValuesLists.add( getValue(str) );
        }
        return ibexValuesLists;
    }

    public static HashMap<Date,Double> getStockDataHashMapByDay(String stock) throws IOException, ParseException {
        String stockFile = StockConstants.getFileName(stock);
        InputStream file = StockHelper.class.getResourceAsStream("/" + stockFile);
        String[] rows = FileHelper.fileToString(file).split("\n");
        HashMap<Date,Double> stockHashMap = new HashMap<Date,Double>();

        for (String str : rows ) {
            Date date = extractDateFromString( getDate(str) );
            Double value = getValue(str);

            if (date != null) {
                stockHashMap.put(date, value);
            }
        }
        return stockHashMap;
    }

    public static Date extractDateFromString(String str) throws ParseException
    {
        if (str.equals("00:00")) return null;
        return new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH).parse(str);
    }

    public static DoubleArrayList getStockArrayByDateRange(HashMap<Date,Double> hashMap, Date iniDate, Date endDate) {
        DoubleArrayList valueList = new DoubleArrayList();

        for (Date key : hashMap.keySet())
        {
            //              key is after iniDate
            //              key is before endDate
            if ((key.compareTo(iniDate) > 0) && (key.compareTo(endDate)<0))
            {
                valueList.add(hashMap.get(key));
            }
        }

        return valueList;
    }
}

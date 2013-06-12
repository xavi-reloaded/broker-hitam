package org.apiumtech.brokerhitam;

import cern.colt.list.DoubleArrayList;
import com.androidxtrem.commonsHelpers.FileHelper;

import java.io.IOException;
import java.io.InputStream;

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
            if (str.equals("Fecha,Último,Apert.,%Dif.,Máx.,Mín.,Volumen")) continue;
            ibexValuesLists.add( getValue(str) );
        }
        return ibexValuesLists;
    }
}

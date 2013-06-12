package org.apiumtech.brokerhitam;

import cern.colt.list.DoubleArrayList;
import com.androidxtrem.commonsHelpers.FileHelper;
import org.apiumtech.brokerhitam.CorrelationsTools;
import org.apiumtech.brokerhitam.StockHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: xavi
 * Date: 6/9/13
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class StockHelperTest {

    @Test
    public void test_Name() throws Exception {
        InputStream ibexFile = CorrelationsTools.class.getResourceAsStream("/ibex.csv");
        String[] we = FileHelper.fileToString(ibexFile).split("\n");
        Assert.assertEquals(we.length,4483);
    }

    @Test
    public void test_getDate_stockRow_stringDate() throws Exception {
        String str = "7/6/2013,\"8.266,60\",\"8.229,20\",\"0,6%\",\"8.308,70\",\"8.165,10\",297.871.458";
        String date = StockHelper.getDate(str);
        String expected = "7/6/2013";
        Assert.assertEquals(date,expected);
    }

    @Test
    public void test_getValue_stockRow_value() throws Exception {
        String str = "7/6/2013,\"8.266,60\",\"8.229,20\",\"0,6%\",\"8.308,70\",\"8.165,10\",297.871.458";
        double date = StockHelper.getValue(str);
        double expected = 8266.60;
        Assert.assertEquals(date,expected);
    }

    @Test
    public void test_getValueDoubleArrayList_() throws Exception {

        DoubleArrayList data = StockHelper.getValueDoubleArrayList();
        int expected = 4482;
        Assert.assertEquals(data.size(),expected);

        String stocklist = data.partFromTo(1, 10).toString();
        String expectedStoclList = "[8216.7, 8290.7, 8363.0, 8284.4, 8320.6, 8433.5, 8441.7, 8511.3, 8363.6, 8264.6]";
        Assert.assertEquals(stocklist,expectedStoclList);


    }


}

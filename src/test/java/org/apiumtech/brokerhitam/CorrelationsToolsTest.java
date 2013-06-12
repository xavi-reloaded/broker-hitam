package org.apiumtech.brokerhitam;

import cern.colt.list.DoubleArrayList;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: xavi
 * Date: 6/9/13
 * Time: 8:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class CorrelationsToolsTest {

    private DoubleArrayList data;

    @BeforeMethod
    public void setUp() throws Exception {
        data = HelperTests.getDoubleArrayList();
    }

    @Test
    public void test_autoCorrelation_perfectCorrelation() throws Exception {
        double actual = CorrelationsTools.getPearsonCorrelation(data, data);
        double expected = 0.9999999999999999;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_autoCorrelation_noCorrelation() throws Exception {
        DoubleArrayList data2 = new DoubleArrayList();
        data2.add(123);
        data2.add(333);
        data2.add(100);
        data2.add(1231231);
        double actual = CorrelationsTools.getPearsonCorrelation(data, data2);
        double expected = 0.48130301568777434;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_getTrendByMinimumSquare() throws Exception {
        DoubleArrayList data = new DoubleArrayList();
        data.add(3.4);data.add(3.1);data.add(3.9);data.add(3.3);data.add(3.2);data.add(4.3);data.add(3.9);data.add(3.5);data.add(3.6);data.add(3.7);
        data.add(4);data.add(3.6);data.add(4.1);data.add(4.7);data.add(4.2);data.add(4.5);
        double actual = CorrelationsTools.getTrendByMinimumSquare(data);
        double expected = 4.407499999999997;
        Assert.assertEquals(actual, expected);

    }
}

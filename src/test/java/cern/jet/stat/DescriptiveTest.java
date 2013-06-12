package cern.jet.stat;

import cern.colt.list.DoubleArrayList;
import org.apiumtech.brokerhitam.HelperTests;
import org.apiumtech.brokerhitam.StockHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created with IntelliJ IDEA.
 * User: xavi
 * Date: 6/9/13
 * Time: 7:19 AM
 * To change this template use File | Settings | File Templates.
 */

public class DescriptiveTest {


    private Descriptive sut;
    private DoubleArrayList data;

    @BeforeMethod
    public void setUp() throws Exception {
        data = HelperTests.getDoubleArrayList();
    }

    @Test
    public void test_autoCorrelation() throws Exception {

        int lag = 1;
        double mean = 1.3;
        double variance = 1.3;
        double actual = Descriptive.autoCorrelation(data, lag, mean, variance);
        double expected = 0.00512820512820512;
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void test_geometricMean() throws Exception {
        double actual = Descriptive.geometricMean(data);
        double expected = 1.3453521380961204;
        Assert.assertEquals(actual,expected);

    }

    @Test
    public void test_meanDeviation() throws Exception {
        double actual = Descriptive.meanDeviation(data,1.3453521380961204);
        double expected = 0.09999999999999998;
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void test_kurtosis() throws Exception {
        double mean = 1.3453521380961204;
        double standardDeviation = 0.09999999999999998;
        double actual = Descriptive.kurtosis(data, mean, standardDeviation);
        double expected = -0.42129336805981676;
        Assert.assertEquals(actual,expected);

    }

    @Test
    public void test_max() throws Exception {
        DoubleArrayList data = StockHelper.getValueDoubleArrayList();
        double actual = Descriptive.max(data);
        double expected = 15945.7;
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void test_mean() throws Exception {
        DoubleArrayList data = StockHelper.getValueDoubleArrayList();
        double actual = Descriptive.mean(data);
        double expected = 9269.44733601075;
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void test_min() throws Exception {
        DoubleArrayList data = StockHelper.getValueDoubleArrayList();
        double actual = Descriptive.min(data);
        double expected = 3651.31;
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void test_median() throws Exception {
        DoubleArrayList data = StockHelper.getValueDoubleArrayList();
        data.sort();
        double actual = Descriptive.median(data);
        double expected = 9291.7;
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void test_sumOfSquares() throws Exception {
        DoubleArrayList data = StockHelper.getValueDoubleArrayList();
        double actual = Descriptive.sumOfSquares(data);
        double expected = 4.148927247413851E11;
        Assert.assertEquals(actual,expected);
    }
}

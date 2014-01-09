package org.apiumtech.brokerhitam.trade.trade;

public class TimeSeriesTest{

//    public DateTime today;
//    public DateTime yesterday;
//    public DateTime twoDaysAgo;
//    public TreeMap<DateTime, BigDecimal> prices;
//    public TimeSeries series;
//
//    @BeforeMethod
//    public void setUp() throws Exception {
//        today = new DateTime();
//        yesterday = today.minusDays(1);
//        twoDaysAgo = today.minusDays(2);
//
//        prices = new TreeMap<DateTime, BigDecimal>();
//        prices.put(today, new BigDecimal(9.0));
//        prices.put(yesterday, new BigDecimal(11.0));
//        prices.put(twoDaysAgo, new BigDecimal(10.0));
//
//        series = new TimeSeries(prices);
//    }
//    @Test
//    public void testOpen() {
//        DateTime oneMinuteLater = today.plusMinutes(1);
//        prices.put(oneMinuteLater, new BigDecimal(9.1));
//
//        Assert.assertEquals(series.openOnDay(today), new BigDecimal(9.0));
//        Assert.assertEquals(series.openOnDay(oneMinuteLater), new BigDecimal(9.0));
//        Assert.assertEquals(series.openOnDay(yesterday), new BigDecimal(11.0));
//    }
//    @Test
//    public void testClose() {
//
//        Assert.assertEquals(series.closeOnDay(today), today);
//
//        DateTime oneMinuteLater = today.plusMinutes(1);
//        prices.put(oneMinuteLater, new BigDecimal(9.1));
//
//        Assert.assertEquals(series.closeOnDay(today), oneMinuteLater);
//    }
//    @Test
//    public void testRemove() {
//        series.removeDays(1);
//        Assert.assertEquals(series.beginningOfSeries(), yesterday);
//    }
//    @Test
//    public void testPriceQuerying() throws Exception {
//        Assert.assertEquals(new BigDecimal(11), series.priceAt(yesterday));
//    }
//    @Test
//    public void testSlicing() throws Exception {
//        prices.put(today.plusDays(1), BigDecimal.ZERO);
//        prices.put(today.plusDays(2), BigDecimal.ZERO);
//        prices.put(today.plusDays(7), BigDecimal.ZERO);
//
//        series = new TimeSeries(prices);
//
//        SortedMap<DateTime, BigDecimal> slice = series.dateSlice(today.plusDays(1), today.plusDays(7));
//
//        Assert.assertEquals(today.plusDays(1), slice.firstKey());
//        assertTrue(slice.containsKey(today.plusDays(2)));
//        Assert.assertEquals(today.plusDays(7), slice.lastKey());
//        Assert.assertEquals(3, slice.keySet().size());
//    }
}

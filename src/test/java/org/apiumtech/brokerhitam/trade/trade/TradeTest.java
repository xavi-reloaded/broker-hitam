package org.apiumtech.brokerhitam.trade.trade;

public class TradeTest extends AssetTest {

//    Trade trade;
//    Trade openTrade;
//    Trade multipleShareTrade;
//
//    TreeMap<DateTime, BigDecimal> toyPrices;
//
//    @BeforeMethod
//    public void setUp() throws Exception {
//
//        today = new DateTime();
//        yesterday = today.minusDays(1);
//        twoDaysAgo = today.minusDays(2);
//
//        toyPrices = new TreeMap<DateTime, BigDecimal>();
//        toyPrices.put(today.minusDays(5), new BigDecimal(10));
//        toyPrices.put(today.minusDays(4), new BigDecimal(15));
//        toyPrices.put(today.minusDays(3), new BigDecimal(20));
//        toyPrices.put(today.minusDays(2), new BigDecimal(10));
//        toyPrices.put(today.minusDays(1), new BigDecimal(5));
//        toyPrices.put(today, new BigDecimal(10));
//
//        series = new TimeSeries(toyPrices);
//        asset = new Asset("FOO", series);        trade = new Trade(asset, yesterday, today);
//        openTrade = new Trade(asset, yesterday);
//        multipleShareTrade = new Trade(asset, twoDaysAgo, yesterday, 500, TradeType.LONG, new Conditions(new BigDecimal(10), BigDecimal.ZERO));
//    }
//
//    public void testDontAllowIncorrectDates() {
//        boolean threw = false;
//
//        try {
//            new Trade(asset, today, yesterday);
//        } catch (Exception ex) {
//            threw = true;
//        }
//
//        Assert.assertTrue(threw);
//    }
//    @Test
//    public void testOpenClosed() {
//        Assert.assertTrue(trade.isOpen());
//        Assert.assertTrue(trade.isClosed());
//
//        Assert.assertTrue(openTrade.isOpen());
//        Assert.assertFalse(openTrade.isClosed());
//    }
//    @Test
//    public void testCantPlaceTradeOnNonexistentData() {
//        boolean threw = false;
//
//        try {
//            new Trade(asset, today.minusDays(3), yesterday);
//        } catch (Exception ex) {
//            threw = true;
//        }
//
//        Assert.assertTrue(threw);
//    }
//    @Test
//    public void testComputingTradePurchasePrice() throws Exception {
//        trade = new Trade(asset, yesterday, null, 2, TradeType.LONG, Conditions.getZero());
//        Assert.assertEquals(new BigDecimal(22), trade.getPurchasePrice());
//
//        trade = new Trade(asset, yesterday, null, 2, TradeType.LONG, new Conditions(new BigDecimal(2), BigDecimal.ZERO));
//        Assert.assertEquals(new BigDecimal(24), trade.getPurchasePrice());
//    }
//    @Test
//    public void testComputingTradeSellPrice() throws Exception {
//        trade = new Trade(asset, yesterday, today, 2, TradeType.LONG, Conditions.getZero());
//
//        Assert.assertEquals(new BigDecimal(18), trade.getSellPrice());
//
//        // We are assuming that there is an entry fee and no exit fee
//
//        trade = new Trade(asset, yesterday, today, 2, TradeType.LONG, new Conditions(new BigDecimal(2), BigDecimal.ZERO));
//
//        Assert.assertEquals(new BigDecimal(18), trade.getSellPrice());
//    }
//    @Test
//    public void testCantCalculateProfitLossOnOpenTrade() {
//        boolean threw = false;
//
//        try {
//            openTrade.profit();
//        } catch (Exception ex) {
//            threw = true;
//        }
//
//        Assert.assertTrue(threw);
//
//    }
//
//    // TODO These tests currently assumed we are unleveraged. Do we want to change this?
//    // TODO These tests assume prices can move continuously. Share prices may be discrete.
//    // TODO These tests assume we always make market orders only.
//    @Test
//    public void testComputingProfitLossWithoutCommissionsOrSlippage() throws Exception {
//        Assert.assertEquals(trade.profit(), new BigDecimal(-2));
//        multipleShareTrade.setConditions(Conditions.getZero());
//        Assert.assertEquals(multipleShareTrade.profit(), new BigDecimal(500));
//
//        Trade losingMultipleShareTrade = new Trade(asset, yesterday, today, 500);
//        Assert.assertEquals(losingMultipleShareTrade.profit(), new BigDecimal(-1000));
//    }
//    @Test
//    public void testShortProfit() throws Exception {
//        Trade shortTrade = new Trade(asset, yesterday, today, 1, TradeType.SHORT, Conditions.getZero());
//        // TODO: make sure this is actually the result we should expect
//        Assert.assertEquals(shortTrade.profit(), new BigDecimal(2));
//    }
//    @Test
//    public void testComputingProfitLossWithCommissionsNoSlippage() throws Exception {
//        Assert.assertEquals(multipleShareTrade.profit(), new BigDecimal(490));
//    }
//    @Test
//    public void testProfitLossWithCommissionAndSlippage() throws Exception {
//        multipleShareTrade.setConditions(new Conditions(new BigDecimal(10), new BigDecimal(0.01)));
//        Assert.assertEquals(multipleShareTrade.profit().doubleValue(), 385.0, 0.0001);
//    }
}

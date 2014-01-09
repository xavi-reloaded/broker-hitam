package org.apiumtech.brokerhitam.trade.trade;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.TreeMap;

public class SessionTest {

    Session session;
    TreeMap<DateTime, BigDecimal> toyPrices;
    Account account;
    TimeSeries series;
    Asset asset;
    Trade winningTrade, losingTrade;
    private Conditions conditions;
    private DateTime today;

    private Trade trade;
    private Trade openTrade;
    private DateTime twoDaysAgo;
    private DateTime yesterday;

    @BeforeMethod
    public void setUp() throws Exception {

        today = new DateTime();
        yesterday = today.minusDays(1);
        twoDaysAgo = today.minusDays(2);

        toyPrices = new TreeMap<DateTime, BigDecimal>();
        toyPrices.put(today.minusDays(5), new BigDecimal(10));
        toyPrices.put(today.minusDays(4), new BigDecimal(15));
        toyPrices.put(today.minusDays(3), new BigDecimal(20));
        toyPrices.put(today.minusDays(2), new BigDecimal(10));
        toyPrices.put(today.minusDays(1), new BigDecimal(5));
        toyPrices.put(today, new BigDecimal(10));

        series = new TimeSeries(toyPrices);
        asset = new Asset("FOO", series);

        conditions = new Conditions(new BigDecimal(1), new BigDecimal(0));
        winningTrade = new Trade(asset, today.minusDays(5), today.minusDays(3), 1, TradeType.LONG, conditions); // 10 -> 20
        losingTrade = new Trade(asset, today.minusDays(3), today.minusDays(1), 1, TradeType.LONG, conditions); // 20 -> 5

        account = new Account(new BigDecimal(100), today.minusDays(6));

        session = new Session(account, conditions);

        session.addTrade(winningTrade);
        session.addTrade(losingTrade);

        trade = new Trade(asset, yesterday, today);
        openTrade = new Trade(asset, yesterday);
//        multipleShareTrade = new Trade(asset, twoDaysAgo, yesterday, 500, TradeType.LONG, new Conditions(new BigDecimal(10), BigDecimal.ZERO));

    }
    @Test
    public void testGettingLastTrade() {
        Assert.assertEquals(losingTrade, session.lastTrade());
    }
    @Test
    public void testInMarket() {
        Assert.assertTrue(session.inMarket(today.minusDays(4)));
        Assert.assertFalse(session.inMarket(today));
    }
    @Test
    public void testAddingTrades() throws Exception {
        Session session = new Session(new Account(new BigDecimal(100), trade.getOpen().minusDays(1)), new Conditions(new BigDecimal(0.01), new BigDecimal(0.005)));
        session.addTrade(trade);
        Assert.assertEquals(trade.profit(), session.grossProfit());
    }
    @Test
    public void testAllowsOpenTrades() throws Exception {
        session.addTrade(openTrade);
    }
    @Test
    public void testForbidDuplicateTrades() {
        boolean threw = false;

        try {
            session.addTrade(trade);
            session.addTrade(trade);
        } catch (Exception ex) {
            threw = true;
        }

        Assert.assertTrue(threw);
    }
    @Test
    public void testForbidOverlappingTrades() throws Exception {
        boolean threw = false;

        try {
            session = new Session(account, conditions);

            Trade trade1 = new Trade(asset, twoDaysAgo, null);
            Trade trade2 = new Trade(asset, yesterday, null);

            session.addTrade(trade1);
            session.addTrade(trade2);
        } catch (Exception ex) {
            threw = true;
        }

        Assert.assertTrue(threw);
    }


    @Test
    public void testWithdrawingAndDepositingWithOpenTrades() throws Exception {

        account = new Account(new BigDecimal(100), openTrade.getOpen().minusDays(1));
        session = new Session(account, conditions);

        BigDecimal accountBalanceBefore = account.getCurrentAmount();

        session.addTrade(openTrade);

        BigDecimal accountBalanceAfter = account.getCurrentAmount();

        Assert.assertTrue(accountBalanceAfter.compareTo(accountBalanceBefore) < 0);
        Assert.assertEquals(new BigDecimal(95), accountBalanceAfter);

        session.closeLastTrade(today);
        Assert.assertEquals(new BigDecimal(105), account.getCurrentAmount());
    }
    @Test
    public void testComputingProfitCurve() throws Exception {
        Assert.assertEquals(Arrays.asList(new BigDecimal[]{new BigDecimal(9), new BigDecimal(-16)}), session.getProfitCurve());
    }
    @Test
    public void testComputingSharpeRatio() throws Exception {
        Assert.assertEquals(-0.198, session.sharpeRatio(), 0.0001);
    }
}

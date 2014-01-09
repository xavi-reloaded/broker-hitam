package org.apiumtech.brokerhitam.trade.trade;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.TreeMap;

import static junit.framework.Assert.assertTrue;

public class RuleTest{

    TreeMap<DateTime, BigDecimal> toyPrices;
    Account account;
    TimeSeries series;
    Asset asset;
    DateTime today;
    Conditions conditions;
    MoneyManagementStrategy moneyManager;

    @BeforeMethod
    public void setUp() throws Exception {
        today = DateTime.now();

        toyPrices = new TreeMap<DateTime, BigDecimal>();
        toyPrices.put(today.minusDays(5), new BigDecimal(10));
        toyPrices.put(today.minusDays(4), new BigDecimal(15));
        toyPrices.put(today.minusDays(3), new BigDecimal(20));
        toyPrices.put(today.minusDays(2), new BigDecimal(10));
        toyPrices.put(today.minusDays(1), new BigDecimal(5));
        toyPrices.put(today, new BigDecimal(10));

        series = new TimeSeries(toyPrices);
        asset = new Asset("FOO", series);

        account = new Account(new BigDecimal(1000), today.minusDays(6));

        conditions = new Conditions(BigDecimal.ZERO, BigDecimal.ZERO);
        moneyManager = new FixedPercentageAllocationStrategy(0.2, asset);
    }
    @Test
    public void testDontEmitMultipleIdenticalSignals() throws Exception {
        Rule buyAndHold = new BuyAndHoldRule(account, asset, conditions);

        Assert.assertEquals(1, buyAndHold.generateSignals(today.minusDays(5), today.minusDays(3)).getTrades().size());
    }
    @Test
    public void quitsIfInsufficientFunds() throws Exception {
        account = new Account(new BigDecimal(1), today.minusDays(6));
        Rule buyAndHold = new BuyAndHoldRule(account, asset, conditions);

        Assert.assertEquals(0, buyAndHold.generateSignals(today.minusDays(5), today.minusDays(4)).getTrades().size());
    }
    @Test
    public void testSignalGenerationSize() throws Exception {
        Rule buySell = new BuySellNextDayRule(account, asset, conditions);
        Session session = buySell.generateSignals(today.minusDays(5), today.minusDays(2));
        Assert.assertEquals(2, session.getTrades().size());

        account = new Account(new BigDecimal(1000), today.minusDays(6));

        buySell = new BuySellNextDayRule(account, asset, conditions);

        Assert.assertEquals(2, buySell.generateSignals(today.minusDays(5), today.minusDays(1)).getTrades().size());
    }
    @Test
    public void testCloseLastTrade() throws Exception {
        Rule buySell = new BuySellNextDayRule(account, asset, conditions);
        Session signals = buySell.generateSignals(today.minusDays(5), today.minusDays(2));
        assertTrue(signals.lastTrade().isClosed());
        Assert.assertEquals(today.minusDays(2), signals.lastTrade().getClose());
    }
    @Test
    public void testNotEnteringMarket() throws Exception {
        Rule noTrades = new NeverEnterMarketRule(account, asset, conditions);
        Assert.assertEquals(0, noTrades.generateSignals(today.minusDays(5), today.minusDays(1)).getTrades().size());
    }
    @Test
    public void testOnlyShorting() throws Exception {
        Rule noTrades = new OnlyShortRule(account, asset, conditions);
        Session session = noTrades.generateSignals(today.minusDays(5), today.minusDays(1));
        Assert.assertEquals(1, session.getTrades().size());
    }

    private class BuySellNextDayRule extends Rule {

        public BuySellNextDayRule(Account account, Asset asset, Conditions conditions) {
            super(account, asset, conditions, moneyManager);
        }

        @Override
        public boolean buy(DateTime time, Session session) {
            int daysSinceSessionStart = new Period(asset.getTimeSeries().beginningOfSeries(), time).getDays();
            return daysSinceSessionStart % 2 == 0;
        }

        @Override
        public boolean sell(DateTime time, Session session) {
            return !buy(time, session);
        }
    }

    private class BuyAndHoldRule extends Rule {

        public BuyAndHoldRule(Account account, Asset asset, Conditions conditions) {
            super(account, asset, conditions, moneyManager);
        }

        @Override
        public boolean buy(DateTime time, Session session) {
            return true;
        }

        @Override
        public boolean sell(DateTime time, Session session) {
            return false;
        }
    }

    private class NeverEnterMarketRule extends Rule {

        public NeverEnterMarketRule(Account account, Asset asset, Conditions conditions) {
            super(account, asset, conditions, moneyManager);
        }

        @Override
        public boolean buy(DateTime time, Session session) {
            return false;
        }

        @Override
        public boolean sell(DateTime time, Session session) {
            return false;
        }
    }

    private class OnlyShortRule extends Rule {

        public OnlyShortRule(Account account, Asset asset, Conditions conditions) {
            super(account, asset, conditions, moneyManager);
        }

        @Override
        public boolean buy(DateTime time, Session session) {
            return false;
        }

        @Override
        public boolean sell(DateTime time, Session session) {
            return true;
        }
    }
}

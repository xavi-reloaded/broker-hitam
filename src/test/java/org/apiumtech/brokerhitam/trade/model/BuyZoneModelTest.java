package org.apiumtech.brokerhitam.trade.model;

import org.apiumtech.brokerhitam.trade.trade.*;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.TreeMap;

public class BuyZoneModelTest{

    TreeMap<DateTime, BigDecimal> toyPrices;
    Account account;
    TimeSeries series;
    Asset asset;
    DateTime today;
    Conditions conditions;
    MoneyManagementStrategy moneyManager;
    Session session;
    double buyTrigger;
    double sellTrigger;
    double stopLoss;
    BuyZoneModel instance;

    @BeforeMethod
    public void setUp() throws Exception {


        today = DateTime.now();

        toyPrices = new TreeMap<DateTime, BigDecimal>();
        toyPrices.put(today, new BigDecimal(10));
        toyPrices.put(today.plusMinutes(1), new BigDecimal(10.5));
        toyPrices.put(today.plusMinutes(2), new BigDecimal(11.3));
        toyPrices.put(today.plusMinutes(3), new BigDecimal(12));
        toyPrices.put(today.plusDays(1), new BigDecimal(10.1));
        toyPrices.put(today.plusDays(1).plusMinutes(1), new BigDecimal(9.5));
        series = new TimeSeries(toyPrices);
        asset = new Asset("FOO", series);

        account = new Account(new BigDecimal(1000), today.minusDays(6));

        conditions = new Conditions(BigDecimal.ZERO, BigDecimal.ZERO);
        moneyManager = new FixedPercentageAllocationStrategy(0.2, asset);
        session = new Session(account, conditions);

        buyTrigger = 0.25;
        sellTrigger = 1.0;
        stopLoss = 1.0;

        instance = new BuyZoneModel(account, asset, conditions,
                moneyManager, buyTrigger, sellTrigger, stopLoss);
    }

    @Test
    public void testBuy() throws Exception {
        Assert.assertEquals(instance.buy(today, session), false);
        Assert.assertEquals(instance.buy(today.plusMinutes(1), session), true);

        Assert.assertEquals(instance.buy(today.plusMinutes(2), session), true);
        session.addTrade(new Trade(asset, today.plusMinutes(1)));
        Assert.assertEquals(instance.buy(today.plusMinutes(2), session), false);

    }
    @Test
    public void testThresholdSell() throws Exception {
        Assert.assertEquals(instance.sell(today.plusMinutes(2), session), false);

        session.addTrade(new Trade(asset, today.plusMinutes(1)));

        Assert.assertEquals(instance.sell(today.plusMinutes(2), session), false);
        Assert.assertEquals(instance.sell(today.plusMinutes(3), session), true);
    }
    @Test
    public void testStopLossSell() throws Exception {
        toyPrices = new TreeMap<DateTime, BigDecimal>();
        toyPrices.put(today, new BigDecimal(10));
        toyPrices.put(today.plusMinutes(1), new BigDecimal(10.5));
        toyPrices.put(today.plusMinutes(2), new BigDecimal(10.26));
        toyPrices.put(today.plusMinutes(3), new BigDecimal(10.25));
        toyPrices.put(today.plusMinutes(4), new BigDecimal(11.5));
        series = new TimeSeries(toyPrices);
        asset = new Asset("FOO", series);

        account = new Account(new BigDecimal(1000), today.minusDays(6));

        conditions = new Conditions(BigDecimal.ZERO, BigDecimal.ZERO);
        moneyManager = new FixedPercentageAllocationStrategy(0.2, asset);
        session = new Session(account, conditions);

        buyTrigger = 0.4;
        sellTrigger = 1.0;
        stopLoss = 0.25;

        instance = new BuyZoneModel(account, asset, conditions,
                moneyManager, buyTrigger, sellTrigger, stopLoss);

        session.addTrade(new Trade(asset, today.plusMinutes(1)));

        Assert.assertEquals(instance.sell(today.plusMinutes(1), session), false);
        Assert.assertEquals(instance.sell(today.plusMinutes(2), session), false);
        Assert.assertEquals(instance.sell(today.plusMinutes(3), session), true);

    }
    @Test
    public void testEndOfDaySell() throws Exception {
        sellTrigger = 1337.0;
        instance = new BuyZoneModel(account, asset, conditions,
                moneyManager, buyTrigger, sellTrigger, stopLoss);

        session.addTrade(new Trade(asset, today.plusMinutes(1)));
        Assert.assertEquals(instance.sell(today.plusMinutes(3), session), true);
    }
}

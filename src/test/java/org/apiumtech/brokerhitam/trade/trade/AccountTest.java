package org.apiumtech.brokerhitam.trade.trade;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class AccountTest{

    Account account;

    @BeforeMethod
    public void setUp() throws Exception {
        account = new Account(new BigDecimal(100000), DateTime.now());
    }

    @Test
    public void testValue() {
        Assert.assertEquals(account.getCurrentAmount(), new BigDecimal(100000));
    }
    @Test
    public void testProfitLoss() throws Exception {
        account.profit(new BigDecimal(1000), DateTime.now().plusMillis(1));
        Assert.assertEquals(account.getCurrentAmount(), new BigDecimal(101000));

        account.lose(new BigDecimal(500), DateTime.now().plusMillis(2));
        Assert.assertEquals(account.getCurrentAmount(), new BigDecimal(100500));
    }
    @Test
    public void testWithdraw() throws Exception {
        account.withdraw(new BigDecimal(10000), DateTime.now().plusSeconds(1));
        Assert.assertEquals(account.getCurrentAmount(), new BigDecimal(90000));
    }
    @Test
    public void testOverdraw() {
        boolean threw = false;
        try {
            account.lose(new BigDecimal(100001), DateTime.now());
        } catch (Exception ex) {
            threw = true;
        }

        Assert.assertTrue(threw);
    }
    @Test
    public void testEquityCurve() throws Exception {
        account.profit(new BigDecimal(1), DateTime.now().plusSeconds(1));
        account.lose(new BigDecimal(1), DateTime.now().plusSeconds(2));
        final Object[] actual = account.getEquityCurve().values().toArray();
        final Object[] expected = Arrays.asList(new BigDecimal[]{new BigDecimal(100000),
            new BigDecimal(100001), new BigDecimal(100000)}).toArray();

        Assert.assertEquals(actual[0], expected[0]);
        Assert.assertEquals(actual[1], expected[1]);
        Assert.assertEquals(actual[2], expected[2]);
    }
}

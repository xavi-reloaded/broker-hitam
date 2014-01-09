package org.apiumtech.brokerhitam.trade.trade;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class MoneyManagementStrategyTest extends RuleTest {
    @Test
    public void testFixedPercentage() throws Exception {
        FixedPercentageAllocationStrategy strategy = new FixedPercentageAllocationStrategy(0.2, asset);
        Assert.assertEquals(20, strategy.sizePosition(account, today.minusDays(5)));

        account = new Account(new BigDecimal(9), today.minusDays(6));
        Assert.assertEquals(0, strategy.sizePosition(account, today.minusDays(5)));
    }
}

package org.apiumtech.brokerhitam.trade.trade;

import org.joda.time.DateTime;

public interface MoneyManagementStrategy {

    public int sizePosition(Account account, DateTime time);
}

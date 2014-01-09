package org.apiumtech.brokerhitam.trade.fetch;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class YahooQuoteFetcherTest{
    @Test
    public void testParseQuotes() throws Exception {
        String samplePath = "src/main/resources/sample_yahoo_response.csv";

        Scanner scan = new Scanner(new File(samplePath));
        scan.useDelimiter("\\Z");
        String sampleResponse = scan.next().replaceAll("\r\n", "\n");

        YahooQuoteFetcher fetcher = new YahooQuoteFetcher();

        List<Quote> quotes = fetcher.parseQuotes(sampleResponse, 60 * 60 * 24);

        // Date,Open,High,Low,Close,Volume,Adj Close
        // 2013-03-25,812.41,819.23,806.82,809.64,1712000,809.64

        Quote firstQuote = quotes.get(0);
        Assert.assertEquals(firstQuote.getOpen(), new BigDecimal("812.41"));
        Assert.assertEquals(firstQuote.getHigh(), new BigDecimal("819.23"));
        Assert.assertEquals(firstQuote.getLow(), new BigDecimal("806.82"));
        Assert.assertEquals(firstQuote.getClose(), new BigDecimal("809.64"));
        Assert.assertEquals(firstQuote.getVolume(), 1712000);
        Assert.assertEquals(firstQuote.getOpenDate(), DateTime.parse("2013-03-25"));

        Assert.assertEquals(quotes.size(), 11);
    }
}

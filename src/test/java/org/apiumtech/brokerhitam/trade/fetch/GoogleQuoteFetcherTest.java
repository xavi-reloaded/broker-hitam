package org.apiumtech.brokerhitam.trade.fetch;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class GoogleQuoteFetcherTest {
    @Test
    public void testParseQuotes() throws Exception {
        String samplePath = "src/main/resources/sample_google_response.csv";

        Scanner scan = new Scanner(new File(samplePath));
        scan.useDelimiter("\\Z");
        String sampleResponse = scan.next().replaceAll("\r\n", "\n");

        GoogleQuoteFetcher fetcher = new GoogleQuoteFetcher();

        List<Quote> quotes = fetcher.parseQuotes(sampleResponse, 60);

        Quote firstQuote = quotes.get(0);
        Assert.assertEquals(firstQuote.getOpen(), new BigDecimal("444.05"));
        Assert.assertEquals(firstQuote.getHigh(), new BigDecimal("444.19"));
        Assert.assertEquals(firstQuote.getLow(), new BigDecimal("443.8"));
        Assert.assertEquals(firstQuote.getClose(), new BigDecimal("443.8"));
        Assert.assertEquals(firstQuote.getVolume(), 78179);
        Assert.assertEquals(firstQuote.getOpenDate().getMillis(), 1362061800000L);

        Assert.assertEquals(quotes.size(), 5873);

    }
}

package com.bitoex.bitopro.java.client;

import com.bitoex.bitopro.java.client.BitoProPublicClient.BitoProPublicPairClient;
import com.bitoex.bitopro.java.model.Trade;
import com.bitoex.bitopro.java.model.Ticker;
import com.bitoex.bitopro.java.model.OrderBook;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class BitoProPublicClientTest {

  private static final Logger logger = LogManager.getLogger();
  private static final String pair = "btc_twd";
  private static BitoProPublicClient client;
  private static BitoProPublicPairClient pairClient;

  @BeforeClass
  public static void init() {
    client = BitoProClientBuilder.withDefaultClient().createPublic();
    pairClient = client.getPairClient(pair);
  }

  @Test
  public void testGetTrades() throws IOException {

    List<Trade> trades = pairClient.getTrades();
    //there are at least 10 currencies
    logger.info("trades: {}", trades);
  }

  @Test
  public void testGetTickers() throws IOException {

    List<Ticker> tickers = client.getTickers();
    logger.info("tickers: {}", tickers);
  }

  @Test
  public void testGetTicker() throws IOException {

    Ticker ticker = pairClient.getTicker();
    logger.info("ticker: {}", ticker);
  }

  @Test
  public void testOrderBook() throws IOException {

    OrderBook ob = pairClient.getOrderBook();
    logger.info("order book: {}", ob);
  }

}
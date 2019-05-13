package com.bitoex.bitopro.java.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.bitoex.bitopro.java.client.BitoProClient.BitoProPairClient;
import com.bitoex.bitopro.java.exception.BadArgumentException;
import com.bitoex.bitopro.java.model.Balance;
import com.bitoex.bitopro.java.model.Order;
import com.bitoex.bitopro.java.model.OrderAction;
import com.bitoex.bitopro.java.model.OrderResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class BitoProClientTest {

  private static final Logger logger = LogManager.getLogger();
  private static final String pair = "btc_twd";
  private static BitoProClient client;
  private static BitoProPairClient pairClient;

  @BeforeClass
  public static void init() {
    client = BitoProClientBuilder.withDefaultClient().createWithEnv();
    pairClient = client.getPairClient(pair);
  }

  @Ignore
  @Test
  public void testGetBalance() throws IOException {

    List<Balance> balances = client.getAccountBalances();

    // there are at least 10 currencies
    logger.info("balances: {}", balances);
    assertTrue(balances.size() > 10);

  }

  @Ignore
  @Test(expected = BadArgumentException.class)
  public void testCreateLimitOrderWithLowAmount() throws IOException {
    BigDecimal price = new BigDecimal("1");
    BigDecimal amount = new BigDecimal("0.0005");
    pairClient.createLimitOrder(OrderAction.BUY, price, amount);
  }

  @Ignore
  @Test
  public void testGetOrder() throws IOException {
    Optional<Order> order = pairClient.getOrder("xxx");

    assertFalse(order.isPresent());
    
  }

  @Ignore
  @Test
  public void testCancelNonexsitingOrder() throws IOException {
    Optional<OrderResponse> r = pairClient.cancelOrder("123");
    assertFalse(r.isPresent());
    
  }

  @Ignore
  @Test
  public void testMarketOrder() throws IOException {
    BigDecimal totalAmount = new BigDecimal("1");
    OrderResponse or = pairClient.createMarketOrder(OrderAction.BUY, totalAmount);

    logger.info("market order response: {}", or);
  }
}
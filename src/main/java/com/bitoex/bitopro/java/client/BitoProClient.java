package com.bitoex.bitopro.java.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.bitoex.bitopro.java.model.Balance;
import com.bitoex.bitopro.java.model.Order;
import com.bitoex.bitopro.java.model.OrderAction;
import com.bitoex.bitopro.java.model.OrderResponse;
import com.bitoex.bitopro.java.model.PaginatedList;

/**
 * Client for BitoPro API. Provides direct methods to access BitoPro API.
 * Default implementation can be created via {@link BitoProClientBuilder}. 
 * All methods throw {@link com.bitoex.bitopro.java.exception.ApiException} when
 * server returns error. All parameter related errors will be thrown with
 * {@link com.bitoex.bitopro.java.exception.BadArgumentException}.
 * 
 * Pair is in terms of base and quote and combined in the form of @{code ${base}_${quote}}.
 */
public interface BitoProClient {

  /**
   * Get account's balances.
   * 
   * @return list of balances for each symbol
   * @throws IOException when connection error occured while called the Rest API
   */
  List<Balance> getAccountBalances() throws IOException;

  /**
   * Place a market order. If action is BUY, totalAmount is in terms of quote. If
   * action is SELL, totalAmount is in terms of base.
   *
   * @param pair        the pair to place order
   * @param action      {@link OrderAction} for the order, either BUY or SELL
   * @param totalAmount total amount for the order
   * @return {@link OrderResponse} with result of the order placed
   * @throws IOException when connection error occured while called the Rest API
   */
  OrderResponse createMarketOrder(String pair, OrderAction action, BigDecimal totalAmount) throws IOException;

  /**
   * Place a limit price order.
   * 
   * @param pair   the pair to place order
   * @param action {@link OrderAction} for the order, either BUY or SELL
   * @param price  price in terms of quote for the order
   * @param amount amount in terms of quote for the order
   * @return {@link OrderResponse} with result of the order placed
   * @throws IOException when connection error occured while called the Rest API
   */
  OrderResponse createLimitOrder(String pair, OrderAction action, BigDecimal price, BigDecimal amount)
      throws IOException;

  /**
   * Cancel an order. If order id is not found, a
   * {@code com.bitoex.bitopro.java.exception.BadArgumentException} is thrown.
   * 
   * @param pair    the pair to cancel order
   * @param orderId id of the order to be cancelled
   * @return {@link OrderResponse} with status of the order cancelled, if present
   * @throws IOException when connection error occured while called the Rest API
   */
  Optional<OrderResponse> cancelOrder(String pair, String orderId) throws IOException;

  /**
   * Get the completed or cancelled orders.
   * 
   * @return list of completed or cancelled orders
   * @throws IOException when connection error occured while called the Rest API
   * @see Order
   */
  List<Order> getOrderHistory() throws IOException;

  /**
   * Get orders for specified pair in paginated form.
   * 
   * @param pair   the pair to get orders
   * @param active whether to get active orders or not. Default: false
   * @param page   page number for the order list. Start from 1.
   * @return the paginated list of orders
   * @throws IOException when connection error occured while called the Rest API
   * @see Order
   */
  PaginatedList<Order> getOrders(String pair, boolean active, int page) throws IOException;

  /**
   * Get order specified by pair and id.
   * 
   * @param pair    the pair to get order
   * @param orderId id of the order
   * @return specified order
   * @throws IOException when connection error occured while called the Rest API
   */
  Optional<Order> getOrder(String pair, String orderId) throws IOException;

  /**
   * Create convenient methods for specified pair. Save users from passing the
   * same pair parameters.
   * 
   * @param pair the pair to be acted on
   * @return a {@code BitoProPairClient} for specified pair
   */
  default BitoProPairClient getPairClient(String pair) {
    return new BitoProPairClient(this, pair);
  }

  /**
   * A pair specific BitoProClient.
   */
  class BitoProPairClient {

    private final BitoProClient client;
    private final String pair;

    BitoProPairClient(BitoProClient client, String pair) {
      this.client = client;
      this.pair = pair;
    }

    /**
     * Place a market order. If action is BUY, totalAmount is in terms of quote. If
     * action is SELL, totalAmount is in terms of base.
     * 
     * @param action      {@link OrderAction} for the order, either BUY or SELL
     * @param totalAmount total amount for the order
     * @return {@link OrderResponse} with result of the order placed
     * @throws IOException when connection error occured while called the Rest API
     * @see BitoProClient#createMarketOrder(String, OrderAction, BigDecimal)
     */
    public OrderResponse createMarketOrder(OrderAction action, BigDecimal totalAmount) throws IOException {
      return client.createMarketOrder(pair, action, totalAmount);
    }

    /**
     * Place a limit price order.
     *
     * @param action {@link OrderAction} for the order, either BUY or SELL
     * @param price  price in terms of quote for the order
     * @param amount amount in terms of quote for the order
     * @return {@link OrderResponse} with result of the order placed
     * @throws IOException when connection error occured while called the Rest API
     * @see BitoProClient#createLimitOrder(String, OrderAction, BigDecimal,
     *      BigDecimal)
     */
    public OrderResponse createLimitOrder(OrderAction action, BigDecimal price, BigDecimal amount) throws IOException {
      return client.createLimitOrder(pair, action, price, amount);
    }

    /**
     * Cancel an order. If order id is not found, a
     * {@code com.bitoex.bitopro.java.exception.BadArgumentException} is thrown.
     * 
     * @param orderId id of the order to be cancelled
     * @return {@link OrderResponse} with status of the order cancelled, if present
     * @throws IOException when connection error occured while called the Rest API
     * @see BitoProClient#cancelOrder(String, String)
     */
    public Optional<OrderResponse> cancelOrder(String orderId) throws IOException {
      return client.cancelOrder(pair, orderId);
    }

    /**
     * Get orders for specified pair in paginated form.
     * 
     * @param active whether to get active orders or not. Default: false
     * @param page   page number for the order list. Default: 1
     * @return the paginated list of orders
     * @throws IOException when connection error occured while called the Rest API
     * @see Order
     * @see BitoProClient#getOrders(String, boolean, int)
     */
    public PaginatedList<Order> getOrders(boolean active, int page) throws IOException {
      return client.getOrders(pair, active, page);
    }

    /**
     * Get order specified by pair and id.
     * 
     * @param orderId id of the order
     * @return specified order
     * @throws IOException when connection error occured while called the Rest API
     * @see Order
     * @see BitoProClient#getOrder(String, String)
     */
    public Optional<Order> getOrder(String orderId) throws IOException {
      return client.getOrder(pair, orderId);
    }

  }

}

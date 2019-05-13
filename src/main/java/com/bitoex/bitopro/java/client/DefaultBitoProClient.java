package com.bitoex.bitopro.java.client;

import static com.bitoex.bitopro.java.util.BitoProUtils.validatePair;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import com.bitoex.bitopro.java.exception.BadArgumentException;
import com.bitoex.bitopro.java.model.Balance;
import com.bitoex.bitopro.java.model.Order;
import com.bitoex.bitopro.java.model.OrderAction;
import com.bitoex.bitopro.java.model.OrderRequest;
import com.bitoex.bitopro.java.model.OrderResponse;
import com.bitoex.bitopro.java.model.OrderType;
import com.bitoex.bitopro.java.model.PaginatedList;
import com.bitoex.bitopro.java.model.ResponseWrapper;
import com.bitoex.bitopro.java.signature.Signature;
import com.bitoex.bitopro.java.util.BitoProUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation for {@link BitoProClient} using HttpClient.
 */
public class DefaultBitoProClient extends AbstractBitoProClient implements BitoProClient {

    private static final Logger log = LoggerFactory.getLogger(AbstractBitoProClient.class);
    private final Header apiKeyHeader;
    private final Signature signature;

    public DefaultBitoProClient(String apiKey, String secret, String email, CloseableHttpClient client) {
        super(client);

        this.apiKeyHeader = new BasicHeader("X-BITOPRO-APIKEY", apiKey);
        this.signature = new Signature(email, secret);
    }

    public DefaultBitoProClient(String apiKey, String secret, String email) {
        this(apiKey, secret, email, BitoProUtils.createDefaultClient());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Balance> getAccountBalances() throws IOException {
        ResponseWrapper<List<Balance>> wrapper = executeRequest("/accounts/balance", HttpGet.METHOD_NAME,
                Optional.empty(), new TypeReference<ResponseWrapper<List<Balance>>>() {});
        return wrapper.getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderResponse createMarketOrder(String pair, OrderAction action, BigDecimal totalAmount) throws IOException {

        validatePair(pair);
        OrderRequest or = new OrderRequest();

        or.setType(OrderType.MARKET);
        or.setAction(action);
        or.setPrice(BigDecimal.ONE);//TODO will be removed in future release
        or.setAmount(totalAmount);
        or.setTimestamp(System.currentTimeMillis());
        return executeRequest("/orders/" + pair, HttpPost.METHOD_NAME, Optional.of(or), OrderResponse.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderResponse createLimitOrder(String pair, OrderAction action, BigDecimal price, BigDecimal amount) throws IOException {

        validatePair(pair);
        OrderRequest or = new OrderRequest();
        or.setType(OrderType.LIMIT);
        or.setAction(action);
        or.setPrice(price);
        or.setAmount(amount);
        or.setTimestamp(System.currentTimeMillis());
        return executeRequest("/orders/" + pair, HttpPost.METHOD_NAME, Optional.of(or), OrderResponse.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderResponse> cancelOrder(String pair, String orderId) throws IOException {
        try {
            return Optional.of(executeRequest("/orders/" + pair + "/" + orderId, HttpDelete.METHOD_NAME, Optional.empty(), OrderResponse.class));
        } catch(BadArgumentException e) {
            log.debug("failed to cancel order: ", orderId, e);
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getOrderHistory() throws IOException {
        ResponseWrapper<List<Order>> wrapper = executeRequest("/orders/history", HttpGet.METHOD_NAME, Optional.empty(),
                new TypeReference<ResponseWrapper<List<Order>>>() {});
        return wrapper.getData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedList<Order> getOrders(String pair, boolean active, int page) throws IOException {
        validatePair(pair);
        PaginatedList<Order> list = executeRequest("/orders/" + pair + "?active=" + active + "&page=" + page,
                HttpGet.METHOD_NAME, Optional.empty(), new TypeReference<PaginatedList<Order>>() {});
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Order> getOrder(String pair, String id) throws IOException {
        validatePair(pair);
        Order order = executeRequest("orders/" + pair + "/" + id, HttpGet.METHOD_NAME, Optional.empty(), Order.class);
        if (StringUtils.isBlank(order.getId())) {
            return Optional.empty();
        } else {
            return Optional.of(order);
        }
    }

    private <T> T executeRequest(String path, String method, Optional<Object> body, Class<T> clazz) throws IOException {
        try (CloseableHttpResponse resp = execute(path, method, body)) {
            checkStatus(resp.getStatusLine(), resp.getEntity());
            return om.readValue(resp.getEntity().getContent(), clazz);
        }
    }

    private <T> T executeRequest(String path, String method, Optional<Object> body, TypeReference<T> typeReference) throws IOException {
        try (CloseableHttpResponse resp = execute(path, method, body)) {
            checkStatus(resp.getStatusLine(), resp.getEntity());
            return om.readValue(resp.getEntity().getContent(), typeReference);
        }
    }

    private CloseableHttpResponse execute(String path, String method, Optional<Object> body) throws IOException {

        Signature.BitoProPayload payload;

        RequestBuilder builder = RequestBuilder.create(method).setUri(getUrl(path));
        String json = "";
        if (body.isPresent()) {
            json = om.writeValueAsString(body.get());
            payload = signature.signJsonBody(json);
            HttpEntity jsonEntity = new StringEntity(json, StandardCharsets.UTF_8);
            builder.setEntity(jsonEntity);
        } else {
            payload = signature.signDefaultRequest(System.currentTimeMillis());
        }

        builder
            .addHeader(apiKeyHeader)
            .addHeader("X-BITOPRO-PAYLOAD", payload.getPayload())
            .addHeader("X-BITOPRO-SIGNATURE", payload.getSignature())
            .addHeader(CLIENT_HEADER);
        log.debug("url: {}, method: {}, signature: {}, body: {}, payload: {}", path, method, payload.getSignature(), json, payload.getPayload());

        return client.execute(builder.build());
    }
}

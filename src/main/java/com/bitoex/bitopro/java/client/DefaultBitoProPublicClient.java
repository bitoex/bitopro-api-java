package com.bitoex.bitopro.java.client;

import static com.bitoex.bitopro.java.util.BitoProUtils.validatePair;

import java.io.IOException;
import java.util.List;

import com.bitoex.bitopro.java.model.OrderBook;
import com.bitoex.bitopro.java.model.ResponseWrapper;
import com.bitoex.bitopro.java.model.Ticker;
import com.bitoex.bitopro.java.model.Trade;
import com.bitoex.bitopro.java.util.BitoProUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Default implementation for {@link BitoProPublicClient} using HttpClient.
 */
public class DefaultBitoProPublicClient extends AbstractBitoProClient implements BitoProPublicClient {

    public DefaultBitoProPublicClient(CloseableHttpClient client) {
        super(client);
    }

    public DefaultBitoProPublicClient() {
        this(BitoProUtils.createDefaultClient());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ticker getTicker(String pair) throws IOException {

        validatePair(pair);
        String url = getUrl("tickers/" + pair);
        HttpGet get = createGet(url);

        try (CloseableHttpResponse resp = client.execute(get)) {

            checkStatus(resp.getStatusLine(), resp.getEntity());
            ResponseWrapper<Ticker> wrap = om.readValue(resp.getEntity().getContent(),
                    new TypeReference<ResponseWrapper<Ticker>>() {});
            return wrap.getData();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticker> getTickers() throws IOException {

        String url = getUrl("tickers");
        HttpGet get = createGet(url);

        try (CloseableHttpResponse resp = client.execute(get)) {

            checkStatus(resp.getStatusLine(), resp.getEntity());
            ResponseWrapper<List<Ticker>> wrap = om.readValue(resp.getEntity().getContent(),
                    new TypeReference<ResponseWrapper<List<Ticker>>>() {});
            return wrap.getData();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderBook getOrderBook(String pair) throws IOException {

        validatePair(pair);
        String url = getUrl("order-book/" + pair);
        HttpGet get = createGet(url);

        try (CloseableHttpResponse resp = client.execute(get)) {

            checkStatus(resp.getStatusLine(), resp.getEntity());
            return om.readValue(resp.getEntity().getContent(), OrderBook.class);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Trade> getTrades(String pair) throws IOException {

        validatePair(pair);
        String url = getUrl("trades/" + pair);
        HttpGet get = createGet(url);

        try (CloseableHttpResponse resp = client.execute(get)) {

            checkStatus(resp.getStatusLine(), resp.getEntity());
            ResponseWrapper<List<Trade>> wrap = om.readValue(resp.getEntity().getContent(),
                    new TypeReference<ResponseWrapper<List<Trade>>>() {});
            return wrap.getData();
        }
    }

    private HttpGet createGet(String url) {
        HttpGet get = new HttpGet(url);
        get.addHeader(CLIENT_HEADER);
        return get;
    }
}

package com.bitoex.bitopro.java.client;

import java.util.Properties;

import com.bitoex.bitopro.java.util.BitoProUtils;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * A builder for creating {@code BitoProClient} and {@code BitoProPublicClient}.
 */
public final class BitoProClientBuilder {

    private CloseableHttpClient client;

    private BitoProClientBuilder() {}

    /**
     * Create a BitoProClientBuilder with provided client.
     * @param client {@link CloseableHttpClient} to be used with BitoProClient/BitoProPublicClient
     * @return a new BitoProClientBuilder
     */
    public static BitoProClientBuilder withClient(CloseableHttpClient client) {
        BitoProClientBuilder builder = new BitoProClientBuilder();
        builder.client = client;
        return builder;
    }

    /**
     * Create a BitoProClientBuilder with default client.
     * @return a new BitoProClientBuilder with default HttpClient
     */
    public static BitoProClientBuilder withDefaultClient() {
        BitoProClientBuilder builder = new BitoProClientBuilder();
        builder.client = BitoProUtils.createDefaultClient();
        return builder;
    }

    /**
     * Create BitoProClient with key, secret, email from ENV using "BITO_PRO_KEY", "BITO_PRO_SECRET" and "BITO_PRO_EMAIL" as keys respectively.
     * @return a new BitoProClient
     */
    public BitoProClient createWithEnv() {
        String key = System.getenv("BITO_PRO_KEY");
        String secret = System.getenv("BITO_PRO_SECRET");
        String email = System.getenv("BITO_PRO_EMAIL");
        return new DefaultBitoProClient(key, secret, email, client);
    }

    /**
     * Create BitoProClient with key, secret, email from provided Properties using "bitopro.key", "bitopro.secret" and "bitopro.email" as keys respectively.
     * @param properties a properties with specified key values
     * @return a new BitoProClient
     */
    public BitoProClient createWithProperties(Properties properties) {
        String key = properties.getProperty("bitopro.key");
        String secret = properties.getProperty("bitopro.secret");
        String email = properties.getProperty("bitopro.email");
        return new DefaultBitoProClient(key, secret, email, client);
    }

    /**
     * Create BitoProClient with key, secret, email from Java system properties using "BITO_PRO_KEY", "BITO_PRO_SECRET" and "BITO_PRO_EMAIL" as keys respectively.
     * @return a new BitoProClient
     */
    public BitoProClient createWithSystemProperties() {
        String key = System.getProperty("BITO_PRO_KEY");
        String secret = System.getProperty("BITO_PRO_SECRET");
        String email = System.getProperty("BITO_PRO_EMAIL");
        return new DefaultBitoProClient(key, secret, email, client);
    }

    /**
     * Create BitoProClient with provided api key, secret and email.
     * @param apiKey BitoPro API key
     * @param secret BitoPro API secret
     * @param email BitoPro email (account)
     * @return a new BitoProClient
     */
    public BitoProClient create(String apiKey, String secret, String email) {
        return new DefaultBitoProClient(apiKey, secret, email, client);
    }

    /**
     * Create BitoProPublicClient.
     * @return a new BitoProPublicClient
     */
    public BitoProPublicClient createPublic() {
        return new DefaultBitoProPublicClient(client);
    }
}

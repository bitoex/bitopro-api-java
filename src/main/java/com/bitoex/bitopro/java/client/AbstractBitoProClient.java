package com.bitoex.bitopro.java.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import com.bitoex.bitopro.java.exception.ApiException;
import com.bitoex.bitopro.java.exception.BadArgumentException;
import com.bitoex.bitopro.java.exception.BadGatewayException;
import com.bitoex.bitopro.java.exception.DoubleSendException;
import com.bitoex.bitopro.java.exception.RateLimitExceedException;
import com.bitoex.bitopro.java.exception.ServiceUnavailableException;
import com.bitoex.bitopro.java.exception.UnauthorizedApiKeyException;
import com.bitoex.bitopro.java.model.Error;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractBitoProClient {

    private static final Logger log = LoggerFactory.getLogger(AbstractBitoProClient.class);
    private static final String URL_BASE = "https://api.bitopro.com/v2/";

    protected final CloseableHttpClient client;
    protected final ObjectMapper om;

    protected static final Header CLIENT_HEADER = new BasicHeader("X-BITOPRO-API", "java");

    AbstractBitoProClient(CloseableHttpClient client) {
        this.client = client;

        ObjectMapper om = new ObjectMapper();
        om.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, JsonGenerator.Feature.IGNORE_UNKNOWN);
        om.enable(MapperFeature.AUTO_DETECT_CREATORS, MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        om.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));

        SimpleModule module = new SimpleModule();
        module.addSerializer(BigDecimal.class, new BigDecimalSerializer());
        om.registerModule(module);

        this.om = om;
    }

    String getUrl(String path) {
        return URL_BASE + path;
    }

    protected void checkStatus(StatusLine sl, HttpEntity entity) throws IOException {
        if (sl.getStatusCode() != 200) {
            String body = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);

            body = tryParseError(body);
            switch (sl.getStatusCode()) {
                case 401:
                    throw new UnauthorizedApiKeyException(sl.getReasonPhrase(), body);
                case 409:
                    throw new DoubleSendException(sl.getReasonPhrase(), body);
                case 422:
                    throw new BadArgumentException(sl.getReasonPhrase(), body);
                case 429:
                    throw new RateLimitExceedException(sl.getReasonPhrase(), body);
                case 520:
                    throw new BadGatewayException(sl.getReasonPhrase(), body);
                case 503:
                    throw new ServiceUnavailableException(sl.getReasonPhrase(), body);
                default:
                    throw new ApiException(sl.getStatusCode(), sl.getReasonPhrase(), body);
            }
        }
    }

    protected String tryParseError(String body) {
        try {
            Error error = om.readValue(body, Error.class);
            return error.getError();
        }
        catch(IOException e) {
            log.debug("failed to parse error body to Error", e);
            return body;
        }
    }

    private static class BigDecimalSerializer extends StdSerializer<BigDecimal> {

        private static final long serialVersionUID = -3765416947238311264L;

        public BigDecimalSerializer() {
            this(null);
        }

        public BigDecimalSerializer(Class<BigDecimal> t) {
            super(t);
        }
        @Override
        public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(value.toPlainString());
        }
    }
}

package com.bitoex.bitopro.java.signature;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A tool for preparing payload and signed signature for a account.
 */
public final class Signature {

    private final HmacUtils hmac;
    private final String email;

    /**
     * Constructor.
     * @param email email of the account
     * @param secret secret of the API key for the account
     */
    public Signature(String email, String secret) {

        this.hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_384, secret);
        this.email = email;
    }

    /**
     * Turn Json body to specified payload format.
     * @param json json data to be sent
     * @return formatted payload
     */
    public static String jsonToPayload(String json) {
        return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Sign the payload.
     * @param payload payload to be signed
     * @return the signature produced by signing the payload
     */
    public String signPayload(String payload) {
        return Hex.encodeHexString(hmac.hmac(payload));
    }

    /**
     * Provide payload and signature with specified Json data.
     * @param json json data to be signed
     * @return {@link BitoProPayload}
     */
    public BitoProPayload signJsonBody(String json) {
        String payload = jsonToPayload(json);
        String signature = signPayload(payload);
        return new BitoProPayload(payload, signature);
    }

    /**
     * Provide payload and signature using default data.
     * 
     * Mainly used for GET and DELETE requests.
     * @param timestamp unix timestamp with milliseconds of current time
     * @return {@link BitoProPayload}
     */
    public BitoProPayload signDefaultRequest(long timestamp) {

        String payload = "{\"identity\":\"" + email + "\",\"nonce\":" + timestamp + "}";
        String base64 = jsonToPayload(payload);
        String sig = signPayload(base64);
        return new BitoProPayload(base64, sig);
    }

    /**
     * Data class containing payload and signature.
     */
    public static class BitoProPayload {

        private final String payload;
        private final String signature;

        private BitoProPayload(String payload, String signature) {
            this.payload = payload;
            this.signature = signature;
        }

        public String getPayload() {
            return payload;
        }

        public String getSignature() {
            return signature;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

}

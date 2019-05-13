package com.bitoex.bitopro.java.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public final class BitoProUtils {

  private BitoProUtils() {
  }

  public static CloseableHttpClient createDefaultClient() {
    return HttpClientBuilder.create().build();
  }

  public static String getPair(String base, String quote) {
    return (base + "_" + quote).toLowerCase();
  }

  public static void validatePair(String pair) {
    if (StringUtils.isBlank(pair) || !StringUtils.contains(pair, "_")) {
      throw new IllegalArgumentException("pair cannot be blank and is in form of ${base}_${quote}");
    }
  }

}
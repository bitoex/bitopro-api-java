package com.bitoex.bitopro.java.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Error {

  private final String error;

  @JsonCreator
  public Error(String error) {
    this.error = error;
  }

  public String getError() {
    return error;
  }
}
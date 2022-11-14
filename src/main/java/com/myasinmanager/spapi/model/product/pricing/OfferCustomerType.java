/*
 * Selling Partner API for Pricing
 * The Selling Partner API for Pricing helps you programmatically retrieve product pricing and offer information for Amazon Marketplace products.
 *
 * OpenAPI spec version: v0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.myasinmanager.spapi.model.product.pricing;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Gets or Sets OfferCustomerType
 */
@JsonAdapter(OfferCustomerType.Adapter.class)
public enum OfferCustomerType {
  
  B2C("B2C"),
  
  B2B("B2B");

  private String value;

  OfferCustomerType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static OfferCustomerType fromValue(String text) {
    for (OfferCustomerType b : OfferCustomerType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }

  public static class Adapter extends TypeAdapter<OfferCustomerType> {
    @Override
    public void write(final JsonWriter jsonWriter, final OfferCustomerType enumeration) throws IOException {
      jsonWriter.value(enumeration.getValue());
    }

    @Override
    public OfferCustomerType read(final JsonReader jsonReader) throws IOException {
      String value = jsonReader.nextString();
      return OfferCustomerType.fromValue(String.valueOf(value));
    }
  }
}


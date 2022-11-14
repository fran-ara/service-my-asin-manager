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
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * The time range in which an item will likely be shipped once an order has been placed.
 */
@ApiModel(description = "The time range in which an item will likely be shipped once an order has been placed.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2022-11-02T11:15:55.180-06:00")
public class DetailedShippingTimeType {
  @SerializedName("minimumHours")
  private Long minimumHours = null;

  @SerializedName("maximumHours")
  private Long maximumHours = null;

  @SerializedName("availableDate")
  private String availableDate = null;

  /**
   * Indicates whether the item is available for shipping now, or on a known or an unknown date in the future. If known, the availableDate property indicates the date that the item will be available for shipping. Possible values: NOW, FUTURE_WITHOUT_DATE, FUTURE_WITH_DATE.
   */
  @JsonAdapter(AvailabilityTypeEnum.Adapter.class)
  public enum AvailabilityTypeEnum {
    NOW("NOW"),
    
    FUTURE_WITHOUT_DATE("FUTURE_WITHOUT_DATE"),
    
    FUTURE_WITH_DATE("FUTURE_WITH_DATE");

    private String value;

    AvailabilityTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AvailabilityTypeEnum fromValue(String text) {
      for (AvailabilityTypeEnum b : AvailabilityTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<AvailabilityTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AvailabilityTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AvailabilityTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return AvailabilityTypeEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("availabilityType")
  private AvailabilityTypeEnum availabilityType = null;

  public DetailedShippingTimeType minimumHours(Long minimumHours) {
    this.minimumHours = minimumHours;
    return this;
  }

   /**
   * The minimum time, in hours, that the item will likely be shipped after the order has been placed.
   * @return minimumHours
  **/
  @ApiModelProperty(value = "The minimum time, in hours, that the item will likely be shipped after the order has been placed.")
  public Long getMinimumHours() {
    return minimumHours;
  }

  public void setMinimumHours(Long minimumHours) {
    this.minimumHours = minimumHours;
  }

  public DetailedShippingTimeType maximumHours(Long maximumHours) {
    this.maximumHours = maximumHours;
    return this;
  }

   /**
   * The maximum time, in hours, that the item will likely be shipped after the order has been placed.
   * @return maximumHours
  **/
  @ApiModelProperty(value = "The maximum time, in hours, that the item will likely be shipped after the order has been placed.")
  public Long getMaximumHours() {
    return maximumHours;
  }

  public void setMaximumHours(Long maximumHours) {
    this.maximumHours = maximumHours;
  }

  public DetailedShippingTimeType availableDate(String availableDate) {
    this.availableDate = availableDate;
    return this;
  }

   /**
   * The date when the item will be available for shipping. Only displayed for items that are not currently available for shipping.
   * @return availableDate
  **/
  @ApiModelProperty(value = "The date when the item will be available for shipping. Only displayed for items that are not currently available for shipping.")
  public String getAvailableDate() {
    return availableDate;
  }

  public void setAvailableDate(String availableDate) {
    this.availableDate = availableDate;
  }

  public DetailedShippingTimeType availabilityType(AvailabilityTypeEnum availabilityType) {
    this.availabilityType = availabilityType;
    return this;
  }

   /**
   * Indicates whether the item is available for shipping now, or on a known or an unknown date in the future. If known, the availableDate property indicates the date that the item will be available for shipping. Possible values: NOW, FUTURE_WITHOUT_DATE, FUTURE_WITH_DATE.
   * @return availabilityType
  **/
  @ApiModelProperty(value = "Indicates whether the item is available for shipping now, or on a known or an unknown date in the future. If known, the availableDate property indicates the date that the item will be available for shipping. Possible values: NOW, FUTURE_WITHOUT_DATE, FUTURE_WITH_DATE.")
  public AvailabilityTypeEnum getAvailabilityType() {
    return availabilityType;
  }

  public void setAvailabilityType(AvailabilityTypeEnum availabilityType) {
    this.availabilityType = availabilityType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DetailedShippingTimeType detailedShippingTimeType = (DetailedShippingTimeType) o;
    return Objects.equals(this.minimumHours, detailedShippingTimeType.minimumHours) &&
        Objects.equals(this.maximumHours, detailedShippingTimeType.maximumHours) &&
        Objects.equals(this.availableDate, detailedShippingTimeType.availableDate) &&
        Objects.equals(this.availabilityType, detailedShippingTimeType.availabilityType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minimumHours, maximumHours, availableDate, availabilityType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DetailedShippingTimeType {\n");
    
    sb.append("    minimumHours: ").append(toIndentedString(minimumHours)).append("\n");
    sb.append("    maximumHours: ").append(toIndentedString(maximumHours)).append("\n");
    sb.append("    availableDate: ").append(toIndentedString(availableDate)).append("\n");
    sb.append("    availabilityType: ").append(toIndentedString(availabilityType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}


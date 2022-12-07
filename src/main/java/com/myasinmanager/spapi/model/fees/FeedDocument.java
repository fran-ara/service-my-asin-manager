/*
 * Selling Partner API for Feeds
 * The Selling Partner API for Feeds lets you upload data to Amazon on behalf of a selling partner.
 *
 * OpenAPI spec version: 2021-06-30
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.myasinmanager.spapi.model.fees;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.IOException;
import java.util.Objects;

/**
 * Information required for the feed document.
 */
@ApiModel(description = "Information required for the feed document.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2022-12-01T06:29:08.866-06:00")
public class FeedDocument {
  @SerializedName("feedDocumentId")
  private String feedDocumentId = null;

  @SerializedName("url")
  private String url = null;

  /**
   * If present, the feed document contents are compressed using the indicated algorithm.
   */
  @JsonAdapter(CompressionAlgorithmEnum.Adapter.class)
  public enum CompressionAlgorithmEnum {
    GZIP("GZIP");

    private String value;

    CompressionAlgorithmEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static CompressionAlgorithmEnum fromValue(String text) {
      for (CompressionAlgorithmEnum b : CompressionAlgorithmEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<CompressionAlgorithmEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CompressionAlgorithmEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CompressionAlgorithmEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return CompressionAlgorithmEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("compressionAlgorithm")
  private CompressionAlgorithmEnum compressionAlgorithm = null;

  public FeedDocument feedDocumentId(String feedDocumentId) {
    this.feedDocumentId = feedDocumentId;
    return this;
  }

   /**
   * The identifier for the feed document. This identifier is unique only in combination with a seller ID.
   * @return feedDocumentId
  **/
  @ApiModelProperty(required = true, value = "The identifier for the feed document. This identifier is unique only in combination with a seller ID.")
  public String getFeedDocumentId() {
    return feedDocumentId;
  }

  public void setFeedDocumentId(String feedDocumentId) {
    this.feedDocumentId = feedDocumentId;
  }

  public FeedDocument url(String url) {
    this.url = url;
    return this;
  }

   /**
   * A presigned URL for the feed document. This URL expires after 5 minutes.
   * @return url
  **/
  @ApiModelProperty(required = true, value = "A presigned URL for the feed document. This URL expires after 5 minutes.")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public FeedDocument compressionAlgorithm(CompressionAlgorithmEnum compressionAlgorithm) {
    this.compressionAlgorithm = compressionAlgorithm;
    return this;
  }

   /**
   * If present, the feed document contents are compressed using the indicated algorithm.
   * @return compressionAlgorithm
  **/
  @ApiModelProperty(value = "If present, the feed document contents are compressed using the indicated algorithm.")
  public CompressionAlgorithmEnum getCompressionAlgorithm() {
    return compressionAlgorithm;
  }

  public void setCompressionAlgorithm(CompressionAlgorithmEnum compressionAlgorithm) {
    this.compressionAlgorithm = compressionAlgorithm;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FeedDocument feedDocument = (FeedDocument) o;
    return Objects.equals(this.feedDocumentId, feedDocument.feedDocumentId) &&
        Objects.equals(this.url, feedDocument.url) &&
        Objects.equals(this.compressionAlgorithm, feedDocument.compressionAlgorithm);
  }

  @Override
  public int hashCode() {
    return Objects.hash(feedDocumentId, url, compressionAlgorithm);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FeedDocument {\n");
    
    sb.append("    feedDocumentId: ").append(toIndentedString(feedDocumentId)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    compressionAlgorithm: ").append(toIndentedString(compressionAlgorithm)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}


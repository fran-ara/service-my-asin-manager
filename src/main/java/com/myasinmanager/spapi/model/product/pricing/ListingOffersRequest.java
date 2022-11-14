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

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * ListingOffersRequest
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2022-11-02T11:15:55.180-06:00")
public class ListingOffersRequest {
	@SerializedName("uri")
	private String uri = null;

	@SerializedName("method")
	private HttpMethod method = null;

	@SerializedName("headers")
	private HttpRequestHeaders headers = null;

	@SerializedName("MarketplaceId")
	private String marketplaceId = null;

	@SerializedName("ItemCondition")
	private ItemCondition itemCondition = null;

	@SerializedName("CustomerType")
	private CustomerType customerType = null;

	public ListingOffersRequest uri(String uri) {
		this.uri = uri;
		return this;
	}

	/**
	 * The resource path of the operation you are calling in batch without any query
	 * parameters. If you are calling &#x60;getItemOffersBatch&#x60;, supply the
	 * path of &#x60;getItemOffers&#x60;. **Example:**
	 * &#x60;/products/pricing/v0/items/B000P6Q7MY/offers&#x60; If you are calling
	 * &#x60;getListingOffersBatch&#x60;, supply the path of
	 * &#x60;getListingOffers&#x60;. **Example:**
	 * &#x60;/products/pricing/v0/listings/B000P6Q7MY/offers&#x60;
	 * 
	 * @return uri
	 **/
	@ApiModelProperty(required = true, value = "The resource path of the operation you are calling in batch without any query parameters.  If you are calling `getItemOffersBatch`, supply the path of `getItemOffers`.  **Example:** `/products/pricing/v0/items/B000P6Q7MY/offers`  If you are calling `getListingOffersBatch`, supply the path of `getListingOffers`.  **Example:** `/products/pricing/v0/listings/B000P6Q7MY/offers`")
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public ListingOffersRequest method(HttpMethod method) {
		this.method = method;
		return this;
	}

	/**
	 * Get method
	 * 
	 * @return method
	 **/
	@ApiModelProperty(required = true, value = "")
	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public ListingOffersRequest headers(HttpRequestHeaders headers) {
		this.headers = headers;
		return this;
	}

	/**
	 * Get headers
	 * 
	 * @return headers
	 **/
	@ApiModelProperty(value = "")
	public HttpRequestHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpRequestHeaders headers) {
		this.headers = headers;
	}

	public ListingOffersRequest marketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
		return this;
	}

	/**
	 * Get marketplaceId
	 * 
	 * @return marketplaceId
	 **/
	@ApiModelProperty(required = true, value = "")
	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public ListingOffersRequest itemCondition(ItemCondition itemCondition) {
		this.itemCondition = itemCondition;
		return this;
	}

	/**
	 * Get itemCondition
	 * 
	 * @return itemCondition
	 **/
	@ApiModelProperty(required = true, value = "")
	public ItemCondition getItemCondition() {
		return itemCondition;
	}

	public void setItemCondition(ItemCondition itemCondition) {
		this.itemCondition = itemCondition;
	}

	public ListingOffersRequest customerType(CustomerType customerType) {
		this.customerType = customerType;
		return this;
	}

	/**
	 * Get customerType
	 * 
	 * @return customerType
	 **/
	@ApiModelProperty(value = "")
	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ListingOffersRequest listingOffersRequest = (ListingOffersRequest) o;
		return Objects.equals(this.uri, listingOffersRequest.uri)
				&& Objects.equals(this.method, listingOffersRequest.method)
				&& Objects.equals(this.headers, listingOffersRequest.headers)
				&& Objects.equals(this.marketplaceId, listingOffersRequest.marketplaceId)
				&& Objects.equals(this.itemCondition, listingOffersRequest.itemCondition)
				&& Objects.equals(this.customerType, listingOffersRequest.customerType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uri, method, headers, marketplaceId, itemCondition, customerType);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ListingOffersRequest {\n");

		sb.append("    uri: ").append(toIndentedString(uri)).append("\n");
		sb.append("    method: ").append(toIndentedString(method)).append("\n");
		sb.append("    headers: ").append(toIndentedString(headers)).append("\n");
		sb.append("    marketplaceId: ").append(toIndentedString(marketplaceId)).append("\n");
		sb.append("    itemCondition: ").append(toIndentedString(itemCondition)).append("\n");
		sb.append("    customerType: ").append(toIndentedString(customerType)).append("\n");
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

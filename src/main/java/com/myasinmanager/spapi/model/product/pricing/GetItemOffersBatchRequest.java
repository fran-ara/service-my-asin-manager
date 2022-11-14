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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * The request associated with the &#x60;getItemOffersBatch&#x60; API call.
 */
@ApiModel(description = "The request associated with the `getItemOffersBatch` API call.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2022-11-02T11:15:55.180-06:00")
public class GetItemOffersBatchRequest {
	@SerializedName("requests")
	private ItemOffersRequestList requests = null;

	public GetItemOffersBatchRequest requests(ItemOffersRequestList requests) {
		this.requests = requests;
		return this;
	}

	/**
	 * Get requests
	 * 
	 * @return requests
	 **/
	@ApiModelProperty(value = "")
	public ItemOffersRequestList getRequests() {
		return requests;
	}

	public void setRequests(ItemOffersRequestList requests) {
		this.requests = requests;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		GetItemOffersBatchRequest getItemOffersBatchRequest = (GetItemOffersBatchRequest) o;
		return Objects.equals(this.requests, getItemOffersBatchRequest.requests);
	}

	@Override
	public int hashCode() {
		return Objects.hash(requests);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class GetItemOffersBatchRequest {\n");

		sb.append("    requests: ").append(toIndentedString(requests)).append("\n");
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

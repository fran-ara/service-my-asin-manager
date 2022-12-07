/*
 * Selling Partner API for Catalog Items
 * The Selling Partner API for Catalog Items provides programmatic access to information about items in the Amazon catalog.  For more information, refer to the [Catalog Items API Use Case Guide](doc:catalog-items-api-v2022-04-01-use-case-guide).
 *
 * OpenAPI spec version: 2022-04-01
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.myasinmanager.spapi.model.catalog;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * An item in the Amazon catalog.
 */
@ApiModel(description = "An item in the Amazon catalog.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2022-11-18T10:44:01.033-06:00")
public class Item {
	@SerializedName("asin")
	private String asin = null;

	@SerializedName("attributes")
	private ItemAttributes attributes = null;

	@SerializedName("dimensions")
	private ItemDimensions dimensions = null;

	@SerializedName("identifiers")
	private ItemIdentifiers identifiers = null;

	@SerializedName("images")
	private ItemImages images = null;

	@SerializedName("productTypes")
	private ItemProductTypes productTypes = null;

	@SerializedName("relationships")
	private ItemRelationships relationships = null;

	@SerializedName("salesRanks")
	private ItemSalesRanks salesRanks = null;

	@SerializedName("summaries")
	private ItemSummaries summaries = null;

	@SerializedName("vendorDetails")
	private ItemVendorDetails vendorDetails = null;

	public Item asin(String asin) {
		this.asin = asin;
		return this;
	}

	/**
	 * Get asin
	 * 
	 * @return asin
	 **/
	@ApiModelProperty(required = true, value = "")
	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public Item attributes(ItemAttributes attributes) {
		this.attributes = attributes;
		return this;
	}

	/**
	 * Get attributes
	 * 
	 * @return attributes
	 **/
	@ApiModelProperty(value = "")
	public ItemAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(ItemAttributes attributes) {
		this.attributes = attributes;
	}

	public Item dimensions(ItemDimensions dimensions) {
		this.dimensions = dimensions;
		return this;
	}

	/**
	 * Get dimensions
	 * 
	 * @return dimensions
	 **/
	@ApiModelProperty(value = "")
	public ItemDimensions getDimensions() {
		return dimensions;
	}

	public void setDimensions(ItemDimensions dimensions) {
		this.dimensions = dimensions;
	}

	public Item identifiers(ItemIdentifiers identifiers) {
		this.identifiers = identifiers;
		return this;
	}

	/**
	 * Get identifiers
	 * 
	 * @return identifiers
	 **/
	@ApiModelProperty(value = "")
	public ItemIdentifiers getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(ItemIdentifiers identifiers) {
		this.identifiers = identifiers;
	}

	public Item images(ItemImages images) {
		this.images = images;
		return this;
	}

	/**
	 * Get images
	 * 
	 * @return images
	 **/
	@ApiModelProperty(value = "")
	public ItemImages getImages() {
		return images;
	}

	public void setImages(ItemImages images) {
		this.images = images;
	}

	public Item productTypes(ItemProductTypes productTypes) {
		this.productTypes = productTypes;
		return this;
	}

	/**
	 * Get productTypes
	 * 
	 * @return productTypes
	 **/
	@ApiModelProperty(value = "")
	public ItemProductTypes getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(ItemProductTypes productTypes) {
		this.productTypes = productTypes;
	}

	public Item relationships(ItemRelationships relationships) {
		this.relationships = relationships;
		return this;
	}

	/**
	 * Get relationships
	 * 
	 * @return relationships
	 **/
	@ApiModelProperty(value = "")
	public ItemRelationships getRelationships() {
		return relationships;
	}

	public void setRelationships(ItemRelationships relationships) {
		this.relationships = relationships;
	}

	public Item salesRanks(ItemSalesRanks salesRanks) {
		this.salesRanks = salesRanks;
		return this;
	}

	/**
	 * Get salesRanks
	 * 
	 * @return salesRanks
	 **/
	@ApiModelProperty(value = "")
	public ItemSalesRanks getSalesRanks() {
		return salesRanks;
	}

	public void setSalesRanks(ItemSalesRanks salesRanks) {
		this.salesRanks = salesRanks;
	}

	public Item summaries(ItemSummaries summaries) {
		this.summaries = summaries;
		return this;
	}

	/**
	 * Get summaries
	 * 
	 * @return summaries
	 **/
	@ApiModelProperty(value = "")
	public ItemSummaries getSummaries() {
		return summaries;
	}

	public void setSummaries(ItemSummaries summaries) {
		this.summaries = summaries;
	}

	public Item vendorDetails(ItemVendorDetails vendorDetails) {
		this.vendorDetails = vendorDetails;
		return this;
	}

	/**
	 * Get vendorDetails
	 * 
	 * @return vendorDetails
	 **/
	@ApiModelProperty(value = "")
	public ItemVendorDetails getVendorDetails() {
		return vendorDetails;
	}

	public void setVendorDetails(ItemVendorDetails vendorDetails) {
		this.vendorDetails = vendorDetails;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Item item = (Item) o;
		return Objects.equals(this.asin, item.asin) && Objects.equals(this.attributes, item.attributes)
				&& Objects.equals(this.dimensions, item.dimensions)
				&& Objects.equals(this.identifiers, item.identifiers) && Objects.equals(this.images, item.images)
				&& Objects.equals(this.productTypes, item.productTypes)
				&& Objects.equals(this.relationships, item.relationships)
				&& Objects.equals(this.salesRanks, item.salesRanks) && Objects.equals(this.summaries, item.summaries)
				&& Objects.equals(this.vendorDetails, item.vendorDetails);
	}

	@Override
	public int hashCode() {
		return Objects.hash(asin, attributes, dimensions, identifiers, images, productTypes, relationships, salesRanks,
				summaries, vendorDetails);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Item {\n");

		sb.append("    asin: ").append(toIndentedString(asin)).append("\n");
		sb.append("    attributes: ").append(toIndentedString(attributes)).append("\n");
		sb.append("    dimensions: ").append(toIndentedString(dimensions)).append("\n");
		sb.append("    identifiers: ").append(toIndentedString(identifiers)).append("\n");
		sb.append("    images: ").append(toIndentedString(images)).append("\n");
		sb.append("    productTypes: ").append(toIndentedString(productTypes)).append("\n");
		sb.append("    relationships: ").append(toIndentedString(relationships)).append("\n");
		sb.append("    salesRanks: ").append(toIndentedString(salesRanks)).append("\n");
		sb.append("    summaries: ").append(toIndentedString(summaries)).append("\n");
		sb.append("    vendorDetails: ").append(toIndentedString(vendorDetails)).append("\n");
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

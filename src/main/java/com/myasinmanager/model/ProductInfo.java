package com.myasinmanager.model;

import java.math.BigDecimal;
import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@DynamoDBTable(tableName = "ProductInfo")
@Builder
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@ToString
public class ProductInfo {

	private String id;
	private String title;
	private String image;
	private String asin;
	private String amazonLink;
	private String supplierLink;
	private String supplierName;
	private BigDecimal currentBBPrice;
	private BigDecimal buyCost;
	private BigDecimal netProfit;
	private BigDecimal roi;
	private BigDecimal netMargin;
	private BigDecimal additionalCost;
	private BigDecimal currentBSR;
	private BigDecimal latestDaysPriceAverage;
	private String productCategory;

	@JsonIgnore
	private String notes;

	@JsonIgnore
	private String tags;

	private Integer fbaSellerCount;

	@JsonIgnore
	private Date date;

	@JsonProperty("details")
	private Details details;

	@DynamoDBHashKey
	@DynamoDBAttribute
	public String getId() {
		return id;
	}

	@DynamoDBAttribute
	public String getTitle() {
		return title;
	}

	@DynamoDBAttribute
	public String getImage() {
		return image;
	}

	@DynamoDBAttribute
	public String getAsin() {
		return asin;
	}

	@DynamoDBAttribute
	public String getAmazonLink() {
		return amazonLink;
	}

	@DynamoDBAttribute
	public String getSupplierLink() {
		return supplierLink;
	}

	@DynamoDBAttribute
	public String getSupplierName() {
		return supplierName;
	}

	@DynamoDBAttribute
	public BigDecimal getCurrentBBPrice() {
		return currentBBPrice;
	}

	@DynamoDBAttribute
	public BigDecimal getBuyCost() {
		return buyCost;
	}

	@DynamoDBAttribute
	public BigDecimal getNetProfit() {
		return netProfit;
	}

	@DynamoDBAttribute
	public BigDecimal getRoi() {
		return roi;
	}

	@DynamoDBAttribute
	public BigDecimal getNetMargin() {
		return netMargin;
	}

	@DynamoDBAttribute
	public BigDecimal getAdditionalCost() {
		return additionalCost;
	}

	@DynamoDBAttribute
	public BigDecimal getCurrentBSR() {
		return currentBSR;
	}

	@DynamoDBAttribute
	public BigDecimal getLatestDaysPriceAverage() {
		return latestDaysPriceAverage;
	}

	@DynamoDBAttribute
	public String getProductCategory() {
		return productCategory;
	}

	@DynamoDBAttribute
	public String getNotes() {
		return notes;
	}

	@DynamoDBAttribute
	public Date getDate() {
		return date;
	}

	@DynamoDBAttribute
	public String getTags() {
		return tags;
	}

	@DynamoDBAttribute
	public Integer getFbaSellerCount() {
		return fbaSellerCount;
	}

	public Details getDetails() {
		this.setDetails(new Details(this.getNotes(), this.getTags(), this.getDate()));
		return details;

	}

	@EqualsAndHashCode
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonInclude(Include.NON_NULL)
	@ToString
	@Data
	class Details {

		@JsonProperty("notes")
		private String notes;
		@JsonProperty("tags")
		private String tags;
		@JsonProperty("date")
		private Date date;

	}

}

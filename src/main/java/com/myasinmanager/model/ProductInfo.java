package com.myasinmanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Builder
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ProductInfo {

	private String id;
	private String title;
	private String asin;
	private String amazonLink;
	private String supplierLink;
	private String supplierName;
	private BigDecimal currentBBPrice;
	private BigDecimal buyCost;
	private BigDecimal netProfit;
	private BigDecimal roi;
	private BigDecimal margin;
	private BigDecimal additionalCost;
	private BigDecimal currenBSR;
	private BigDecimal latestDaysPriceAverage;
	private String productCategory;
	private String notes;
	private LocalDate date;

	@DynamoDbPartitionKey
	@DynamoDbAttribute("id")
	public String getId() {
		return id;
	}

	@DynamoDbAttribute("title")
	public String getTitle() {
		return title;
	}

	@DynamoDbAttribute("asin")
	public String getAsin() {
		return asin;
	}

	@DynamoDbAttribute("amazonLink")
	public String getAmazonLink() {
		return amazonLink;
	}

	@DynamoDbAttribute("supplierLink")
	public String getSupplierLink() {
		return supplierLink;
	}

	@DynamoDbAttribute("supplierName")
	public String getSupplierName() {
		return supplierName;
	}

	@DynamoDbAttribute("currentBBPrice")
	public BigDecimal getCurrentBBPrice() {
		return currentBBPrice;
	}

	@DynamoDbAttribute("buyCost")
	public BigDecimal getBuyCost() {
		return buyCost;
	}

	@DynamoDbAttribute("netProfit")
	public BigDecimal getNetProfit() {
		return netProfit;
	}

	@DynamoDbAttribute("roi")
	public BigDecimal getRoi() {
		return roi;
	}

	@DynamoDbAttribute("margin")
	public BigDecimal getMargin() {
		return margin;
	}

	@DynamoDbAttribute("additionalCost")
	public BigDecimal getAdditionalCost() {
		return additionalCost;
	}

	@DynamoDbAttribute("currentBSR")
	public BigDecimal getCurrenBSR() {
		return currenBSR;
	}

	@DynamoDbAttribute("latestDaysPriceAverage")
	public BigDecimal getLatestDaysPriceAverage() {
		return latestDaysPriceAverage;
	}

	@DynamoDbAttribute("productCategory")
	public String getProductCategory() {
		return productCategory;
	}

	@DynamoDbAttribute("notes")
	public String getNotes() {
		return notes;
	}

	@DynamoDbAttribute("date")
	public LocalDate getDate() {
		return date;
	}

}

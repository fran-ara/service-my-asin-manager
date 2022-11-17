package com.myasinmanager.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = "tags")
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@ToString(exclude = "tags")
@Table(name = "products", schema = "public")
@Entity(name = "products")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "incrementDomain")
	@GenericGenerator(name = "incrementDomain", strategy = "increment")
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "image")
	private String image;

	@Column(name = "asin")
	private String asin;

	@Column(name = "amazon_link")
	private String amazonLink;

	@Column(name = "supplier_link")
	private String supplierLink;

	@Column(name = "current_bb_price")
	private BigDecimal currentBBPrice;

	@Column(name = "buy_cost")
	private BigDecimal buyCost;

//	@Column(name = "net_profit")
//	private BigDecimal netProfit;

	@Column(name = "roi")
	private BigDecimal roi;

	@Column(name = "net_margin")
	private BigDecimal netMargin;

	@Column(name = "additional_cost")
	private BigDecimal additionalCost;

	@Column(name = "current_bsr")
	private BigDecimal currentBSR;

	@Column(name = "category")
	private String category;

	@JsonIgnore
	@Column(name = "notes")
	private String notes;

//	@JsonIgnore
//	@Column(name = "tags")
//	private String tags;

	@JsonIgnoreProperties("products")
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "products_tags", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<TagEntity> tags;
	
	@JsonIgnore
	@Transient
	private List<Integer> tagsId;


	@Column(name = "fba_seller_count")
	private Integer fbaSellerCount;

	@JsonIgnore
	@Column(name = "date")
	private Date date;

	@JsonProperty("details")
	@Transient
	private Details details;

	public Details getDetails() {
		this.setDetails(new Details(this.getNotes(), 
				this.getTags().stream().map(t -> t.getName()).collect(Collectors.toList()), this.getDate()));
		return details;

	}
	
	public List<Integer>  getTagsId() {
		this.setTagsId(this.getTags().stream().map(t -> t.getId().intValue()).collect(Collectors.toList()));
		return tagsId;

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
		private List<String> tags;
		@JsonProperty("date")
		private Date date;

	}
}
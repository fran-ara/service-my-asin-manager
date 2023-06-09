package com.myasinmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
public class ProductEntity implements Serializable {

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

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "current_bb_price")
    private BigDecimal currentBBPrice;

    @Column(name = "buy_cost")
    private BigDecimal buyCost;

//	@Column(name = "net_profit")
//	private BigDecimal netProfit;

    @Column(name = "roi")
    private BigDecimal roi;

    @Column(name = "net_profit")
    private BigDecimal netProfit;

    @Column(name = "additional_cost")
    private BigDecimal additionalCost;

    @Column(name = "current_bsr")
    private BigDecimal currentBSR;

    @Column(name = "category")
    private String category;

    @Column(name = "user_id")
    private Long userId;

    @JsonIgnore
    @Column(name = "notes")
    private String notes;

//	@JsonIgnore
//	@Column(name = "tags")
//	private String tags;

    @JsonIgnoreProperties("products")
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    @JoinTable(name = "products_tags", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags;

    @JsonIgnore
    @Transient
    private List<Integer> tagsId;

    @Column(name = "fba_seller_count")
    private Integer fbaSellerCount;

    @Column(name = "date")
    private Date date;

    @JsonProperty("details")
    @Transient
    private Details details;

    public Details getDetails() {
        this.setDetails(new Details(this.getNotes(), this.getTags()));
        return details;

    }

    public List<Integer> getTagsId() {
        this.setTagsId(Objects.nonNull(this.getTags())
                ? this.getTags().stream().map(t -> t.getId().intValue()).collect(Collectors.toList())
                : new ArrayList<>());
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
        private Set<TagEntity> tags;

    }
}
package com.myasinmanager.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = "products")
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@ToString(exclude = "products")
@Table(name = "tags", schema = "public")
@Entity(name = "tags")

public class TagEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "incrementDomain")
	@GenericGenerator(name = "incrementDomain", strategy = "increment")
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToMany(mappedBy = "tags",cascade = CascadeType.ALL,fetch = FetchType.LAZY)	
    @JsonIgnoreProperties("tags")
	@JsonIgnore
	private Set<ProductEntity> products;

}
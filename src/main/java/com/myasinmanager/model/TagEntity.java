package com.myasinmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

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

	@Column(name = "user_id")
	private Long userId;

	@ManyToMany(mappedBy = "tags",cascade = CascadeType.ALL,fetch = FetchType.LAZY)	
    @JsonIgnoreProperties("tags")
	@JsonIgnore
	private Set<ProductEntity> products;

}
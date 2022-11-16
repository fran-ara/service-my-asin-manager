package com.myasinmanager.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.myasinmanager.model.ProductEntity;

@Transactional
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, String> {
	@Modifying
	@Query(value = "UPDATE public.products SET buy_cost = to_number(FLOOR(random()*(10-1+1))+1,'9G999g999') WHERE id = '1'", nativeQuery = true)
	public void turnPurple();

	Page<ProductEntity> findAll(Pageable pageable);

}
package com.myasinmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.myasinmanager.model.ProductEntity;

import java.util.Optional;

@Transactional
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {

	Page<ProductEntity> findAll(Pageable pageable);

	Page<ProductEntity> findByUserId(Long userId, Pageable pageable);
	Optional<ProductEntity> findByAsinAndUserId(String asin, Long userId);

}
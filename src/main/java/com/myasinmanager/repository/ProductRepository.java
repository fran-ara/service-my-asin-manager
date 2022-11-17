package com.myasinmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.myasinmanager.model.ProductEntity;

@Transactional
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, String> {

	Page<ProductEntity> findAll(Pageable pageable);

}
package com.myasinmanager.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.myasinmanager.model.ProductInfo;

public interface PagingProductRepository  {

//	@EnableScan
//	@EnableScanCount
//	Page<ProductInfo> findAll(Pageable pageable);
}
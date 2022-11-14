package com.myasinmanager.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myasinmanager.exception.ProductInfoNotFoundException;
import com.myasinmanager.model.ProductInfo;
import com.myasinmanager.repository.PagingProductRepository;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductInfoService {

//	@Autowired
//	private PagingProductRepository pagingProductRepository;
//
//	public ProductInfo create(ProductInfo product) {
//		UUID uuid = UUID.randomUUID();
//		String uuidAsString = uuid.toString();
//
//		product.setId(uuidAsString);
//
//		pagingProductRepository.save(product);
//		return product;
//	}
//
//	public ProductInfo getById(String id) {
//		ProductInfo product = pagingProductRepository.findById(id).orElseThrow(
//				() -> new ProductInfoNotFoundException(String.format("Product with the id: %s not found.", id)));
//		return product;
//	}
//
//	public Page<ProductInfo> findAll(Pageable pageable) {
//		Page<ProductInfo> productsPaginated =pagingProductRepository.findAll(pageable);
//		log.debug("Response  findAll:{}", productsPaginated.getContent());
//		return productsPaginated;
//	}
}

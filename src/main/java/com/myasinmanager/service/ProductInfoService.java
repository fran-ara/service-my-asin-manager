package com.myasinmanager.service;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myasinmanager.exception.ProductInfoNotFoundException;
import com.myasinmanager.model.ProductInfo;
import com.myasinmanager.repository.ProductInfoRepository;

@Service
public class ProductInfoService {

	@Autowired
	private ProductInfoRepository productInfoRepositoryImpl;

	public ProductInfo create(ProductInfo product) {
		UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        
        product.setId(uuidAsString);
		
        productInfoRepositoryImpl.save(product);
	    return product;
	}

	public ProductInfo getById(String id) {
		ProductInfo product = productInfoRepositoryImpl.findById(id);
		if(Objects.isNull(product)) {
			 throw new ProductInfoNotFoundException(String.format("Product with the id: %s not found.", id));
		}
		return product;
	}
//
//	public Page<ProductInfo> findAll(Pageable pageable) {
//		return productInfoRepositoryImpl.findAll(pageable);
//	}
}

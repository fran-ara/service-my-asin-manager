package com.myasinmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myasinmanager.exception.ProductInfoNotFoundException;
import com.myasinmanager.model.ProductEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductSPAPIService {

	@Autowired
	private ProductService productService;

	static List<ProductEntity> productsInSpApi;


	public ProductEntity findByAsin(String asin) {
		log.debug("Searching in SP by asin {}", asin);
		return productsInSpApi.stream().filter(p -> asin.equals(p.getAsin())).findFirst()
				.orElseThrow(() -> new ProductInfoNotFoundException(asin));
	}

	public void processByAsin(String asin) {
		log.debug("Product to be processed: {}", asin);
		ProductEntity productFromSPAPI = findByAsin(asin);

		productService.create(productFromSPAPI);

	}
}

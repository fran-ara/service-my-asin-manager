package com.myasinmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myasinmanager.exception.ProductInfoNotFoundException;
import com.myasinmanager.model.ProductEntity;
import com.myasinmanager.service.ProductSPAPIService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sp-api")
@Slf4j
public class ProductSPAPIController {

	@Autowired
	private ProductSPAPIService productSPAPIService;

	@PostMapping("/process-product")
	public ResponseEntity<ProductEntity> getAl(@RequestParam String asin) {
		log.debug("Process product [asin = {}]", asin);

		try {
			productSPAPIService.processByAsin(asin);
			return ResponseEntity.noContent().build();
		} catch (ProductInfoNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

}

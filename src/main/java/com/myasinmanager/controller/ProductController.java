package com.myasinmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myasinmanager.model.ProductEntity;
import com.myasinmanager.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productInfoService;

	@GetMapping
	public ResponseEntity<Page<ProductEntity>> getAll(Pageable pageable) {
		log.debug("Getting all products with pagination [page = {}, size= {}]", pageable.getPageNumber(),
				pageable.getPageSize());
		return ResponseEntity.ok(productInfoService.findAll(pageable));
	}

	@PostMapping("/load-data")
	public ResponseEntity<Void> getAll() {
		log.debug("Loading data into database[page = {}, size= {}]");
		productInfoService.insertData();
		return ResponseEntity.ok().build();
	}
}

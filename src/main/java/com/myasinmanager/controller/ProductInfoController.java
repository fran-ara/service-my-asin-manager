package com.myasinmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myasinmanager.exception.ProductInfoNotFoundException;
import com.myasinmanager.model.ProductInfo;
import com.myasinmanager.service.ProductInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products-spapi")
@Slf4j
public class ProductInfoController {

//	@Autowired
//	private ProductInfoService productInfoService;
//
//	@PostMapping
//	public ResponseEntity<ProductInfo> create(@RequestBody ProductInfo productInfoRequest) {
//		log.debug("Creating product with request {}", productInfoRequest);
//		return ResponseEntity.status(HttpStatus.CREATED).body(productInfoService.create(productInfoRequest));
//	}
//
//	@GetMapping
//	public ResponseEntity<Page<ProductInfo>> getAll(Pageable pageable) {
//		log.debug("Getting all products with pagination [page = {}, size= {}]", pageable.getPageNumber(),
//				pageable.getPageSize());
//		return ResponseEntity.ok(productInfoService.findAll(pageable));
//	}
//
//	@GetMapping("{id}")
//	public ResponseEntity<ProductInfo> getById(@PathVariable String id) {
//		log.debug("Getting product by id = {}", id);
//		try {
//			return ResponseEntity.ok(productInfoService.getById(id));
//		} catch (ProductInfoNotFoundException e) {
//			return ResponseEntity.notFound().build();
//		}
//	}
}

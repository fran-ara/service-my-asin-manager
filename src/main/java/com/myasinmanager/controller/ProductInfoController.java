package com.myasinmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/products")
public class ProductInfoController {

	@Autowired
	private ProductInfoService productInfoService;

	@PostMapping
	public ResponseEntity<ProductInfo> create(@RequestBody ProductInfo productInfoRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productInfoService.create(productInfoRequest));
	}

//	@GetMapping
//	public ResponseEntity<Page<ProductInfo>> getAll(Pageable pageable) {
//		return ResponseEntity.ok(productInfoService.findAll(pageable));
//	}

	@GetMapping("{id}")
	public ResponseEntity<ProductInfo> getAll(@PathVariable String id) {
		try {
			return ResponseEntity.ok(productInfoService.getById(id));
		} catch (ProductInfoNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}

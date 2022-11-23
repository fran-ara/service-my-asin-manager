package com.myasinmanager.controller;

import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myasinmanager.model.ProductEntity;
import com.myasinmanager.model.TagEntity;
import com.myasinmanager.service.ProductService;
import com.myasinmanager.service.TagService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@Slf4j
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private TagService tagService;

	@GetMapping
	public ResponseEntity<Page<ProductEntity>> getAll(@RequestParam("tags") Integer[] tags, Pageable pageable) {
		log.debug("Getting all products with pagination [page = {}, size= {}, tags = {}]", pageable.getPageNumber(),
				pageable.getPageSize(), tags);
		return ResponseEntity.ok(productService.findAll(pageable, tags));
	}

	@PostMapping("/{id}/add-tags")
	public ResponseEntity<Void> addTagas(@PathVariable Integer id, @RequestParam Integer[] tags) {
		log.debug("Adding taggs [{}] to the product [{}]", Arrays.toString(tags), id);
		productService.setTagsToProduct(id, Stream.of(tags).toList());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{id}/assing-tag")
	public ResponseEntity<Void> createTag(@PathVariable Integer id, @RequestParam("name") String name) {
		log.debug("Creating new tag {} to the product {}", name, id);
		TagEntity tagEntity = tagService.create(name);
		productService.addNewTagToProduct(id, tagEntity);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/{id}/add-notes")
	public ResponseEntity<Void> addNote(@PathVariable Integer id, @RequestParam("notes") String notes) {
		log.debug("Creating new notes {} to the product {}", notes, id);
		productService.setNotesToProduct(id, notes);
		return ResponseEntity.noContent().build();
	}
}

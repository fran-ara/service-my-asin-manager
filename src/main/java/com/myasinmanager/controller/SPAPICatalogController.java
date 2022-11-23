package com.myasinmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myasinmanager.spapi.client.ApiException;
import com.myasinmanager.spapi.model.catalog.Item;
import com.myasinmanager.spapi.service.CatalogService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/spapi")
@Slf4j
public class SPAPICatalogController {

	@Autowired
	private CatalogService catalogService;

	@GetMapping
	public ResponseEntity<Item> getByAsin(@RequestParam("asin") String asin) {
		log.debug("Fetch product [asin = {}]", asin);
		try {
			Item item = catalogService.getProductsByAsin(asin);
			return ResponseEntity.ok(item);
		} catch (ApiException e) {
			log.error("SP API ERROR", e);
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			log.error("Internal Server error", e);
			return ResponseEntity.internalServerError().build();
		}
	}

}

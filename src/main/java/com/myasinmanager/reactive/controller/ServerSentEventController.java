package com.myasinmanager.reactive.controller;

import com.myasinmanager.model.ProductEntity;
import com.myasinmanager.reactive.service.ServerSentEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(value = { "*" }, allowedHeaders = { "*" }, maxAge = 9000)
public class ServerSentEventController {

	@Autowired
	private ServerSentEventService eventService;

	@GetMapping(path = "/sse/product-prices")
	@CrossOrigin({ "*" })
	public Flux<ServerSentEvent<List<ProductEntity>>> streamProducts(@RequestParam Integer[] tags, String username,Pageable pageable) {
		return eventService.getProducts(pageable,username, tags);
	}

	@GetMapping(path = "/sse/product-prices/update")
	@CrossOrigin({ "*" })
	public void streamProductsUpdate() {
		log.debug("Updating products");
		eventService.updateProducts();
	}
}
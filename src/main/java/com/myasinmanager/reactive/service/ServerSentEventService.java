package com.myasinmanager.reactive.service;

import com.myasinmanager.model.ProductEntity;
import com.myasinmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@Service
public class ServerSentEventService {

	@Autowired
	private ProductService productService;

	@CrossOrigin(allowCredentials = "true")
	public Flux<ServerSentEvent<List<ProductEntity>>> getProducts(Pageable pageable, String username,Integer[] tags) {
		return Flux.interval(Duration.ofSeconds(40))
				.map(sequence -> ServerSentEvent.<List<ProductEntity>>builder().id(String.valueOf(sequence))
						.event("products-event").data(productService.findAll(pageable,username, tags).getContent()).build());

	}

	@CrossOrigin(allowCredentials = "true")
	public void updateProducts() {

	}

}

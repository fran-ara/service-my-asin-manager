package com.myasinmanager.reactive.event;

import org.springframework.context.ApplicationEvent;

import com.myasinmanager.model.ProductEntity;

public class PriceUpdatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = -7922384174500333784L;

	public PriceUpdatedEvent(ProductEntity product) {
		super(product);
	}
}

package com.myasinmanager.reactive.publisher;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.myasinmanager.reactive.event.PriceUpdatedEvent;

import reactor.core.publisher.FluxSink;

public class PriceEventPublisher { // <2>
}

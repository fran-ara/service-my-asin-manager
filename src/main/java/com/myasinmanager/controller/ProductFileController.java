package com.myasinmanager.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.myasinmanager.spapi.client.ApiException;
import com.myasinmanager.spapi.service.CatalogService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("multipart-file")
@RequiredArgsConstructor
public class ProductFileController {

	@Autowired
	private CatalogService catalogService;

	@PostMapping("/upload-filePart")
	public Mono<List<String>> upload(@RequestPart("file") FilePart filePart) {

		return getLines(filePart).collectList();
	}

	public Flux<String> getLines(FilePart filePart) {
		return filePart.content().map(dataBuffer -> {
			byte[] bytes = new byte[dataBuffer.readableByteCount()];
			dataBuffer.read(bytes);
			DataBufferUtils.release(dataBuffer);

			return new String(bytes, StandardCharsets.UTF_8);
		}).map(this::processAndGetLinesAsList).flatMapIterable(Function.identity());
	}

	private List<String> processAndGetLinesAsList(String string) {

		Supplier<Stream<String>> streamSupplier = string::lines;
		try {
			catalogService.processItemsByAsin(streamSupplier.get().collect(Collectors.toList()));
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return streamSupplier.get().collect(Collectors.toList());
	}

}

package com.myasinmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.multipart.DefaultPartHttpMessageReader;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsGlobalConfiguration implements WebFluxConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		corsRegistry.addMapping("/**").allowedHeaders("*").allowedOrigins("*").allowedMethods("GET", "POST")
				.maxAge(3600);
	}

	@Override
	public void configureHttpMessageCodecs(ServerCodecConfigurer config) {
		DefaultPartHttpMessageReader partReader = new DefaultPartHttpMessageReader();
		partReader.setStreaming(true);
		config.customCodecs().register(partReader);
	}

	public DefaultPartHttpMessageReader defaultPartHttpMessageReader() {
		DefaultPartHttpMessageReader defaultPartHttpMessageReader = new DefaultPartHttpMessageReader();
		defaultPartHttpMessageReader.setStreaming(true);
		return defaultPartHttpMessageReader;
	}
}
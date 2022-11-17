package com.myasinmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myasinmanager.model.TagEntity;
import com.myasinmanager.service.TagService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tags")
@Slf4j
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class TagController {

	@Autowired
	private TagService tagService;

	@GetMapping
	public ResponseEntity<List<TagEntity>> getAll() {
		log.debug("Getting all tags");
		return ResponseEntity.ok(tagService.findAll());
	}

}

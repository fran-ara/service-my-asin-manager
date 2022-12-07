package com.myasinmanager.controller;

import com.myasinmanager.model.TagEntity;
import com.myasinmanager.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@Slf4j
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class TagController {

	@Autowired
	private TagService tagService;

	@GetMapping
	public ResponseEntity<List<TagEntity>> getAll(@RequestParam String username) {
		log.debug("Getting all tags");
		return ResponseEntity.ok(tagService.findAll(username));
	}

}

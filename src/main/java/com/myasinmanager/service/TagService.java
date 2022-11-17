package com.myasinmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myasinmanager.model.TagEntity;
import com.myasinmanager.repository.TagRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class TagService {

	@Autowired
	private TagRepository tagRepository;

	public List<TagEntity> findAll() {
		List<TagEntity> tags = tagRepository.findAll();
		log.debug("Response  TagService.findAll:{}", tags);
		return tags;
	}


}

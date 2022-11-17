package com.myasinmanager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myasinmanager.model.TagEntity;

public interface TagRepository extends CrudRepository<TagEntity, Long> {
	List<TagEntity> findAll();
}
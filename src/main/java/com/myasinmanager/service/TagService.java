package com.myasinmanager.service;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.myasinmanager.model.TagEntity;
import com.myasinmanager.repository.TagRepository;
import com.myasinmanager.security.model.User;
import com.myasinmanager.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;


    public List<TagEntity> findAll(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));

        List<TagEntity> tags = tagRepository.findAll();
        tags = tags.stream().filter(tag -> tag.getUserId().equals(user.getId())).collect(Collectors.toList());
        log.debug("Response  TagService.findAll:{}", tags);
        return tags;
    }

    public TagEntity create(String name) {
        log.debug("Creating new tag with name :{}", name);
        TagEntity tag = TagEntity.builder().name(name).build();
        return tagRepository.save(tag);
    }

}

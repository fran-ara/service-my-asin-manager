package com.myasinmanager.controller;

import com.myasinmanager.model.ProductEntity;
import com.myasinmanager.service.ProductService;
import com.myasinmanager.service.TagService;
import com.myasinmanager.spapi.client.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/products")
@Slf4j
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<Page<ProductEntity>> getAll(@RequestParam("tags") Integer[] tags, Pageable pageable, String username) {
        log.debug("For user {} , Getting all products with pagination [page = {}, size= {}, tags = {}]", username, pageable.getPageNumber(),
                pageable.getPageSize(), tags);
        return ResponseEntity.ok(productService.findAll(pageable, username, tags));
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    public String getName(Principal principal) {
        System.out.println("-----------------");
        System.out.println(principal.getName());
        return "";
    }

    @PutMapping("/{id}/update-product")
    public ResponseEntity<Void> upate(@PathVariable Integer id, @RequestBody ProductEntity productRequest) {
        log.debug("Updating the product with id {} : {}", id, productRequest);
        productService.updateProduct(id, productRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/delete-product")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.debug("Deleting the product with id {} ", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create-product-batch")
    public ResponseEntity<Void> createBatch(@RequestBody List<ProductEntity> productsRequest, @RequestParam String username) {
        log.debug("For user {}, creating products by batch {}", username, productsRequest);
        try {
            productService.createBatch(productsRequest, username);
        } catch (ApiException e) {
            log.error("Error creating products by batch", e);
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/add-tags")
    public ResponseEntity<Void> addTagas(@PathVariable Integer id, @RequestParam Integer[] tags) {
        log.debug("Adding taggs [{}] to the product [{}]", Arrays.toString(tags), id);
        productService.setTagsToProduct(id, Stream.of(tags).collect(Collectors.toList()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/assing-tag")
    public ResponseEntity<Void> createTag(@PathVariable Integer id, @RequestParam("username") String username, @RequestParam("name") String tagName) {
        log.debug("For user {} Creating new tag {} to the product {}",username, tagName, id);
        productService.addNewTagToProduct(id, username, tagName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/add-notes")
    public ResponseEntity<Void> addNote(@PathVariable Integer id, @RequestParam("notes") String notes) {
        log.debug("Creating new notes {} to the product {}", notes, id);
        productService.setNotesToProduct(id, notes);
        return ResponseEntity.noContent().build();
    }
}

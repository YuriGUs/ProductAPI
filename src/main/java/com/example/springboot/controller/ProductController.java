package com.example.springboot.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dtos.ProductRecordDTO;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;

import jakarta.persistence.Id;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class ProductController {
  
  @Autowired
  ProductRepository productRepository;

  @PostMapping("/products") // rota
  public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO) {
    var productModel = new ProductModel();
    BeanUtils.copyProperties(productRecordDTO, productModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
  }
  
  @GetMapping("/products")
  public ResponseEntity<List<ProductModel>> getAllProducts() {
    return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
  }
  
  @GetMapping("/products/{id}")
  public ResponseEntity<Object> getOneProduct(@PathVariable("id") UUID id) {
    Optional<ProductModel> product0 = productRepository.findById(id);
    if (product0.isPresent()) {
      return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
  }
  
}

/*
 * em saveProduct recebemos do requestbody objetos do tipo productrecordDTO. (que praticamente são os mesmos dados de productmodel)
 * como esses dados estão como obejto DTO faremos uma conversão de dados usando beanutils.copyProperties...
 * dessa forma -> BeanUtils.copyProperties(productRecordDTO, productModel); copiando de dto para model.
 */
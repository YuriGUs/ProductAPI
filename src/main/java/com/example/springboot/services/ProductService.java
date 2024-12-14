package com.example.springboot.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.dtos.ProductRecordDTO;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;

@Service
public class ProductService {
  
  @Autowired
  private ProductRepository productRepository;

  public Optional<ProductModel> updateProduct(UUID id, ProductRecordDTO updatedProduct) {
    Optional<ProductModel> existingProduct = productRepository.findById(id);

    if (existingProduct.isPresent()) {
      ProductModel product = existingProduct.get();

      product.setName(updatedProduct.name());
      product.setValue(updatedProduct.value());

      productRepository.save(product);
      return Optional.of(productRepository.save(product));
    }

    return Optional.empty();
  }
}

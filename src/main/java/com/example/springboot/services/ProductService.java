package com.example.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.dtos.ProductRecordDTO;
import com.example.springboot.exceptions.ResourceNotFoundException;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;

@Service
public class ProductService {
  
  @Autowired
  private ProductRepository productRepository;

  public ProductModel saveProduct(ProductRecordDTO productRecordDTO) {
    var productModel = new ProductModel();
    BeanUtils.copyProperties(productRecordDTO, productModel);
    return productRepository.save(productModel);
  }

  public List<ProductModel> getAllProducts() {
    return productRepository.findAll();
  }

  public ProductModel getOneProduct(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
  }


  public void deleteProductById(Long id) {
    if (!productRepository.existsById(id)) {
      throw new ResourceNotFoundException("Item não encontrado");
    }
    productRepository.deleteById(id);
  }
  
  
  public Optional<ProductModel> updateProduct(Long id, ProductRecordDTO updatedProduct) {
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

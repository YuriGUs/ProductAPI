package com.example.springboot.services;

import java.util.List;
import java.util.Optional;

import com.example.springboot.models.CategoryModel;
import com.example.springboot.repositories.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.dtos.ProductRecordDTO;
import com.example.springboot.exceptions.ResourceNotFoundException;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;


  @Autowired
  public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

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

  public ProductModel associateCategory(Long productId, Long categoryId ) {
    // Buscando o produto e categoria pelo ID
    ProductModel product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    CategoryModel category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

    // Associando categoria ao produto
    product.setCategoria(category);

    // Salvando o produto atualizado no banco
    return productRepository.save(product);
  }
}

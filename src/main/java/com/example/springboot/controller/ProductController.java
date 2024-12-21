package com.example.springboot.controller;

import java.util.List;
import java.util.Optional;

import com.example.springboot.services.CategoryService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dtos.ProductRecordDTO;
import com.example.springboot.models.ProductModel;
import com.example.springboot.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService, CategoryService categoryService) {
    this.productService = productService;
  }

  @PostMapping("/products") // rota
  public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO) {
    ProductModel savedProduct = productService.saveProduct(productRecordDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
  }
  
  @GetMapping("/products")
  public ResponseEntity<List<ProductModel>> getAllProducts() {
    List<ProductModel> products = productService.getAllProducts();
    return ResponseEntity.ok(products);
  }
  
  @GetMapping("/products/{id}")
  public ResponseEntity<ProductModel> getOneProduct(@PathVariable Long id) {
    ProductModel product = productService.getOneProduct(id);
    return ResponseEntity.ok(product);
  }

  @DeleteMapping("/products/{id}")
  public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
    productService.deleteProductById(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/products/{id}")
  public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRecordDTO updatedProduct) {
    Optional<ProductModel> updated = productService.updateProduct(id, updatedProduct);

    if (updated.isPresent()) {
      return ResponseEntity.ok("Produto atualizado com sucesso!");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
  }

  @PutMapping("/products/{productId}/category/{categoryId}")
  public ResponseEntity<ProductModel> associateCategory(@PathVariable Long productId, @PathVariable Long categoryId) {
    ProductModel updatedProduct = productService.associateCategory(productId, categoryId);
    return ResponseEntity.ok(updatedProduct);
  }
}

/*
 * em saveProduct recebemos do requestbody objetos do tipo productrecordDTO. (que praticamente são os mesmos dados de productmodel)
 * como esses dados estão como obejto DTO faremos uma conversão de dados usando beanutils.copyProperties...
 * dessa forma -> BeanUtils.copyProperties(productRecordDTO, productModel); copiando de dto para model.
 */
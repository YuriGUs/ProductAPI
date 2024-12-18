package com.example.springboot.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dtos.CategoryRecordDTO;
import com.example.springboot.models.CategoryModel;
import com.example.springboot.services.CategoryService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class CategoryController {
  
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping("/categories") // Criar categoria
  public ResponseEntity<CategoryModel> saveCagetory(@RequestBody CategoryRecordDTO categoryRecordDTO) {
    CategoryModel savedCategory = categoryService.saveCategory(categoryRecordDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
  }

  @GetMapping("/categories")
  public ResponseEntity<List<CategoryModel>> getAllCategories() {
    List<CategoryModel> listaDeCategoria = categoryService.getAllCategories();
    return ResponseEntity.ok(listaDeCategoria);
  }
  
  @GetMapping("/categories/{id}")
  public ResponseEntity<CategoryModel> getOneCategory(@PathVariable Long id) {
    CategoryModel categoria = categoryService.getOneCategory(id);

    return ResponseEntity.ok(categoria);
  }
  
  @DeleteMapping("/categories/{id}")
  public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
    categoryService.deleteCategoryById(id);
    return ResponseEntity.noContent().build();
  }
  
  @PutMapping("/categories/{id}")
  public ResponseEntity<CategoryModel> updateCategory(@PathVariable Long id, @RequestBody CategoryRecordDTO updatedCategory) {
    return categoryService.updateCategory(id, updatedCategory)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}

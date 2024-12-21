package com.example.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.dtos.CategoryRecordDTO;
import com.example.springboot.exceptions.ResourceNotFoundException;
import com.example.springboot.models.CategoryModel;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.CategoryRepository;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public CategoryModel saveCategory(CategoryRecordDTO categoryRecordDTO) {
    CategoryModel categoryModel = new CategoryModel();
    BeanUtils.copyProperties(categoryRecordDTO, categoryModel);
    return categoryRepository.save(categoryModel);
  }

  public List<CategoryModel> getAllCategories() {
    return categoryRepository.findAll();
  }

  public CategoryModel getOneCategory(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada" + id));
  }

  public void deleteCategoryById(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new ResourceNotFoundException("Categoria não encontrada");
    }
    categoryRepository.deleteById(id);
  }

  public Optional<CategoryModel> updateCategory(Long id, CategoryRecordDTO updatedCategory) {
    Optional<CategoryModel> existingCategory = categoryRepository.findById(id);

    if (existingCategory.isPresent()) {
      CategoryModel category = existingCategory.get();

      category.setName(updatedCategory.name());

      // Atualiza os produtos individualmente 
      List<ProductModel> novosProdutos = updatedCategory.produtos();
      
      for (ProductModel produto : novosProdutos) {
        if (!category.getProdutos().contains(produto)) {
          category.getProdutos().add(produto); // Adiciona apenas os que ainda não estão na lsita
        }
      }

      // Remove produtos que não estão na nova lista
      category.getProdutos().removeIf(produto -> !novosProdutos.contains(produto));
      categoryRepository.save(category);

      return Optional.of(category);
    }

    return Optional.empty();
  }
}

package com.example.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.models.CategoryModel;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long>{
  
}

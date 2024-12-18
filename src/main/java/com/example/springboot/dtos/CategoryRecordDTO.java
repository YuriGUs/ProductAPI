package com.example.springboot.dtos;

import java.util.Collections;
import java.util.List;

import com.example.springboot.models.ProductModel;

import jakarta.validation.constraints.NotBlank;

public record CategoryRecordDTO(@NotBlank String name, List<ProductModel> produtos) {
  public List<ProductModel> produtos() {
    // retorna a lista mesmo que ela esta vazia
    return produtos != null ? produtos : Collections.emptyList();
  }
}


// TODO Associar produtos às categorias
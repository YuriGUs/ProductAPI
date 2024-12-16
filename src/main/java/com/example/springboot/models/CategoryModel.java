package com.example.springboot.models;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TB_CATEGORIAS")
public class CategoryModel {
  
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Long categoryId;

  @Column( unique = true )
  private String name;

  @OneToMany(mappedBy = "categoria")
  private List<ProductModel> produtos;
}

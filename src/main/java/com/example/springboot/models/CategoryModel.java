package com.example.springboot.models;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

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

  @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<ProductModel> produtos;
}

/*
  Tive um problema com ciclos durante a serialização quando fiz uma requisição para o endPoint de
  associação de produtos a uma categoria.

  Solução: @JsonIgnore anotação usada em um dos lados da referencia bidirecionais para evitar o loop.
  Existem outas soluções também.
 */


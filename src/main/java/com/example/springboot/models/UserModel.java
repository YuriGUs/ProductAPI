package com.example.springboot.models;

import com.example.springboot.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TB_USER")
public class UserModel {
  
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Long idUser;

  private String name;
  
  @Column( unique = true )
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private UserRole role;
}

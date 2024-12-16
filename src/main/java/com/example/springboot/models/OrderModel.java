package com.example.springboot.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.springboot.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table( name = "TB_ORDER" )
public class OrderModel {
  
  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Long idOrder;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private BigDecimal total_price;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItemModel> orderItems;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserModel userModel;

  // Relação com order item

  private LocalDateTime createdAt;
}

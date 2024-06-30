package com.amar.onlinestore;



import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;




@Entity
@Table(name = "cart_item")
class CartItem {
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
 private Integer id;
 @PositiveOrZero(message = "quantity should be a positive number")
  private int quantity;
  private double total_cost;
  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart;

  public CartItem(int quantity, double total_cost, Cart cart) {
    this.quantity = quantity;
    this.total_cost = total_cost;
    this.cart = cart;
  }

  CartItem() {}

  public CartItem(int quantity, double total_cost) {
    this.quantity = quantity;
    this.total_cost = total_cost;
}

public Integer getCart_item_id() {
    return id;
}

public void setCart_item_id(Integer cart_item_id) {
    this.id = cart_item_id;
}

public int getQuantity() {
    return quantity;
}

public void setQuantity(int quantity) {
    this.quantity = quantity;
}

public double getTotal_cost() {
    return total_cost;
}

public void setTotal_cost(double total_cost) {
    this.total_cost = total_cost;
}



  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof CartItem))
      return false;
    CartItem cartitem = (CartItem) o;
    return Objects.equals(this.id, cartitem.id) && Objects.equals(this.quantity, cartitem.quantity)
        && Objects.equals(this.total_cost, cartitem.total_cost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.quantity, this.total_cost);
  }

  @Override
  public String toString() {
    return "Employee{" + "id=" + this.id + ", quantity='" + this.quantity + '\'' + ", total cost='" + this.total_cost + '\'' + '}';
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }
}

package com.amar.onlinestore;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;




@Entity
@Table(name = "cart")
class Cart {
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
  private Integer id;
  private double totalCost;
  @OneToOne(cascade = CascadeType.ALL)
  @JsonBackReference
  private Customer customer;
  @JsonManagedReference
  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
  private List<CartItem> cartitems;

  Cart() {}

  public Cart(double totalCost, Customer customer, ArrayList<CartItem> cartitems) {
    this.totalCost = totalCost;
    this.customer = customer;
    this.cartitems = cartitems;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public List<CartItem> getCartitems() {
    return cartitems;
  }

  public void setCartitems(List<CartItem> cartitems) {
    this.cartitems = cartitems;
  }

  public Cart(double totalCost) {
    this.totalCost = totalCost;
}



  public Integer getId() {
    return this.id;
  }

  

  public void setId(Integer id) {
    this.id = id;
  }

 

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Cart))
      return false;
    Cart cart = (Cart) o;
    return Objects.equals(this.id, cart.id) && Objects.equals(this.totalCost, cart.totalCost) && Objects.equals(this.customer, cart.customer) && Objects.equals(this.cartitems, cart.cartitems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.totalCost,this.customer,this.cartitems);
  }

  @Override
  public String toString() {
    StringBuilder b=new StringBuilder();
    for(int i=0;i<this.cartitems.size();++i){
b.append(this.cartitems.get(i).toString());
if(i!=this.cartitems.size()-1)
b.append(",");
    }
return "Cart{" + "id=" + this.id + ", total cost='" + this.totalCost +'\''+", Cart items["+b+']'+'}';
}

public double getTotalCost() {
    return totalCost;
}

public void setTotalCost(double totalCost) {
    this.totalCost = totalCost;
}
}

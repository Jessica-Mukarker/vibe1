package com.amar.onlinestore;




import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
@Table(name = "customer")
class Customer {

  private  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
  Integer id;
  @NotNull
  @Size(min=2, max=30, message="First Name should have at least be 2 characters")
  private String firstName;
  @Size(min=2, message="Last Name should have at least be 2 characters")
  private String lastName;
  @Email(message = "Invalid email address")
  private String emailAddress;
  private String phoneNumber;
  private String username;
  @JsonManagedReference
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private List<Payment> payment;
  @JsonManagedReference
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
  private Cart cart;
  @JsonManagedReference
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private List<Order> orders;
  

  

  public Customer(
        @NotNull @Size(min = 2, max = 30, message = "First Name should have at least be 2 characters") String firstName,
        @Size(min = 2, message = "Last Name should have at least be 2 characters") String lastName,
        @Email(message = "Invalid email address") String emailAddress, String phoneNumber, String username,
        List<Payment> payment, Cart cart, List<Order> orders) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
    this.username = username;
    this.payment = payment;
    this.cart = cart;
    this.orders = orders;
}



public List<Order> getOrders() {
    return orders;
}

public void setOrders(List<Order> orders) {
    this.orders = orders;
}

public Customer(String firstName, String lastName, String emailAddress, String phoneNumber,
        String username) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
    this.username = username;
}

Customer() {}


 

  public Integer getid() {
    return id;
}

public void setid(Integer customer_id) {
    this.id = customer_id;
}

public String getFirstName() {
    return firstName;
}

public void setFirstName(String firstName) {
    this.firstName = firstName;
}

public String getLastName() {
    return lastName;
}

public void setLastName(String lastName) {
    this.lastName = lastName;
}

public String getEmailAddress() {
    return emailAddress;
}

public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
}

public String getPhoneNumber() {
    return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
}



public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}

@Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Customer))
      return false;
    Customer customer = (Customer) o;
    return Objects.equals(this.id, customer.id) && Objects.equals(this.firstName, customer.firstName)
        && Objects.equals(this.lastName, customer.lastName)  && Objects.equals(this.phoneNumber, customer.phoneNumber)
         && Objects.equals(this.emailAddress, customer.emailAddress)
        && Objects.equals(this.username, customer.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.firstName, this.lastName,this.emailAddress,this.phoneNumber,this.username);
  }

  @Override
  public String toString() {
    return "Customer{" + "id=" + this.id + ", first name='" + this.firstName + '\'' + ", last name='" + this.lastName + '\''+", email='"+this.emailAddress+'\''+", phone='"+this.phoneNumber+'\''+", username='"+this.username+'}';
  }

public List<Payment> getPayment() {
    return payment;
}

public void setPayment(List<Payment> payment) {
    this.payment = payment;
}

public Cart getCart() {
    return cart;
}

public void setCart(Cart cart) {
    this.cart = cart;
}





  
}

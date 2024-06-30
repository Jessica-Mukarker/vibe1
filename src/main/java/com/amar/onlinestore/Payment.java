package com.amar.onlinestore;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name = "payment")
public class Payment{
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
  private Integer id;
  private Double amount;
      private PaymentType type; 
      @JsonBackReference
      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "customer_id", nullable = false)
      private Customer customer;
  
    Payment() {}
  
    public Payment(Double amount,PaymentType type){
      this.amount=amount;
      this.type=type;
      }
  
    
  
    public int getId() {
      return id;
  }
  
  
  
  public Double getAmount() {
      return amount;
  }
  
  public void setAmount(Double amount) {
      this.amount = amount;
  }
  
 
  
  public PaymentType getType() {
      return type;
  }
  
  public void setType(PaymentType type) {
      this.type = type;
  }
  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Payment))
      return false;
    Payment payment = (Payment) o;
    return Objects.equals(this.id, payment.id) && Objects.equals(this.amount, payment.amount) && Objects.equals(this.type, payment.type) && Objects.equals(this.customer, payment.customer);
       
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.amount,this.type,this.customer);
  }

  @Override
  public String toString() {
    return "Payment{" + "id=" + this.id + ", amount=" + this.amount +'\'' +", type='"+this.type+'}';
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
  }

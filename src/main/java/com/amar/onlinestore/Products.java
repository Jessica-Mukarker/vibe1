



package com.amar.onlinestore;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;


@Entity
@Table(name = "products")
class Products {
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private Integer id;

    @Size(min = 2, max = 500, message = "Name should have at least 2 characters")
    private String name;

    private String description;

    private double price;

    private Integer category_id; // New field to store category ID

    @ElementCollection
    private List<String> colors;

    private String brand;

    private String rating;

    private String productType;

    @ElementCollection
    private List<String> tagList;

    @Lob
    private byte[] image;

    public Products(String name, String description, double price, Integer category_id,
                    List<String> colors, String brand, String rating, String productType, List<String> tagList, byte[] image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category_id = category_id;
        this.colors = colors;
        this.brand = brand;
        this.rating = rating;
        this.productType = productType;
        this.tagList = tagList;
        this.image = image;
    }





public Integer getId() {
  return id;
}




public void setId(Integer id) {
  this.id = id;
}




public List<String> getColors() {
  return colors;
}




public void setColors(List<String> colors) {
  this.colors = colors;
}




public String getBrand() {
  return brand;
}




public void setBrand(String brand) {
  this.brand = brand;
}




public String getRating() {
  return rating;
}




public void setRating(String rating) {
  this.rating = rating;
}




public String getProductType() {
  return productType;
}




public void setProductType(String productType) {
  this.productType = productType;
}




public List<String> getTagList() {
  return tagList;
}




public void setTagList(List<String> tagList) {
  this.tagList = tagList;
}




public byte[] getImage() {
  return image;
}




public void setImage(byte[] image) {
  this.image = image;
}




public Integer getProduct_id() {
    return id;
}




public void setProduct_id(Integer product_id) {
    this.id = product_id;
}




public String getName() {
    return name;
}




public void setName(String name) {
    this.name = name;
}




public String getDescription() {
    return description;
}




public void setDescription(String description) {
    this.description = description;
}









public double getPrice() {
    return price;
}




public void setPrice(double price) {
    this.price = price;
}




Products() {}


 

  

@Override
public boolean equals(Object o) {
    if (this == o)
        return true;
    if (!(o instanceof Products))
        return false;
    Products product = (Products) o;
    return Objects.equals(this.id, product.id) &&
            Objects.equals(this.name, product.name) &&
            Objects.equals(this.description, product.description) &&
            Objects.equals(this.price, product.price) &&
            Objects.equals(this.colors, product.colors) &&
            Objects.equals(this.brand, product.brand) &&
            Objects.equals(this.rating, product.rating) &&
            Objects.equals(this.productType, product.productType) &&
            Objects.equals(this.tagList, product.tagList) &&
            Arrays.equals(this.image, product.image);
}


@Override
public int hashCode() {
    return Objects.hash(this.id, this.name, this.description, this.price, this.colors, this.brand, this.rating, this.productType, this.tagList, Arrays.hashCode(this.image));
}


@Override
public String toString() {
    return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", price=" + price +
            ", colors=" + colors +
            ", brand='" + brand + '\'' +
            ", rating=" + rating +
            ", productType='" + productType + '\'' +
            ", tagList=" + tagList +
            ", image=" + Arrays.toString(image) +
            '}';
}





public Integer getCategory_id() {
  return category_id;
}





public void setCategory_id(Integer category_id) {
  this.category_id = category_id;
}





  


}
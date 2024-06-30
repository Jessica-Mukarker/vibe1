package com.amar.onlinestore;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
@Table(name = "category")
class Category {
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    private Integer category_id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "category_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Products> products;
    

    @Column(length = 100000)
    private byte[] image; // Field to hold the image as byte array

    public Category(String name, ArrayList<Products> products) {
        this.name = name;
        this.products = products;
    }

    public Category() {}

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Category))
            return false;
        Category category = (Category) o;
        return Objects.equals(category_id, category.category_id) &&
                Objects.equals(name, category.name) &&
                Objects.equals(products, category.products) &&
                Objects.equals(image, category.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category_id, name, products, image);
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", name='" + name + '\'' +
                ", products=" + products +
                ", image='" + image + '\'' +
                '}';
    }
}

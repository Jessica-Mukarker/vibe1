package com.amar.onlinestore;



import org.springframework.data.jpa.repository.JpaRepository;

interface ProductsRepository extends JpaRepository<Products, Integer> {

}

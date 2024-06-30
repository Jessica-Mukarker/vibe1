package com.amar.onlinestore;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByName(String name);
    void deleteByName(String name);
}

interface CategoryRepository2 extends JpaRepository<Category, Integer> {
   
}



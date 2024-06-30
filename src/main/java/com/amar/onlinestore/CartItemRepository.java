package com.amar.onlinestore;


import org.springframework.data.jpa.repository.JpaRepository;

interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}

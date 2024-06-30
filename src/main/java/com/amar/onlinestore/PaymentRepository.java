package com.amar.onlinestore;



import org.springframework.data.jpa.repository.JpaRepository;

interface PaymentRepository extends JpaRepository<Payment, Integer> {

}

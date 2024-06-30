package com.amar.onlinestore.springjwt.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.amar.onlinestore.springjwt.models.ERole;
import com.amar.onlinestore.springjwt.models.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}

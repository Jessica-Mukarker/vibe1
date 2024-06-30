package com.amar.onlinestore.springjwt.controllers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.amar.onlinestore.springjwt.models.User;
@Component
public class UsersAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User user) {
  
      return EntityModel.of(user, //
          linkTo(methodOn(UsersController.class).oneUser(user.getUsername())).withSelfRel(),
          linkTo(methodOn(UsersController.class).allUsers()).withRel("allCustomers"));
    }
}


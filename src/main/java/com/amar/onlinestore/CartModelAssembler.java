package com.amar.onlinestore;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class CartModelAssembler implements RepresentationModelAssembler<Cart, EntityModel<Cart>> {

  @Override
  public EntityModel<Cart> toModel(Cart cart) {

    return EntityModel.of(cart, //
        linkTo(methodOn(CustomerController.class).one(cart.getId())).withSelfRel(),
        linkTo(methodOn(CustomerController.class).all()).withRel("carts"));
  }
}

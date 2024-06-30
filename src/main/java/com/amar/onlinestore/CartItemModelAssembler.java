package com.amar.onlinestore;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class CartItemModelAssembler implements RepresentationModelAssembler<CartItem, EntityModel<CartItem>> {

  @Override
  public EntityModel<CartItem> toModel(CartItem cartitem) {

    return EntityModel.of(cartitem, //
        linkTo(methodOn(CustomerController.class).one(cartitem.getCart_item_id())).withSelfRel(),
        linkTo(methodOn(CustomerController.class).all()).withRel("cartitems"));
  }
}

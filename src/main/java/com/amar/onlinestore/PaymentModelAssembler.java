package com.amar.onlinestore;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class PaymentModelAssembler implements RepresentationModelAssembler<Payment, EntityModel<Payment>> {

  @Override
  public EntityModel<Payment> toModel(Payment payment) {

    return EntityModel.of(payment, //
        linkTo(methodOn(CustomerController.class).one(payment.getId())).withSelfRel(),
        linkTo(methodOn(CustomerController.class).all()).withRel("payments"));
  }
}

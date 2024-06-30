package com.amar.onlinestore.springjwt.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amar.onlinestore.springjwt.models.User;
import com.amar.onlinestore.springjwt.repository.UserRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UsersController {
    private final UsersAssembler assembler;
    private final UserRepository repository;

    public UsersController(UserRepository repository, UsersAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;

    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> allUsers() {
        List<EntityModel<User>> users = ((Collection<User>) repository.findAll())
                .stream().map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UsersController.class).allUsers()).withSelfRel());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{userName}")
    EntityModel<User> oneUser(@PathVariable String userName) {

        User user = repository.findByUsername(userName) //
                .orElseThrow(() -> new UsersNotFoundExecption(userName));

        return assembler.toModel(user);

    }




   
  /*  @PutMapping("customers/{customerId}")
    public ResponseEntity<?> replaceCustomer(@Valid @RequestBody Customers newCustomer, @PathVariable Long customerId) {
        Customers updatedCustomers = repository.findById(customerId).map(
                customer -> {
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    customer.setEmail(newCustomer.getEmail());
                    customer.setPhone(newCustomer.getPhone());
                    customer.setShippingAddress(newCustomer.getShippingAddress());
                    customer.setPaymentInfo(newCustomer.getPaymentInfo());
                    return repository.save(customer);
                }).orElseGet(() -> {
                    newCustomer.setCostomerId(customerId);
                    return repository.save(newCustomer);
                });
                EntityModel<Customers> entityModel = assembler.toModel(updatedCustomers);
                return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    
    
}*/



}

package com.amar.onlinestore;



import java.net.http.HttpHeaders;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
class CustomerController {

    private final CustomerRepository repository;
    private final CartRepository repository1;
    private final OrderRepository repository2;
    private final PaymentRepository repository3;
    private final CartItemRepository repository4;
    private final CustomerModelAssembler assembler;
    private final CartModelAssembler assemblercart;
    private final PaymentModelAssembler assemblerpayment;
    private final CartItemModelAssembler assemblercartitem;
    private final  OrderModelAssembler assemblerorder;
   
    
   





    

    


  public CustomerController(CustomerRepository repository,PaymentRepository repository3,CartItemRepository repository4,CartRepository repository1,OrderRepository repository2,CustomerModelAssembler assembler, CartModelAssembler assemblercart,
            PaymentModelAssembler assemblerpayment, CartItemModelAssembler assemblercartitem,OrderModelAssembler assemblerorder) {
        this.repository = repository;
        this.repository1=repository1;
        this.repository2=repository2;
        this.repository3=repository3;
        this.repository4=repository4;
        this.assembler = assembler;
        this.assemblercart = assemblercart;
        this.assemblerpayment = assemblerpayment;
        this.assemblercartitem = assemblercartitem;
        this.assemblerorder = assemblerorder;
    }

// Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/customers")
  CollectionModel<EntityModel<Customer>> all() {
    
  List<EntityModel<Customer>> customers = repository.findAll().stream() //
  .map(assembler::toModel) //
  .collect(Collectors.toList());

return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
  }

  @GetMapping("/carts")
  CollectionModel<EntityModel<Cart>> allcarts() {
    
  List<EntityModel<Cart>> carts = repository1.findAll().stream() //
  .map(assemblercart::toModel) //
  .collect(Collectors.toList());

return CollectionModel.of(carts, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
  }

  @GetMapping("/cartitems")
  CollectionModel<EntityModel<CartItem>> allCartItems() {
    
  List<EntityModel<CartItem>> cartitems = repository4.findAll().stream() //
  .map(assemblercartitem::toModel) //
  .collect(Collectors.toList());

return CollectionModel.of(cartitems, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
  }
  // end::get-aggregate-root[]

  @PostMapping("/customers")
  ResponseEntity<?> newCustomer(@Valid @RequestBody Customer newCustomer) {
    Cart cart=new Cart();
    cart.setCustomer(newCustomer);
    newCustomer.setCart(cart);
    EntityModel<Customer> entityModel = assembler.toModel(repository.save(newCustomer));

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }
  
 
  // Single item
  
  @GetMapping("/customers/{id}")
  EntityModel<Customer> one(@PathVariable Integer id) {
   
  Customer customer = repository.findById(id) //
  .orElseThrow(() -> new CustomerNotFoundException(id));

return assembler.toModel(customer);
  }

  @PutMapping("/carts/{id}")
  ResponseEntity<?> replaceCart(@Valid @RequestBody Cart newCart, @PathVariable Integer id) {
    
    Cart updatedCart =  repository1.findById(id)
      .map(cart -> {
        cart.setCartitems(newCart.getCartitems());
        cart.setTotalCost(newCart.getTotalCost());
        return repository1.save(cart);
      })
      .orElseGet(() -> {
        newCart.setId(id);
        return repository1.save(newCart);
      });

      EntityModel<Cart> entityModel = assemblercart.toModel(updatedCart);

  return ResponseEntity //
      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
      .body(entityModel);
  }
  @PutMapping("/customers/{id}")
  ResponseEntity<?> replaceCustomer(@Valid @RequestBody Customer newCustomer, @PathVariable Integer id) {
    
    Customer updatedCustomer =  repository.findById(id)
      .map(customer -> {
        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
        customer.setEmailAddress(newCustomer.getEmailAddress());
        customer.setPayment(newCustomer.getPayment());
        customer.setPhoneNumber(newCustomer.getPhoneNumber());
        customer.setUsername(newCustomer.getUsername());
        if(newCustomer.getCart()!=null){
          customer.setCart(newCustomer.getCart());
        }
        return repository.save(customer);
      })
      .orElseGet(() -> {
        newCustomer.setid(id);
        return repository.save(newCustomer);
      });

      EntityModel<Customer> entityModel = assembler.toModel(updatedCustomer);

  return ResponseEntity //
      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
      .body(entityModel);
  }

  @DeleteMapping("/customers/{id}")
  ResponseEntity<?> deleteCustomer(@PathVariable Integer id) {
    repository.deleteById(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/customers/{id}/cart")
  EntityModel<Cart> getCartForPost(@PathVariable Integer id) {
     Customer customer = repository.findById(id)
     .orElseThrow(() -> new CustomerNotFoundException(id));
     return assemblercart.toModel(customer.getCart());
  }

  @GetMapping("/customers/{id}/payments")
  CollectionModel<EntityModel<Payment>> getPaymentsForPost(@PathVariable Integer id) {
     Customer customer = repository.findById(id)
     .orElseThrow(() -> new CustomerNotFoundException(id));

     List<EntityModel<Payment>> payments = customer.getPayment().stream() //
     .map(assemblerpayment::toModel) //
     .collect(Collectors.toList());
     return CollectionModel.of(payments, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
  }

  @GetMapping("/customers/{id}/payments/{paymentid}")
  EntityModel<Payment> getPaymentsForPost(@PathVariable Integer id,@PathVariable Integer paymentid) {
     Payment payment = repository3.findById(paymentid)
     .orElseThrow(() -> new CustomerNotFoundException(id));

     return assemblerpayment.toModel(payment);
  }

  @GetMapping("/customers/{id}/cart/cartitems")
  CollectionModel<EntityModel<CartItem>> getCartItemsForPost(@PathVariable Integer id) {
     Customer customer = repository.findById(id)
     .orElseThrow(() -> new CustomerNotFoundException(id));
     Cart cart=customer.getCart();

     List<EntityModel<CartItem>> cartitems = cart.getCartitems().stream() //
     .map(assemblercartitem::toModel) //
     .collect(Collectors.toList());
     return CollectionModel.of(cartitems, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    
  }

  @PostMapping("/customers/{id}/payments")
  ResponseEntity<?> newPayment(@RequestBody Payment newPayment,@PathVariable Integer id) {
    Customer customer=repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    newPayment.setCustomer(customer); 
    repository3.save(newPayment);
    customer.getPayment().add(newPayment);

    EntityModel<Payment> entityModel = assemblerpayment.toModel(newPayment);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
   
  }

  @PostMapping("/customers/{id}/cartitems")
  ResponseEntity<?> newCartItem(@Valid @RequestBody CartItem newCartItem,@PathVariable Integer id) {
    Customer customer=repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        repository4.save(newCartItem); 
    customer.getCart().getCartitems().add(newCartItem);

    EntityModel<CartItem> entityModel = assemblercartitem.toModel(newCartItem);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
   
  }

  @PostMapping("/customers/{id}/cart/cartitems")
  ResponseEntity<?> newCartIteminCart(@Valid @RequestBody CartItem newCartItem,@PathVariable Integer id) {
    Customer customer=repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    newCartItem.setCart(customer.getCart());
    repository4.save(newCartItem); 
    customer.getCart().getCartitems().add(newCartItem);

    EntityModel<CartItem> entityModel = assemblercartitem.toModel(newCartItem);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
   
  }

  @DeleteMapping("/customers/{id}/payments/{paymentid}")
  ResponseEntity<?> deletepayment(@PathVariable Integer id,@PathVariable Integer paymentid) {
    Customer customer=repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    for(int i=0;i<customer.getPayment().size();++i){
        if(customer.getPayment().get(i).getId()==paymentid){
            customer.getPayment().remove(i);
        }
        
    }

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/customers/{id}/cart/cartitems/{cartitemid}")
  ResponseEntity<?> deleteCartitem(@PathVariable Integer id,@PathVariable Integer cartitemid) {
    repository4.deleteById(cartitemid);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("cartitems/{cartitemid}")
  ResponseEntity<?> deleteCartitem(@PathVariable Integer cartitemid) {
    repository4.deleteById(cartitemid);

    return ResponseEntity.noContent().build();
  }


  @DeleteMapping("cart/{cartid}")
  ResponseEntity<?> deleteCart(@PathVariable Integer cartid) {
    repository1.deleteById(cartid);

    return ResponseEntity.noContent().build();
  }
  

  @PostMapping("/carts")
  ResponseEntity<?> newCart(@Valid @RequestBody Cart newCart) {
    EntityModel<Cart> entityModel = assemblercart.toModel(repository1.save(newCart));

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  @GetMapping("/customers/{id}/orders")
  CollectionModel<EntityModel<Order>> getOrdersForPost(@PathVariable Integer id) {
     Customer customer = repository.findById(id)
     .orElseThrow(() -> new CustomerNotFoundException(id));

     List<EntityModel<Order>> orders = customer.getOrders().stream() //
     .map(assemblerorder::toModel) //
     .collect(Collectors.toList());
     return CollectionModel.of(orders, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
  }


@PostMapping("/customers/{id}/orders")
  ResponseEntity<?> newOrder(@RequestBody Order newOrder,@PathVariable Integer id) {
    Customer customer=repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    newOrder.setStatus(Status.IN_PROGRESS);
    newOrder.setCustomer(customer);
    repository2.save(newOrder);
    customer.getOrders().add(newOrder);

    EntityModel<Order> entityModel = assemblerorder.toModel(newOrder);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
   
  }


  @DeleteMapping("/customers/{id}/orders/{orderid}")
  ResponseEntity<?> deleteOrder(@PathVariable Integer id,@PathVariable Integer orderid) {
    repository2.deleteById(orderid);

    return ResponseEntity.noContent().build();
  }


  @PutMapping("customers/{id}/orders/{orderid}/complete")
ResponseEntity<?> complete(@PathVariable Integer orderid) {
  Order order = repository2.findById(orderid) //
      .orElseThrow(() -> new OrderNotFoundException(orderid));

  if (order.getStatus() == Status.IN_PROGRESS) {
    order.setStatus(Status.COMPLETED);
    return ResponseEntity.ok(assemblerorder.toModel(repository2.save(order)));
  }

  return ResponseEntity //
      .status(HttpStatus.METHOD_NOT_ALLOWED) //
      .header(MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
      .body(Problem.create() //
          .withTitle("Method not allowed") //
          .withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
}


@DeleteMapping("customers/{id}/orders/{orderid}/cancel")
ResponseEntity<?> cancel(@PathVariable Integer orderid) {

  Order order = repository2.findById(orderid) //
      .orElseThrow(() -> new OrderNotFoundException(orderid));

  if (order.getStatus() == Status.IN_PROGRESS) {
    order.setStatus(Status.CANCELLED);
    return ResponseEntity.ok(assemblerorder.toModel(repository2.save(order)));
  }

  return ResponseEntity //
      .status(HttpStatus.METHOD_NOT_ALLOWED) //
      .header(MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
      .body(Problem.create() //
          .withTitle("Method not allowed") //
          .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
}
}

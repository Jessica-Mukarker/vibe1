package com.amar.onlinestore;


class CustomerNotFoundException extends RuntimeException {

  CustomerNotFoundException(Integer id) {
    super("Could not find customer " + id);
  }
}

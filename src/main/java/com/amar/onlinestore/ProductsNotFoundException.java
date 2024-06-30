package com.amar.onlinestore;


class ProductsNotFoundException extends RuntimeException {

  ProductsNotFoundException(Integer id) {
    super("Could not find product " + id);
  }
}

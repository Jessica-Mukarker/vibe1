package com.amar.onlinestore;



class CategoryNotFoundException extends RuntimeException {

  CategoryNotFoundException(String name) {
    super("Could not find category " + name);
  }

  
}

package com.amar.onlinestore;




class newCategoryNotFoundException extends RuntimeException {


  newCategoryNotFoundException() {
    super("Could not find category ");
  }
}

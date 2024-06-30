package com.amar.onlinestore.springjwt.controllers;


public class UsersNotFoundExecption extends RuntimeException{
    UsersNotFoundExecption(String username){
        super("could not find user "+username);
    }
}
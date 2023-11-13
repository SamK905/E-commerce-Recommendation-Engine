package com.syam.ecommerce.exception;

public class UserNotFoundException extends RuntimeException {

    private String message;

    public UserNotFoundException(String message) {
        this.message = message;
    }
}

package com.syam.ecommerce.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public String message;

    public UsernameAlreadyExistsException(String s) {
    }

    @Override
    public String getMessage() {
        return message;
    }

}

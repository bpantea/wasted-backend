package com.wasted.backend.core.user.api.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String msg){
        super(msg);
    }
}

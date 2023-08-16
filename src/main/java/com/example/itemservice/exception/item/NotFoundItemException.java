package com.example.itemservice.exception.item;

public class NotFoundItemException extends RuntimeException {

    public NotFoundItemException() {
        this("Not Found Item");
    }

    public NotFoundItemException(String message) {
        super(message);
    }
}

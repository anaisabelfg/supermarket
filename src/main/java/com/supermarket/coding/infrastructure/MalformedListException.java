package com.supermarket.coding.infrastructure;

public class MalformedListException extends RuntimeException {
    public MalformedListException(Exception e) {
        super(e);
    }
}

package com.akshatapp.restaurant.exeptions;

public class Storageexeption extends BaseException {
    public Storageexeption() {
        super();
    }

    public Storageexeption(String message) {
        super(message);
    }

    public Storageexeption(String message, Throwable cause) {
        super(message, cause);
    }
}

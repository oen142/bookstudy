package com.jpastudy.bookstudy.exception;

public class NotiEnoughStockException extends RuntimeException {
    public NotiEnoughStockException() {
        super();
    }

    public NotiEnoughStockException(String message) {
        super(message);
    }

    public NotiEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotiEnoughStockException(Throwable cause) {
        super(cause);
    }

}


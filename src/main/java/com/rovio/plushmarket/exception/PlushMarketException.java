package com.rovio.plushmarket.exception;

public class PlushMarketException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PlushMarketException(String message) {
        super(message);
    }

    public PlushMarketException(String message, Throwable cause) {
        super(message, cause);
    }
}

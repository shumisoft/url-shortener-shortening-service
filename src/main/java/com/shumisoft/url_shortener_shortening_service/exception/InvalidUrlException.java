package com.shumisoft.url_shortener_shortening_service.exception;

public class InvalidUrlException extends RuntimeException {

    public InvalidUrlException(String msg) {
        super(msg);
    }
}

package com.foltan.rentalCarTestApp.exception;

public class NoCreditCardException extends RuntimeException {

        public NoCreditCardException(String message) {
                super(message);
        }

}

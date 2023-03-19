package com.foltan.rentalCarTestApp.exception;

public class ExistingOrderException extends RuntimeException {

        public ExistingOrderException(String message) {
                super(message);
        }

}

package com.boolabs.sample.exception;

public class QRCodeGeneratorException extends RuntimeException {

    public QRCodeGeneratorException() {
    }

    public QRCodeGeneratorException(String message) {
        super(message);
    }

    public QRCodeGeneratorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

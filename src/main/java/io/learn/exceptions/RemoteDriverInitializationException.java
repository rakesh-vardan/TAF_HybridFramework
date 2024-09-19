package io.learn.exceptions;

public class RemoteDriverInitializationException extends RuntimeException {

    public RemoteDriverInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}

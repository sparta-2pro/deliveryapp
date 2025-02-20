package com.twopro.deliveryapp.ai.exception;

public class InvalidChatGptResponseException extends RuntimeException {

    public InvalidChatGptResponseException(String message) {
        super(message);
    }
}

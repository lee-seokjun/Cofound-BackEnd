package com.cofound.member.exception;

public class NotFoundMemberException extends RuntimeException{
    public NotFoundMemberException() {
    }

    public NotFoundMemberException(String message) {
        super(message);
    }
}
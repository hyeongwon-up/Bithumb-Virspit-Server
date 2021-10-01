package com.virspit.virspitauth.error.exception;

public class LoginRunnerException extends RuntimeException{
    private static final long SerializableUID = 1L;

    public LoginRunnerException() {
        super();
    }

    public LoginRunnerException(String msg) {
        super(msg);
    }
}
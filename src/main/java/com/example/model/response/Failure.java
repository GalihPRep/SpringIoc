package com.example.model.response;

public class Failure extends Common {

    public Failure(String status, String code, String message) {
        super.setStatus(status);
        super.setCode(code);
        super.setMessage(message);
    }
}

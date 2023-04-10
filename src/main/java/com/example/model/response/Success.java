package com.example.model.response;

import org.springframework.http.HttpStatus;

public class Success<T> extends Common {

    public Success(String message, T data) {
        super.setStatus(HttpStatus.OK.name());
        super.setCode("200");
        super.setMessage(message);
        this.data = data;
    }

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}

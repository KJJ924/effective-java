package com.jaejoon.demo.item75;

/**
 * @author dkansk924@naver.com
 * @since 2021/07/17
 */
public class ErrorResponse {
    private final String message;
    private final String fieldName;
    private final String inputValue;

    public ErrorResponse(String message, String fieldName, String inputValue) {
        this.message = message;
        this.fieldName = fieldName;
        this.inputValue = inputValue;
    }

    @Override
    public String toString() {
        return
            "message='" + message + '\'' +
            ", fieldName='" + fieldName + '\'' +
            ", inputValue='" + inputValue + '\'' ;
    }
}

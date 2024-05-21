package com.javaguides.todomanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class TodoAPIException extends Exception {
    private HttpStatus status;
    private String message;
}

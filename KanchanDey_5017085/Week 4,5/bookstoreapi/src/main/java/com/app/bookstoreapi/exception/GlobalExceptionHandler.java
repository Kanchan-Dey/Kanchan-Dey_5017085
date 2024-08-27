package com.app.bookstoreapi.exception;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> handleGlobalException(Exception ex){
        HttpHeaders headers=new HttpHeaders();
        headers.add("Internal-Error-Header","Server");
        String errorMessage="An error occured: "+ex.getMessage();
        return new ResponseEntity<>(errorMessage,headers,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleInvalidInputException(MethodArgumentTypeMismatchException ex){
        HttpHeaders headers=new HttpHeaders();
        headers.add("Invalid-Input","Provide-Correct-Input");
        String errorMessage="Invalid Input: "+ex.getMessage();
        return new ResponseEntity<>(errorMessage,headers,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Validation-Error", "Invalid input data");

        // You can customize the error response here
        String errorMessage = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(errorMessage, headers, HttpStatus.BAD_REQUEST);
    }
}

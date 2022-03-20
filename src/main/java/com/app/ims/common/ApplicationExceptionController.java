package com.app.ims.common;


import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApplicationExceptionController  {

    @ExceptionHandler(EntityNotFoundException.class)
    private final ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception){
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(exception.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), " ",errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errorDetails = new ArrayList<>();
        for(ObjectError error: ex.getBindingResult().getAllErrors()){
            errorDetails.add(error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse("Validation failed", "Pls follow form instructions before submit", errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private final ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception){
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(exception.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), " ",errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    private final ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception){
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(exception.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), "",errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private final ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(exception.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), " ",errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private final ResponseEntity<ErrorResponse> handleTransactionSystemException(ConstraintViolationException exception){
        final List<String> errorDetails = new ArrayList<>();
        for (final ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            errorDetails.add(violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse("Validation failed", "Pls follow form instructions before submit" ,errorDetails);
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    private final ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException exception){
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(exception.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), " ",errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    private final ResponseEntity<ErrorResponse> handleUnexpectedTypeException(UnexpectedTypeException exception){
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(exception.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), " ",errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    private final ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException exception){
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(exception.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), " ",errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MalformedJwtException.class)
    private final ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException exception){
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(exception.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), " ",errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    private final ResponseEntity<ErrorResponse> handleGlobalException(Exception exception, WebRequest request){
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(exception.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), " ",errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

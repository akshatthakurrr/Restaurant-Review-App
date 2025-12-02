package com.akshatapp.restaurant.controllers;


import com.akshatapp.restaurant.domain.dtos.ErrorDto;
import com.akshatapp.restaurant.exeptions.BaseException;
import com.akshatapp.restaurant.exeptions.RestaurantNotFoundException;
import com.akshatapp.restaurant.exeptions.ReviewNotAllowedException;
import com.akshatapp.restaurant.exeptions.Storageexeption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@Configuration
@Slf4j
public class ErrorController {
    @ExceptionHandler(ReviewNotAllowedException.class)
    public ResponseEntity<ErrorDto> handleReviewNotAllowedException(ReviewNotAllowedException ex){
        log.error("Caught ReviewNotAllowedException ", ex);
        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("The specified review cannot be created or updated")
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorDto> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
        log.error("Caught RestaurantNotFoundException", ex);

        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("the specified restaurant not found")
                .build();

        return  new ResponseEntity<>(errorDto,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation error", ex);
        String errorMessage = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed: " + errorMessage)
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Storageexeption.class)
    public ResponseEntity<ErrorDto>handleStorageExeption(Storageexeption ex){
        log.error("caught storage exception");
        ErrorDto error = ErrorDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDto> handleException(BaseException ex) {
        log.error("Caught unexpected exception", ex);
        ErrorDto error = ErrorDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

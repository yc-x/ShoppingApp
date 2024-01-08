package org.example.shoppingapp.aop;

import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.exception.NotEnoughInventoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
//@RestControllerAdvice
public class MyExceptionHandler {

//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<DataResponse> handleException(Exception e){
//        return new ResponseEntity<DataResponse>(DataResponse.builder()
//                .message(e.getMessage())
//                .build(),
//                HttpStatus.OK);
//    }

    @ExceptionHandler(value = {NotEnoughInventoryException.class})
    public ResponseEntity<DataResponse> handleDemoNotFoundException(NotEnoughInventoryException e){
        return new ResponseEntity<DataResponse>(DataResponse.builder()
                .message(e.getMessage())
                .success(false)
                .build(),
                HttpStatus.OK);
    }
}

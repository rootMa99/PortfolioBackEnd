package com.portfolio.appPortfolio.exception;

import com.portfolio.appPortfolio.ui.model.rest.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(ServiceException ex, WebRequest req){

        ErrorMessage errorMessage= new ErrorMessage(new Date(), ex.getMessage() );

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object>handleOtherExceptions(Exception ex, WebRequest req){

        ErrorMessage errorMessage= new ErrorMessage(new Date(), ex.getMessage() );

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

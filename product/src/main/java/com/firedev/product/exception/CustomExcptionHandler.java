package com.firedev.product.exception;

import com.firedev.product.dto.APIError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExcptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({OfferNotValidException.class,CurrencyNotValidException.class})
    ResponseEntity<?> offerNotValidHandler(Exception exc, ServletWebRequest request) {
        APIError error = new APIError();
        error.setTimeStamp(LocalDateTime.now());
        error.setPathUri(request.getDescription(false));
        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setErrors(Arrays.asList(exc.getClass() + " : " + exc.getMessage()));
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<String> errors = fieldErrors.stream().
                map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        APIError error = new APIError();
        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setTimeStamp(LocalDateTime.now());
        error.setPathUri(request.getDescription(false));
        error.setErrors(errors);

        return new ResponseEntity<>(error, headers, error.getStatus());
    }
}

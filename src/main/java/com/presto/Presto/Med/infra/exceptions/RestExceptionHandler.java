package com.presto.Presto.Med.infra.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> handle404(EntityNotFoundException ex){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorMessage> handle404(IllegalArgumentException ex){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(apiErrorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = ex.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(errorMessages);
        return ResponseEntity.badRequest().body(apiErrorMessage);
    }

    @ExceptionHandler(DataAlreadyExists.class)
    public ResponseEntity handleDataAlreadyExists(DataAlreadyExists ex){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(apiErrorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        String errorMsg = ex.getMostSpecificCause().getMessage();
        String message;

        if (errorMsg.contains("Id")) {
            message = "invalid id format.";
        }else if(errorMsg.contains("could not be parsed")){
            message = "date format must be: dd-MM-yyyy HH:mm";
        }else{
            message = "invalid json.";
        }

        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(message);

        return ResponseEntity.badRequest().body(apiErrorMessage);
    }

    @ExceptionHandler(InvalidScheduleException.class)
    public ResponseEntity handleInvalidSchedule(InvalidScheduleException ex){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(apiErrorMessage);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity handleInvalidToken(InvalidTokenException ex){
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(apiErrorMessage);
    }
}


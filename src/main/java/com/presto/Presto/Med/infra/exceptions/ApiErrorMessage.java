package com.presto.Presto.Med.infra.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ApiErrorMessage {

    private List<String> errors;

    public ApiErrorMessage(List<String> errors) {
        super();
        this.errors = errors;
    }

    public ApiErrorMessage(String error) {
        super();
        errors = Arrays.asList(error);
    }
}

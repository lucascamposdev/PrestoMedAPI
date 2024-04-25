package com.presto.Presto.Med.infra.exceptions;

public class DataAlreadyExists extends RuntimeException{

    public DataAlreadyExists(String message){
        super(message);
    }
}

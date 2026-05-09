package com.projekt1.tournament_manager.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String poruka){
        super(poruka);
    }

}

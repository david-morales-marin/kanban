package com.example.kanban.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProjectUtils {

    private ProjectUtils(){}

    public static ResponseEntity<String> getResponseEntity(String message , HttpStatus httpStatus){
        return new ResponseEntity<String>("Mensaje : " , httpStatus);
    }
}

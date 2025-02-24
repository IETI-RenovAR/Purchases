package org.adaschool.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PurchaseNotFoundException extends RuntimeException {

    public PurchaseNotFoundException(String id) {
        super("Purchase with ID: " + id + " not found");
    }
}
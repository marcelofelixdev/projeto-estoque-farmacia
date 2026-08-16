package com.farma.estoque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseStatus diz ao Spring qual HTTP status esta exceção representa
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final String recurso;
    private final Long id;

    public ResourceNotFoundException(String recurso, Long id) {
        super(recurso + " não encontrado(a) com ID: " + id);
        this.recurso = recurso;
        this.id = id;
    }

    public String getRecurso() { return recurso; }
    public Long getId() { return id; }
}
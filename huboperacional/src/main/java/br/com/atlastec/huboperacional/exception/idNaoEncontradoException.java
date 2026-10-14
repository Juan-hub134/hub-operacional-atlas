package br.com.atlastec.huboperacional.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class idNaoEncontradoException extends RuntimeException {
    public idNaoEncontradoException(String message) {
        super(message);
    }
}

package com.farma.estoque.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> tratarErrosDeValidacao(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ErroPadrao erro = new ErroPadrao();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.BAD_REQUEST.value());
        erro.setTitulo("Erro de Validação dos Dados. Verique os campos abaixo.");
        erro.setCaminho(request.getRequestURI());

        List<ErroPadrao.CampoErro> listaDeErros = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult(). getFieldErrors()) {
            listaDeErros.add(new ErroPadrao.CampoErro(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        erro.setDetalhes(listaDeErros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroPadrao> tratarRegrasDeNegocio(
            RuntimeException ex,
            HttpServletRequest request) {

        ErroPadrao erro = new ErroPadrao();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.BAD_REQUEST.value());
        erro.setTitulo(ex.getMessage());
        erro.setCaminho(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}

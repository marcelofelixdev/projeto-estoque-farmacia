package com.farma.estoque.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    // ── 400 Bad Request: campos inválidos (@NotBlank, @Email, @Size...) ──────
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> tratarErrosDeValidacao(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ErroPadrao.CampoErro> listaDeErros = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            listaDeErros.add(new ErroPadrao.CampoErro(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        ErroPadrao erro = new ErroPadrao();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.BAD_REQUEST.value());
        erro.setTitulo("Dados inválidos. Verifique os campos abaixo e tente novamente.");
        erro.setCaminho(request.getRequestURI());
        erro.setDetalhes(listaDeErros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // ── 401 Unauthorized: login ou senha incorretos ───────────────────────────
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroPadrao> tratarCredenciaisInvalidas(
            BadCredentialsException ex,
            HttpServletRequest request) {

        ErroPadrao erro = new ErroPadrao();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.UNAUTHORIZED.value());
        erro.setTitulo("Login ou senha incorretos.");
        erro.setCaminho(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    // ── 404 Not Found: recurso não existe no banco ────────────────────────────
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroPadrao> tratarNaoEncontrado(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErroPadrao erro = new ErroPadrao();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.NOT_FOUND.value());
        erro.setTitulo(ex.getMessage());
        erro.setCaminho(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // ── 409 Conflict: duplicata (CNPJ, código de barras, login já existentes) ─
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErroPadrao> tratarConflito(
            ConflictException ex,
            HttpServletRequest request) {

        ErroPadrao erro = new ErroPadrao();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.CONFLICT.value());
        erro.setTitulo(ex.getMessage());
        erro.setCaminho(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    // ── Genérico para ResponseStatusException (ex: 409 do AutenticacaoService) ─
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroPadrao> tratarResponseStatus(
            ResponseStatusException ex,
            HttpServletRequest request) {

        ErroPadrao erro = new ErroPadrao();
        erro.setTimestamp(Instant.now());
        erro.setStatus(ex.getStatusCode().value());
        erro.setTitulo(ex.getReason());
        erro.setCaminho(request.getRequestURI());

        return ResponseEntity.status(ex.getStatusCode()).body(erro);
    }

    // ── 500 Internal Server Error: qualquer erro inesperado ──────────────────
    // IMPORTANTE: nunca exponha detalhes internos aqui (ex.getMessage() NÃO vai no body)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> tratarErroInesperado(
            Exception ex,
            HttpServletRequest request) {

        ErroPadrao erro = new ErroPadrao();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        erro.setTitulo("Ocorreu um erro interno. Tente novamente mais tarde.");
        erro.setCaminho(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
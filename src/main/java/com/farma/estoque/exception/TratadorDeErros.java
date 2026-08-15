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

<<<<<<< HEAD
    // Erros de validação de campos (@NotBlank, @Size, etc.)
    // Esses são seguros para retornar ao cliente — são mensagens que você definiu
=======
>>>>>>> b273284ef5b46a93ad22dd0efc1cba65a868ebb6
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> tratarErrosDeValidacao(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ErroPadrao erro = new ErroPadrao();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.BAD_REQUEST.value());
<<<<<<< HEAD
        erro.setTitulo("Erro de Validação dos Dados. Verifique os campos abaixo.");
        erro.setCaminho(request.getRequestURI());

        List<ErroPadrao.CampoErro> listaDeErros = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
=======
        erro.setTitulo("Erro de Validação dos Dados. Verique os campos abaixo.");
        erro.setCaminho(request.getRequestURI());

        List<ErroPadrao.CampoErro> listaDeErros = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult(). getFieldErrors()) {
>>>>>>> b273284ef5b46a93ad22dd0efc1cba65a868ebb6
            listaDeErros.add(new ErroPadrao.CampoErro(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        erro.setDetalhes(listaDeErros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

<<<<<<< HEAD
    // Erros de regra de negócio conhecidos (ex: "Fabricante não encontrado")
=======
>>>>>>> b273284ef5b46a93ad22dd0efc1cba65a868ebb6
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroPadrao> tratarRegrasDeNegocio(
            RuntimeException ex,
            HttpServletRequest request) {

        ErroPadrao erro = new ErroPadrao();
        erro.setTimestamp(Instant.now());
<<<<<<< HEAD
        erro.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        erro.setTitulo(ex.getMessage());
        erro.setCaminho(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

    // Captura genérica — qualquer Exception inesperada NÃO vaza detalhes internos
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
=======
        erro.setStatus(HttpStatus.BAD_REQUEST.value());
        erro.setTitulo(ex.getMessage());
        erro.setCaminho(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
>>>>>>> b273284ef5b46a93ad22dd0efc1cba65a868ebb6

package com.farma.estoque.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroPadrao {

    private Instant timestamp;
    private Integer status;
    private String titulo;
    private String caminho;

    private List<CampoErro> detalhes;

    @Getter
    @Setter
    public static class CampoErro {
        private String campo;
        private String mensagem;

        public CampoErro(String campo, String mensagem) {
            this.campo = campo;
            this.mensagem = mensagem;
        }
    }

}

package com.farma.estoque.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FabricanteRequestDTO {

    @NotBlank(message = "O nome do fabricante é obrigatório")
    private String nome;

    @CNPJ
    @NotBlank(message = "O CNPJ é obrigatório")
    private String cnpj;

    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

}

package com.farma.estoque.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Setter
public class FabricanteRequestDTO {

    @NotBlank(message = "O nome do fabricante é obrigatório")
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
    private String nome;

    @CNPJ
    @NotBlank(message = "O CNPJ é obrigatório")
    private String cnpj;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @NotBlank(message = "O email é obrigatório")
    @Email(
            regexp = "^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$",
            message = "O e-mail deve ser em um formato válido (ex: usuario@dominio.com)"
    )
    @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres")
    private String email;
}
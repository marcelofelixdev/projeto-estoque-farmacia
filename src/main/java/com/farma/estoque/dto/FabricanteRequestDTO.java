package com.farma.estoque.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
<<<<<<< HEAD
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
=======
import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Getter;
import lombok.Setter;
>>>>>>> b273284ef5b46a93ad22dd0efc1cba65a868ebb6

@Getter
@Setter
public class FabricanteRequestDTO {

    @NotBlank(message = "O nome do fabricante é obrigatório")
<<<<<<< HEAD
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
=======
>>>>>>> b273284ef5b46a93ad22dd0efc1cba65a868ebb6
    private String nome;

    @CNPJ
    @NotBlank(message = "O CNPJ é obrigatório")
    private String cnpj;

    @NotBlank(message = "O telefone é obrigatório")
<<<<<<< HEAD
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
=======
>>>>>>> b273284ef5b46a93ad22dd0efc1cba65a868ebb6
    private String telefone;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Formato de e-mail inválido")
<<<<<<< HEAD
    @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres")
    private String email;
}
=======
    private String email;

}
>>>>>>> b273284ef5b46a93ad22dd0efc1cba65a868ebb6

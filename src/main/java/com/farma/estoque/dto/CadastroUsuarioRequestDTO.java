package com.farma.estoque.dto;

import com.farma.estoque.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastroUsuarioRequestDTO(

        @NotBlank(message = "O login não pode estar em branco")
        @Email(
                regexp = "^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$",
                message = "O login deve ser um e-mail válido (ex: usuario@dominio.com)"
        )
        String login,

        @NotBlank(message = "A senha não pode estar em branco")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotNull(message = "O perfil (role) é obrigatório")
        UserRole role

) {}
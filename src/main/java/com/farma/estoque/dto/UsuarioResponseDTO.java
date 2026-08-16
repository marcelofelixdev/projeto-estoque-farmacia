package com.farma.estoque.dto;

import com.farma.estoque.model.UserRole;
import com.farma.estoque.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String login,
        UserRole role
) {
    // Construtor de conveniência: recebe uma entidade e extrai os campos
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin(), usuario.getRole());
    }
}
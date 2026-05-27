package com.farma.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicamentoRequestDTO {

    @NotBlank(message = "O nome do medicamento é obrigatório")
    private String nome;

    @NotBlank(message = "O princípio ativo é obrigatório")
    private String principioAtivo;

    @NotBlank(message = "A dosagem é obrigatória")
    private String dosagem;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve maior que zero")
    private Double preco;

    @NotNull(message = "O ID do fabricante é obrigatório")
    private Long fabricanteId;

}

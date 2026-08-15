package com.farma.estoque.dto;

import com.farma.estoque.model.Tarja;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicamentoRequestDTO {

    @NotBlank(message = "O código de barras é obrigatório")
    @Size(min = 13, max = 13, message = "O código de barras deve ter exatamente 13 caracteres")
    private String codigoBarras;

    @NotBlank(message = "O nome do medicamento é obrigatório")
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
    private String nome;

    @NotBlank(message = "O princípio ativo é obrigatório")
    @Size(max = 255, message = "O princípio ativo deve ter no máximo 255 caracteres")
    private String principioAtivo;

    @NotNull(message = "A tarja é obrigatória")
    private Tarja tarja;

    @NotBlank(message = "A dosagem é obrigatória")
    @Size(max = 100, message = "A dosagem deve ter no máximo 100 caracteres")
    private String dosagem;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @PositiveOrZero(message = "O estoque não pode ser negativo")
    private Integer quantidadeEstoque;

    @NotBlank(message = "O lote é obrigatório")
    @Size(max = 50, message = "O lote deve ter no máximo 50 caracteres")
    private String lote;

    @NotNull(message = "A data de validade é obrigatória")
    @Future(message = "A data de validade deve estar no futuro")
    private LocalDate dataValidade;

    @NotNull(message = "O ID do fabricante é obrigatório")
    private Long fabricanteId;
}
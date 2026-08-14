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
    private String nome;

    @NotBlank(message = "O princípio ativo é obrigatório")
    private String principioAtivo;

    @NotNull(message = "A tarja é obrigatória")
    private Tarja tarja;

    @NotBlank(message = "A dosagem é obrigatória")
    private String dosagem;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @PositiveOrZero(message = "O estoque não pode ser negativo")
    private Integer quantidadeEstoque;

    @NotBlank(message = "O lote é obrigatório")
    private String lote;

    @NotNull(message = "A data de validade é obrigatória")
    @Future(message = "A data de validade deve estar no futuro")
    private LocalDate dataValidade;

    @NotNull(message = "O ID do fabricante é obrigatório")
    private Long fabricanteId;

}

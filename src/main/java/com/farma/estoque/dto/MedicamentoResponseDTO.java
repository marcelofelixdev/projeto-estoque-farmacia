package com.farma.estoque.dto;

import com.farma.estoque.model.Medicamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicamentoResponseDTO {

    private Long id;
    private String nome;
    private String principioAtivo;
    private String dosagem;
    private Double preco;

    private FabricanteResponseDTO fabricante;

    public MedicamentoResponseDTO(Medicamento medicamento) {
        this.id = medicamento.getId();
        this.nome = medicamento.getNome();
        this.principioAtivo = medicamento.getPrincipioAtivo();
        this.dosagem = medicamento.getDosagem();
        this.preco = medicamento.getPreco();
        this.fabricante = new FabricanteResponseDTO(medicamento.getFabricante());
    }
}

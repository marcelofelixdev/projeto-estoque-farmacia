package com.farma.estoque.dto;

import com.farma.estoque.model.Tarja;
import com.farma.estoque.model.Medicamento;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicamentoResponseDTO {

    private Long id;
    private String codigoBarras;
    private String nome;
    private String principioAtivo;
    private Tarja tarja;
    private String dosagem;
    private Double preco;
    private Integer quantidadeEstoque;
    private String lote;
    private LocalDate dataValidade;
    private FabricanteResponseDTO fabricante;

    public MedicamentoResponseDTO(Medicamento medicamento) {
        this.id = medicamento.getId();
        this.codigoBarras = medicamento.getCodigoBarras();
        this.nome = medicamento.getNome();
        this.principioAtivo = medicamento.getPrincipioAtivo();
        this.tarja = medicamento.getTarja();
        this.dosagem = medicamento.getDosagem();
        this.preco = medicamento.getPreco();
        this.quantidadeEstoque = medicamento.getQuantidadeEstoque();
        this.lote = medicamento.getLote();
        this.dataValidade = medicamento.getDataValidade();
        this.fabricante = new FabricanteResponseDTO(medicamento.getFabricante());
    }
}

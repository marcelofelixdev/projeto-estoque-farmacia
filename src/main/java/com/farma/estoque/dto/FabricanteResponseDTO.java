package com.farma.estoque.dto;

import com.farma.estoque.model.Fabricante;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FabricanteResponseDTO {

    private Long id;
    private String nome;
    private String cnpj;

    public FabricanteResponseDTO(Fabricante fabricante) {
        this.id = fabricante.getId();
        this.nome = fabricante.getNome();
        this.cnpj = fabricante.getCnpj();
    }
}

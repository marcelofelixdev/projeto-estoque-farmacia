package com.farma.estoque.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String principioAtivo;

    private String dosagem;

    private Double preco;

    @ManyToOne

    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

}

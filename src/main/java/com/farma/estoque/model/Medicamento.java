package com.farma.estoque.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 13)
    private String codigoBarras;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String principioAtivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tarja tarja;

    @Column(nullable = false)
    private String dosagem;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Column(nullable = false)
    private String lote;

    @Column(nullable = false)
    private LocalDate dataValidade;

    @ManyToOne
    @JoinColumn(name = "fabricante_id", nullable = false)
    private Fabricante fabricante;
}
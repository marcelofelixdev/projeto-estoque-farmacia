package com.farma.estoque.repository;

import com.farma.estoque.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    List<Medicamento> findByPrincipioAtivo(String principioAtivo);

    List<Medicamento> findByFabricanteNome(String nomeFabricante);

    List<Medicamento> findByPrecoLessThan(Double precoMaximo);

}

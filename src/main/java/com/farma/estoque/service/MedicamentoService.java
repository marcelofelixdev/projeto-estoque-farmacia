package com.farma.estoque.service;

import com.farma.estoque.dto.MedicamentoRequestDTO;
import com.farma.estoque.model.Fabricante;
import com.farma.estoque.model.Medicamento;
import com.farma.estoque.repository.FabricanteRepository;
import com.farma.estoque.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepo;

    @Autowired
    private FabricanteRepository fabricanteRepo;

    public Medicamento cadastrarMedicamento(MedicamentoRequestDTO dto) {

        Fabricante fabricanteEncontrado = fabricanteRepo.findById(dto.getFabricanteId())
                .orElseThrow(() -> new RuntimeException("Fabricante não encontrado no banco de dados"));

        Medicamento novoMedicamento = new Medicamento();
        novoMedicamento.setNome(dto.getNome());
        novoMedicamento.setPrincipioAtivo(dto.getPrincipioAtivo());
        novoMedicamento.setDosagem(dto.getDosagem());
        novoMedicamento.setPreco(dto.getPreco());

        novoMedicamento.setFabricante(fabricanteEncontrado);

        return medicamentoRepo.save(novoMedicamento);
    }

    public Page<Medicamento> listarTodos(Pageable pageable) {
        return medicamentoRepo.findAll(pageable);
    }

    public Medicamento buscarPorId(Long id) {
        return medicamentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrada."));
    }

    public Medicamento atualizar(Long id, MedicamentoRequestDTO dto) {

        Medicamento medicamentoExistente = buscarPorId(id);

        Fabricante fabricanteEncontrado = fabricanteRepo.findById(dto.getFabricanteId())
                .orElseThrow(() -> new RuntimeException("Fabricante não encontrado no banco de dados"));

        medicamentoExistente.setNome(dto.getNome());
        medicamentoExistente.setPrincipioAtivo(dto.getPrincipioAtivo());
        medicamentoExistente.setDosagem(dto.getDosagem());
        medicamentoExistente.setPreco(dto.getPreco());
        medicamentoExistente.setFabricante(fabricanteEncontrado);

        return medicamentoRepo.save(medicamentoExistente);
    }

    public void excluir(Long id) {
        Medicamento medicamentoEncontrado = buscarPorId(id);

        medicamentoRepo.delete(medicamentoEncontrado);
    }
}

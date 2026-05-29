package com.farma.estoque.service;

import com.farma.estoque.dto.FabricanteRequestDTO;
import com.farma.estoque.model.Fabricante;
import com.farma.estoque.repository.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class FabricanteService {

    @Autowired
    private FabricanteRepository fabricanteRepo;

    public Fabricante cadastrar(FabricanteRequestDTO dto) {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome(dto.getNome());
        fabricante.setCnpj(dto.getCnpj());
        return  fabricanteRepo.save(fabricante);
    }

    public Page<Fabricante> listarTodos(Pageable pageable) {
        return fabricanteRepo.findAll(pageable);
    }

    public Fabricante buscarPorId(Long id) {
        return fabricanteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Fabricante não encontrado"));
    }

    public Fabricante atualizar(Long id, FabricanteRequestDTO dto) {
        Fabricante fabricanteExistente = buscarPorId(id);
        fabricanteExistente.setNome(dto.getNome());
        fabricanteExistente.setCnpj(dto.getCnpj());
        return fabricanteRepo.save(fabricanteExistente);
    }

    public void excluir(Long id) {
        Fabricante fabricante = buscarPorId(id);

        fabricanteRepo.delete(fabricante);
    }
}

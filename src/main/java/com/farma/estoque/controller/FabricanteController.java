package com.farma.estoque.controller;

import com.farma.estoque.dto.FabricanteRequestDTO;
import com.farma.estoque.dto.FabricanteResponseDTO;
import com.farma.estoque.model.Fabricante;
import com.farma.estoque.service.FabricanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/fabricantes")
public class FabricanteController {

    @Autowired
    private FabricanteService fabricanteService;

    @PostMapping
    public ResponseEntity<FabricanteResponseDTO> cadastrar(@RequestBody @Valid FabricanteRequestDTO dto){
        Fabricante fabricanteSalvo = fabricanteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new FabricanteResponseDTO(fabricanteSalvo));
    }

    @GetMapping
    public ResponseEntity<Page<Fabricante>> listarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        Page<FabricanteResponseDTO> paginaDto = fabricanteService.listarTodos(pageable)
                .map(FabricanteResponseDTO::new);

        return ResponseEntity.ok(fabricanteService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FabricanteResponseDTO> buscarPorId(@PathVariable Long id) {
        Fabricante fabricanteEncontrado = fabricanteService.buscarPorId(id);
        return ResponseEntity.ok(new FabricanteResponseDTO(fabricanteEncontrado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FabricanteResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid FabricanteRequestDTO dto) {
        Fabricante fabricanteAtualizado = fabricanteService.atualizar(id, dto);
        return ResponseEntity.ok(new FabricanteResponseDTO(fabricanteAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        fabricanteService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}

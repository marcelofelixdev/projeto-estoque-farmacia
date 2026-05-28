package com.farma.estoque.controller;

import com.farma.estoque.dto.FabricanteRequestDTO;
import com.farma.estoque.model.Fabricante;
import com.farma.estoque.service.FabricanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fabricantes")
public class FabricanteController {

    @Autowired
    private FabricanteService fabricanteService;

    @PostMapping
    public ResponseEntity<Fabricante> cadastrar(@RequestBody @Valid FabricanteRequestDTO dto){
        Fabricante fabricante = fabricanteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fabricante);
    }

    @GetMapping
    public ResponseEntity<List<Fabricante>> listarTodos(){
        return  ResponseEntity.ok(fabricanteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fabricante> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fabricanteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fabricante> buscarPorId(@PathVariable Long id, @RequestBody @Valid FabricanteRequestDTO dto) {
        return ResponseEntity.ok(fabricanteService.atualizar(id, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        fabricanteService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}

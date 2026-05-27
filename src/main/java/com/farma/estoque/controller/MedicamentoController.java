package com.farma.estoque.controller;

import com.farma.estoque.dto.MedicamentoRequestDTO;
import com.farma.estoque.model.Medicamento;
import com.farma.estoque.service.MedicamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @PostMapping
    public ResponseEntity<Medicamento> cadastrar(@RequestBody @Valid MedicamentoRequestDTO dto){

        Medicamento medicamentoSalvo = medicamentoService.cadastrarMedicamento(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Medicamento>> listar() {
        return ResponseEntity.ok(medicamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(medicamentoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> atualizar(@PathVariable Long id, @RequestBody @Valid MedicamentoRequestDTO dto) {

        Medicamento medicamentoAtualizado = medicamentoService.atualizar(id, dto);

        return ResponseEntity.ok(medicamentoAtualizado);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        medicamentoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}

package com.farma.estoque.controller;

import com.farma.estoque.dto.MedicamentoRequestDTO;
import com.farma.estoque.dto.MedicamentoResponseDTO;
import com.farma.estoque.model.Medicamento;
import com.farma.estoque.service.MedicamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController

@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> cadastrar(@RequestBody @Valid MedicamentoRequestDTO dto){
        Medicamento medicamentoSalvo = medicamentoService.cadastrarMedicamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MedicamentoResponseDTO(medicamentoSalvo));
    }

    @GetMapping
    public ResponseEntity<Page<MedicamentoResponseDTO>> listarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        Page<MedicamentoResponseDTO> paginaDto = medicamentoService.listarTodos(pageable)
                .map(MedicamentoResponseDTO::new);

        return ResponseEntity.ok(paginaDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> buscarPorId(@PathVariable Long id){
        Medicamento medicamentoEncontrado = medicamentoService.buscarPorId(id);
        return ResponseEntity.ok(new MedicamentoResponseDTO(medicamentoEncontrado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MedicamentoRequestDTO dto) {

        Medicamento medicamentoAtualizado = medicamentoService.atualizar(id, dto);

        return ResponseEntity.ok(new  MedicamentoResponseDTO(medicamentoAtualizado));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        medicamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

package com.farma.estoque.service;

import com.farma.estoque.dto.MedicamentoRequestDTO;
import com.farma.estoque.model.Fabricante;
import com.farma.estoque.model.Medicamento;
import com.farma.estoque.repository.FabricanteRepository;
import com.farma.estoque.repository.MedicamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MedicamentoServiceTest {

    @InjectMocks
    private MedicamentoService medicamentoService;

    @Mock
    private MedicamentoRepository medicamentoRepo;

    @Mock
    private FabricanteRepository fabricanteRepo;

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar remédio com fabricante que não existe")
    void cadastrarMedicamento_ComFabricanteInexistente_DeveLancarExcecao() {
        MedicamentoRequestDTO dto = new MedicamentoRequestDTO();
        dto.setNome("Dorflex");
        dto.setFabricanteId(999L);

        Mockito.when(fabricanteRepo.findById(999L)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            medicamentoService.cadastrarMedicamento(dto);
        });

        assertEquals("Fabricante não encontrado no banco de dados.", excecao.getMessage());

        Mockito.verify(medicamentoRepo, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Deve cadastrar medicamento com sucesso quando fabricante existe")
    void cadastrarMedicamento_ComSucesso() {
        MedicamentoRequestDTO dto = new MedicamentoRequestDTO();
        dto.setNome("Aspirina");
        dto.setFabricanteId(1L);

        Fabricante fabricanteSimulado = new Fabricante();
        fabricanteSimulado.setId(1L);
        fabricanteSimulado.setNome("Bayer");

        Medicamento medicamentoSalvoSimulado = new Medicamento();
        medicamentoSalvoSimulado.setId(100L);
        medicamentoSalvoSimulado.setNome("Aspirina");
        medicamentoSalvoSimulado.setFabricante(fabricanteSimulado);

        Mockito.when(fabricanteRepo.findById(1L)).thenReturn(Optional.of(fabricanteSimulado));
        Mockito.when(medicamentoRepo.save(any(Medicamento.class))).thenReturn(medicamentoSalvoSimulado);

        Medicamento resultado = medicamentoService.cadastrarMedicamento(dto);

        assertNotNull(resultado.getId());
        assertEquals(100L, resultado.getId());
        assertEquals("Aspirina", resultado.getNome());
        assertEquals("Bayer", resultado.getFabricante().getNome());
    }
}
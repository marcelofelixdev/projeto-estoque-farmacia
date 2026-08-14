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

// Avisa o JUnit que vamos usar o Mockito para criar dublês
@ExtendWith(MockitoExtension.class)
class MedicamentoServiceTest {

    // @InjectMocks: É a classe REAL que vamos testar. O Mockito vai injetar os dublês dentro dela.
    @InjectMocks
    private MedicamentoService medicamentoService;

    // @Mock: São os dublês! Eles fingem ser o banco de dados.
    @Mock
    private MedicamentoRepository medicamentoRepo;

    @Mock
    private FabricanteRepository fabricanteRepo;

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar remédio com fabricante que não existe")
    void cadastrarMedicamento_ComFabricanteInexistente_DeveLancarExcecao() {
        // 1. ARRANGE (Preparar o cenário)
        MedicamentoRequestDTO dto = new MedicamentoRequestDTO();
        dto.setNome("Dorflex");
        dto.setFabricanteId(999L); // ID fantasma

        // Ensinamos o dublê: "Quando alguém buscar o ID 999, devolva VAZIO (Optional.empty)"
        Mockito.when(fabricanteRepo.findById(999L)).thenReturn(Optional.empty());

        // 2 & 3. ACT & ASSERT (Agir e Validar ao mesmo tempo)
        // Verificamos se o Service realmente joga uma RuntimeException e se a mensagem está certa
        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            medicamentoService.cadastrarMedicamento(dto);
        });

        assertEquals("Fabricante não encontrado no banco de dados.", excecao.getMessage());

        // Garante que o método save() NUNCA foi chamado, protegendo o banco!
        Mockito.verify(medicamentoRepo, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Deve cadastrar medicamento com sucesso quando fabricante existe")
    void cadastrarMedicamento_ComSucesso() {
        // 1. ARRANGE
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

        // Ensinando os dublês
        Mockito.when(fabricanteRepo.findById(1L)).thenReturn(Optional.of(fabricanteSimulado));
        Mockito.when(medicamentoRepo.save(any(Medicamento.class))).thenReturn(medicamentoSalvoSimulado);

        // 2. ACT
        Medicamento resultado = medicamentoService.cadastrarMedicamento(dto);

        // 3. ASSERT
        assertNotNull(resultado.getId());
        assertEquals(100L, resultado.getId());
        assertEquals("Aspirina", resultado.getNome());
        assertEquals("Bayer", resultado.getFabricante().getNome());
    }
}
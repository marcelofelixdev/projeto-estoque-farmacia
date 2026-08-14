package com.farma.estoque.service;

import com.farma.estoque.dto.MedicamentoRequestDTO;
import com.farma.estoque.model.Fabricante;
import com.farma.estoque.model.Medicamento;
import com.farma.estoque.model.Tarja;
import com.farma.estoque.repository.FabricanteRepository;
import com.farma.estoque.repository.MedicamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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

    // metodo auxiliar para menos repetições no código

    private MedicamentoRequestDTO criarDtoValido() {
        MedicamentoRequestDTO dto = new MedicamentoRequestDTO();
        dto.setCodigoBarras("7891234567890");
        dto.setNome("Dorflex");
        dto.setPrincipioAtivo("Dipirona");
        dto.setTarja(Tarja.LIVRE);
        dto.setDosagem("1g");
        dto.setPreco(20.0);
        dto.setQuantidadeEstoque(100);
        dto.setLote("L-102030");
        dto.setDataValidade(LocalDate.of(2028, 5, 15));
        dto.setFabricanteId(1L);
        return dto;
    }

    private Fabricante criarFabricanteValido() {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(1L);
        fabricante.setNome("Medley");
        fabricante.setCnpj("50.929.710/0001-79");
        return fabricante;
    }

    // cenarios de teste

    @Test
    @DisplayName("Deve cadastrar medicamento com sucesso quando todos os dados e fabricante são válidos")
    void cadastrarMedicamento_ComSucesso() {
        MedicamentoRequestDTO dto = criarDtoValido();
        Fabricante fabricante = criarFabricanteValido();

        Medicamento medicamentoSimulado = new Medicamento();
        medicamentoSimulado.setId(10L);
        medicamentoSimulado.setNome(dto.getNome());
        medicamentoSimulado.setCodigoBarras(dto.getCodigoBarras());
        medicamentoSimulado.setFabricante(fabricante);

        Mockito.when(fabricanteRepo.findById(1L)).thenReturn(Optional.of(fabricante));
        Mockito.when(medicamentoRepo.save(any(Medicamento.class))).thenReturn(medicamentoSimulado);

        Medicamento resultado = medicamentoService.cadastrarMedicamento(dto);

        assertNotNull(resultado.getId());
        assertEquals("Dorflex", resultado.getNome());
        assertEquals("7891234567890", resultado.getCodigoBarras());

        // Garante que o método save foi chamado exatamente 1 vez
        Mockito.verify(medicamentoRepo, Mockito.times(1)).save(any(Medicamento.class));
    }

    @Test
    @DisplayName("Deve bloquear o cadastro e lançar exceção se o Fabricante não existir")
    void cadastrarMedicamento_FabricanteInexistente_DeveLancarExcecao() {
        MedicamentoRequestDTO dto = criarDtoValido();
        dto.setFabricanteId(99L); // ID fantasma

        Mockito.when(fabricanteRepo.findById(99L)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            medicamentoService.cadastrarMedicamento(dto);
        });

        assertEquals("Fabricante não encontrado no banco de dados", excecao.getMessage());

        // Proteção máxima: Garante que o sistema NUNCA tentou salvar no banco
        Mockito.verify(medicamentoRepo, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar as informações do medicamento corretamente")
    void atualizarMedicamento_ComSucesso() {
        Long idMedicamento = 10L;
        MedicamentoRequestDTO dto = criarDtoValido();
        dto.setNome("Dorflex Atualizado"); // Mudamos o nome para simular a edição

        Medicamento medicamentoExistente = new Medicamento();
        medicamentoExistente.setId(idMedicamento);
        medicamentoExistente.setNome("Dorflex Antigo");

        Fabricante fabricante = criarFabricanteValido();

        Mockito.when(medicamentoRepo.findById(idMedicamento)).thenReturn(Optional.of(medicamentoExistente));
        Mockito.when(fabricanteRepo.findById(1L)).thenReturn(Optional.of(fabricante));
        Mockito.when(medicamentoRepo.save(any(Medicamento.class))).thenReturn(medicamentoExistente);

        Medicamento resultado = medicamentoService.atualizar(idMedicamento, dto);

        assertEquals("Dorflex Atualizado", resultado.getNome());
        Mockito.verify(medicamentoRepo).save(medicamentoExistente);
    }

    @Test
    @DisplayName("Deve excluir o medicamento quando o ID for encontrado")
    void excluirMedicamento_ComSucesso() {
        Long idMedicamento = 10L;
        Medicamento medicamentoExistente = new Medicamento();
        medicamentoExistente.setId(idMedicamento);

        Mockito.when(medicamentoRepo.findById(idMedicamento)).thenReturn(Optional.of(medicamentoExistente));

        medicamentoService.excluir(idMedicamento);

        // Verifica se o delete foi disparado passando o objeto correto
        Mockito.verify(medicamentoRepo, Mockito.times(1)).delete(medicamentoExistente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir um ID que não existe")
    void excluirMedicamento_Inexistente_DeveLancarExcecao() {
        Long idMedicamento = 99L;
        Mockito.when(medicamentoRepo.findById(idMedicamento)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            medicamentoService.excluir(idMedicamento);
        });

        assertEquals("Medicamento não encontrada.", excecao.getMessage());
        Mockito.verify(medicamentoRepo, Mockito.never()).delete(any());
    }
}
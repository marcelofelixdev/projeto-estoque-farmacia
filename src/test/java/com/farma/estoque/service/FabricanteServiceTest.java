package com.farma.estoque.service;

import com.farma.estoque.dto.FabricanteRequestDTO;
import com.farma.estoque.model.Fabricante;
import com.farma.estoque.repository.FabricanteRepository;
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
class FabricanteServiceTest {

    @InjectMocks
    private FabricanteService fabricanteService;

    @Mock
    private FabricanteRepository fabricanteRepo;

    private FabricanteRequestDTO criarDtoValido() {
        FabricanteRequestDTO dto = new FabricanteRequestDTO();
        dto.setNome("Eurofarma");
        dto.setCnpj("61.190.096/0001-92");
        dto.setTelefone("(11) 3888-0000");
        dto.setEmail("vendas@eurofarma.com.br");
        return dto;
    }

    @Test
    @DisplayName("Deve cadastrar um novo fabricante com todos os campos logísticos")
    void cadastrarFabricante_ComSucesso() {
        FabricanteRequestDTO dto = criarDtoValido();

        Fabricante fabricanteSimulado = new Fabricante();
        fabricanteSimulado.setId(1L);
        fabricanteSimulado.setNome(dto.getNome());
        fabricanteSimulado.setCnpj(dto.getCnpj());
        fabricanteSimulado.setTelefone(dto.getTelefone());
        fabricanteSimulado.setEmail(dto.getEmail());

        Mockito.when(fabricanteRepo.save(any(Fabricante.class))).thenReturn(fabricanteSimulado);

        // Ajuste o nome do metodo caso no seu Service se chame 'cadastrar', 'salvar', etc.
        Fabricante resultado = fabricanteService.cadastrar(dto);

        assertNotNull(resultado.getId());
        assertEquals("Eurofarma", resultado.getNome());
        assertEquals("vendas@eurofarma.com.br", resultado.getEmail());
        Mockito.verify(fabricanteRepo, Mockito.times(1)).save(any(Fabricante.class));
    }

    @Test
    @DisplayName("Deve buscar um fabricante por ID com sucesso")
    void buscarPorId_ComSucesso() {
        Long id = 1L;
        Fabricante fabricante = new Fabricante();
        fabricante.setId(id);
        fabricante.setNome("Bayer");

        Mockito.when(fabricanteRepo.findById(id)).thenReturn(Optional.of(fabricante));

        Fabricante resultado = fabricanteService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals("Bayer", resultado.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar fabricante que não existe")
    void buscarPorId_Inexistente_DeveLancarExcecao() {
        Long id = 99L;

        Mockito.when(fabricanteRepo.findById(id)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> fabricanteService.buscarPorId(id));

        assertNotNull(excecao.getMessage());
    }
}
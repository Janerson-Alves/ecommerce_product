package br.com.jaanalves.ecommerce_product.services;

import br.com.jaanalves.ecommerce_product.dto.ProdutoRequestDTO;
import br.com.jaanalves.ecommerce_product.dto.ProdutoResponseDTO;
import br.com.jaanalves.ecommerce_product.entities.Produto;
import br.com.jaanalves.ecommerce_product.exceptions.ResourceNotFoundException;
import br.com.jaanalves.ecommerce_product.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;
    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;
    private ProdutoRequestDTO requestDTO;

    // - Dados para testes
    @BeforeEach
    void setUp() {
        produto = new Produto(1L, "Teclado Mecânico", "Teclado RGB Switch Blue",
                new BigDecimal("250.00"), 10);
        requestDTO = new ProdutoRequestDTO("Teclado Mecânico", "Teclado RGB Switch Blue",
                new BigDecimal("250.00"), 10);
    }

    // TESTE 1 - Cadastrando Produtos
    @Test
    @DisplayName("Deve cadastrar produto com sucesso quando o nome não for repetido")
    void deveCadastrarProdutoComSucesso() {
        // Verifica se o nome do produto ja existe, se não existir, retorna false
        when(produtoRepository.existsByNome(requestDTO.getNome())).thenReturn(false);
        // Salvando os produtos
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        // ACT
        // - Cadastrando o produto usando o service
        ProdutoResponseDTO responseDTO = produtoService.cadastrarProduto(requestDTO);

        // ASSERTS
        // - Verifica se os dados estão corretos
        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(produto.getId(), responseDTO.getId());
        Assertions.assertEquals(produto.getDescricao(), responseDTO.getDescricao());
        Assertions.assertEquals(produto.getPreco(), responseDTO.getPreco());
        Assertions.assertEquals(produto.getQuantidadeEstoque(), responseDTO.getQuantidadeEstoque());
        // Confirma que o método foi chamado.
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    @DisplayName("Deve Lançar IllegalArgumentException ao tentar cadastrar um produto com nome duplicado")
    void deveLancarExcecaoComNomeDuplicado() {
        // Lança a exceção para nome duplicado
        when(produtoRepository.existsByNome(requestDTO.getNome())).thenReturn(true);
        // Pega a exceção que e lançada
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            produtoService.cadastrarProduto(requestDTO);
        });

        // ASSERTS
        // - Verifica se os dados estão corretos para gerar a exceção
        Assertions.assertEquals("Esse produto já existe. Favor cadastrar um produto novo.", exception.getMessage());
        // Confirma que o método foi chamado.
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    @DisplayName("Realiza a busca por Id com sucesso.")
    void buscarPorIdComSucesso() {
        // Busca o Id, se existir, ele retorna o produto
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        // Busca o produto por ID
        ProdutoResponseDTO responseDTO = produtoService.buscarPorId(1L);

        // ASSERT
        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(1L, responseDTO.getId());
        Assertions.assertEquals("Teclado Mecânico", responseDTO.getNome());
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando ID não existir ao buscar")
    void deveLancarExcecaoIdInexistente() {
        // se o ID não existir, retorna vazio
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());
        // ASSERT para verificar o retorno se e Resource
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            produtoService.buscarPorId(99L);
        });
    }

    @Test
    @DisplayName("Atualiza Produto com sucesso pelo ID")
    void atualizarProdutoComSucesso() {
        // Busca o ID, se existir, ele retorna o produto
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        // Novo Produto Atualizado
        ProdutoRequestDTO produtoAtualizado = new ProdutoRequestDTO();
        produtoAtualizado.setNome("Iphone 18");
        produtoAtualizado.setDescricao("Lançado em 2026");
        produtoAtualizado.setPreco(new BigDecimal("10999.99"));
        produtoAtualizado.setQuantidadeEstoque(10);

        // Simulando o Salvamento
        when(produtoRepository.save(any(Produto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // ACT - Atualiza Produto
        ProdutoResponseDTO responseAtualizadoDTO = produtoService.atualizarProdutoPorId(1L, produtoAtualizado);

        // ASSERT
        Assertions.assertNotNull(responseAtualizadoDTO);
        Assertions.assertEquals("Iphone 18", responseAtualizadoDTO.getNome());
        Assertions.assertEquals("Lançado em 2026", responseAtualizadoDTO.getDescricao());
        Assertions.assertEquals(new BigDecimal("10999.99"), responseAtualizadoDTO.getPreco());
        Assertions.assertEquals(10, responseAtualizadoDTO.getQuantidadeEstoque());

        // verifica se o metodo foi chamado
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).save(any(Produto.class));

    }

    @Test
    @DisplayName("Deve Excluir produto com sucesso quando ID existir")
    void deveExcluirProdutoComSucesso() {
        // Busca o Id, se existir, ele retorna o produto
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        // Metodo nao retorna nada entao devemos realizar dessa forma
        doNothing().when(produtoRepository).delete(produto);

        //ASSERTS
        Assertions.assertDoesNotThrow(() -> produtoService.excluirProduto(1L));
        // verifica se o metodo foi chamado
        verify(produtoRepository, times(1)).delete(produto);
    }

}

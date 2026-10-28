package br.com.jaanalves.ecommerce_product.services;

import br.com.jaanalves.ecommerce_product.dto.ProdutoRequestDTO;
import br.com.jaanalves.ecommerce_product.dto.ProdutoResponseDTO;
import br.com.jaanalves.ecommerce_product.entities.Produto;
import br.com.jaanalves.ecommerce_product.exceptions.ResourceNotFoundException;
import br.com.jaanalves.ecommerce_product.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    // Cadastro do produto
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO dto) {
        // Verifica se o nome do produto já existe
        if (produtoRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Esse produto já existe. Favor cadastrar um produto novo.");
        }

        // Convertendo um DTO para Entities
        Produto produto = new Produto();
            produto.setNome(dto.getNome());
            produto.setDescricao(dto.getDescricao());
            produto.setPreco(dto.getPreco());
            produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        // Salvando no banco
        Produto produtoSalvo = produtoRepository.save(produto);

        // Criando o DTO para retorno
        return new ProdutoResponseDTO(
                produtoSalvo.getId(),
                produtoSalvo.getNome(),
                produtoSalvo.getDescricao(),
                produtoSalvo.getPreco(),
                produtoSalvo.getQuantidadeEstoque()
        );
    }

    // Listar Produtos
    public Page<ProdutoResponseDTO> listarProdutos(String nome, BigDecimal precoInicio,
                                                   BigDecimal precoFim, Pageable pageable) {
        // Armazena em uma lista os produtos cadastrados no determinado filtro
        Page<Produto> listarProdutosPage = produtoRepository.findByNomeContainingIgnoreCaseAndPrecoBetween(
                nome, precoInicio, precoFim, pageable
        );
        // retorna os produtos cadastrados em um DTO
        return listarProdutosPage
                .map(t -> new ProdutoResponseDTO(t.getId(), t.getNome(), t.getDescricao(),
                        t.getPreco(), t.getQuantidadeEstoque()));
    }

    // Buscar por Id
    public ProdutoResponseDTO buscarPorId(Long id) {
        // Armazena o Id se caso existir
        Optional<Produto> encontrado = produtoRepository.findById(id);

        // verifica se o Id não foi encontrado
        if (encontrado.isEmpty()) {
            throw new ResourceNotFoundException("ID do produto não encontrado");
        }
        // Armazena as informações do Id
        Produto produto = encontrado.get();
        // retorna as informações do produto encontrado.
        // Criando o DTO para retorno
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeEstoque()
        );

    }

    // Atualizar Produto.
    public ProdutoResponseDTO atualizarProdutoPorId(Long id, ProdutoRequestDTO produtoAtualizado) {
        // Armazena o Id se caso existir
        Optional<Produto> encontrado = produtoRepository.findById(id);

        // verifica se o Id não foi encontrado
        if (encontrado.isEmpty()) {
            throw new ResourceNotFoundException("ID do produto não encontrado");
        }

        // Pegando as informações do produto encontrado
        Produto produto = encontrado.get();
        // Atualizando os valores dos produtos.
        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());

        // Salva o produto no banco.
        Produto produtoSalvo = produtoRepository.save(produto);

        // Criando o DTO para retorno
        return new ProdutoResponseDTO(
                produtoSalvo.getId(),
                produtoSalvo.getNome(),
                produtoSalvo.getDescricao(),
                produtoSalvo.getPreco(),
                produtoSalvo.getQuantidadeEstoque()
        );

    }

    public void excluirProduto(Long id) {
        // Armazena o Id se caso existir
        Optional<Produto> produtoEncontrado = produtoRepository.findById(id);

        // verifica se o Id não foi encontrado
        if (produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException("ID do produto não encontrado");
        }
        // Pega as informações do produto
        Produto produto = produtoEncontrado.get();

        // Deleta o Produto e salva o banco
        produtoRepository.delete(produto);
    }
}

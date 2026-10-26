package br.com.jaanalves.ecommerce_product.services;

import br.com.jaanalves.ecommerce_product.entities.Produto;
import br.com.jaanalves.ecommerce_product.exceptions.ResourceNotFoundException;
import br.com.jaanalves.ecommerce_product.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    // Cadastro do produto
    public Produto cadastrarProduto(Produto produto) {
        // Verifica se o nome do produto já existe
        if (produtoRepository.existsByNome(produto.getNome())) {
            throw new IllegalArgumentException("Esse produto já existe. Favor cadastrar um produto novo.");
        }
        // Salvar o produto cadastrado no banco
        return produtoRepository.save(produto);
    }

    // Listar Produtos
    public List<Produto> listarProdutos() {
        // Armazena em uma lista todos os produtos cadastrados
        List<Produto> listarProdutos = produtoRepository.findAll();
        // retorna os produtos cadastrados em uma lista
        return listarProdutos;
    }

    // Buscar por Id
    public Produto buscarPorId(Long id) {
        // Armazena o Id se caso existir
        Optional<Produto> encontrado = produtoRepository.findById(id);

        // verifica se o Id não foi encontrado
        if (encontrado.isEmpty()) {
            throw new ResourceNotFoundException("ID do produto não encontrado");
        }
        // Armazena as informações do Id
        Produto produto = encontrado.get();
        // retorna as informações do produto encontrado.
        return produto;

    }

    // Atualizar Produto.
    public Produto atualizarProdutoPorId(Long id, Produto produtoAtualizado) {
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
        produtoRepository.save(produto);
        // Retorna o produto atualizado.
        return produto;

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

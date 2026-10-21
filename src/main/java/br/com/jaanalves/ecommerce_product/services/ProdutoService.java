package br.com.jaanalves.ecommerce_product.services;

import br.com.jaanalves.ecommerce_product.entities.Produto;
import br.com.jaanalves.ecommerce_product.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

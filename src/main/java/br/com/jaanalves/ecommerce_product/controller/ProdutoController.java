package br.com.jaanalves.ecommerce_product.controller;

import br.com.jaanalves.ecommerce_product.entities.Produto;
import br.com.jaanalves.ecommerce_product.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity cadastrarProduto(@Valid @RequestBody Produto produto) {
        produtoRepository.save(produto);
        return ResponseEntity.ok("Produto cadastrado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> buscaProduto = produtoRepository.findAll();
        return ResponseEntity.ok().body(buscaProduto);
    }
}

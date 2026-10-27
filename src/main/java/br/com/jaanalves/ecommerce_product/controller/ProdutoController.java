package br.com.jaanalves.ecommerce_product.controller;

import br.com.jaanalves.ecommerce_product.entities.Produto;
import br.com.jaanalves.ecommerce_product.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springdoc.core.annotations.ParameterObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @PostMapping
    public ResponseEntity cadastrarProduto(@Valid @RequestBody Produto produto) {
        produtoService.cadastrarProduto(produto);
        return ResponseEntity.ok("Produto cadastrado com sucesso");
    }

    @GetMapping
    public ResponseEntity<Page<Produto>> listarProdutos(
            @RequestParam String nome,
            @RequestParam BigDecimal valorInicio,
            @RequestParam BigDecimal valorFinal,
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<Produto> listarProdutos = produtoService.listarProdutos(nome, valorInicio, valorFinal, pageable);
        return ResponseEntity.ok().body(listarProdutos);
    }

    @GetMapping("/{id}")
    public ResponseEntity listaPorId(@PathVariable Long id) {
        Produto encontrado = produtoService.buscarPorId(id);
        return ResponseEntity.ok(encontrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizarProduto(@Valid @PathVariable Long id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizarProdutoPorId(id, produto);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Produto atualizado com sucesso!",
                "produto", produtoAtualizado
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.ok("Produto deletado com sucesso");
    }
}

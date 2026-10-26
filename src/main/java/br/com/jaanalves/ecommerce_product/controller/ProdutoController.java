package br.com.jaanalves.ecommerce_product.controller;

import br.com.jaanalves.ecommerce_product.entities.Produto;
import br.com.jaanalves.ecommerce_product.repository.ProdutoRepository;
import br.com.jaanalves.ecommerce_product.services.ProdutoService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> listarProdutos = produtoService.listarProdutos();
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

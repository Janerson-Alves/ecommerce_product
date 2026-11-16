package br.com.jaanalves.ecommerce_product.controller;

import br.com.jaanalves.ecommerce_product.dto.ProdutoRequestDTO;
import br.com.jaanalves.ecommerce_product.dto.ProdutoResponseDTO;
import br.com.jaanalves.ecommerce_product.entities.Produto;
import br.com.jaanalves.ecommerce_product.repository.ProdutoRepository;
import br.com.jaanalves.ecommerce_product.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;
    @Autowired
    private ProdutoRepository produtoRepository;

    @Operation(summary = "Cadastro de produtos", description = "Realiza cadastros de produto e salva no banco.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou produto já existente")
    })
    @PostMapping
    public ResponseEntity cadastrarProduto(@Valid @RequestBody ProdutoRequestDTO produtoDTO) {
        produtoService.cadastrarProduto(produtoDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body("Produto Cadastrado com Sucesso.");
    }

    @Operation(summary = "Lista os produtos cadastrados.", description = "Lista os produtos cadastrados paginalmente.")
    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDTO>> listarProdutos(
            @RequestParam String nome,
            @RequestParam BigDecimal valorInicio,
            @RequestParam BigDecimal valorFinal,
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<ProdutoResponseDTO> listarProdutos = produtoService.listarProdutos(nome, valorInicio, valorFinal, pageable);
        return ResponseEntity.ok().body(listarProdutos);
    }

    @Operation(summary = "Lista por ID.", description = "Lista o produto filtrado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity listaPorId(@PathVariable Long id) {
        ProdutoResponseDTO encontrado = produtoService.buscarPorId(id);
        return ResponseEntity.ok(encontrado);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @Operation(summary = "Atualização de produto.", description = "Atualiza o produto filtrado pelo ID.")
    @PutMapping("/{id}")
    public ResponseEntity atualizarProduto(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDTO produto) {
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizarProdutoPorId(id, produto);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Produto atualizado com sucesso!",
                "produto", produtoAtualizado
        ));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto excluido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @Operation(summary = "Excluir produto.", description = "Exclui um produto cadastrado filtrado pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity deletarProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.ok("Produto deletado com sucesso");
    }

    @Operation(summary = "Reativar produto.", description = "Reativa um produto desativado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto reativado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Produto já está ativo"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/{id};reativar")
    public ResponseEntity reativarProduto(@PathVariable Long id){
        produtoService.reativarProduto(id);
        return ResponseEntity.ok("Produto reativado com sucesso");
    }
}

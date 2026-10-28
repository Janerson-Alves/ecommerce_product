package br.com.jaanalves.ecommerce_product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class ProdutoRequestDTO {
    @NotBlank(message = "O Nome do Produto é obrigatório")
    private String nome;
    @NotBlank(message = "A descrição do produto é obrigatório")
    private String descricao;
    @NotNull(message = "O valor do produto é obrigatorio")
    @Positive(message = "O preço precisa ser maior que zero")
    private BigDecimal preco;
    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Positive(message = "A quantidade minima no estoque deve ser maior ou igual a 1")
    private Integer quantidadeEstoque;

    // Construtor
    public ProdutoRequestDTO() {}
    public ProdutoRequestDTO(String nome, String descricao, BigDecimal preco, Integer quantidadeEstoque) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
}


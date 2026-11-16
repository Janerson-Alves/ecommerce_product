package br.com.jaanalves.ecommerce_product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class ProdutoResponseDTO {
    private Long id;
    @Schema(description = "Nome do produto", example = "Iphone 17 Pro max")
    private String nome;
    @Schema(description = "Descrição do Produto", example = "256GB novo")
    private String descricao;
    @Schema(description = "Preço do produto", example = "3999.99")
    private BigDecimal preco;
    @Schema(description = "Quantidade em estoque disponivel", example = "10")
    private Integer quantidadeEstoque;

    // Construtor
    public ProdutoResponseDTO() {}

    public ProdutoResponseDTO(Long id, String nome, String descricao, BigDecimal preco, Integer quantidadeEstoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
}

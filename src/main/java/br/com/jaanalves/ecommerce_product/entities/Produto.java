package br.com.jaanalves.ecommerce_product.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O Nome do Produto é obrigatório")
    @Column(nullable = false)
    private String nome;
    @NotBlank(message = "A descrição do produto é obrigatório")
    @Column(nullable = false)
    private String descricao;
    @NotNull(message = "O valor do produto é obrigatorio")
    @Positive(message = "O preço precisa ser maior que zero")
    @Column(nullable = false)
    private BigDecimal preco;
    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Positive(message = "A quantidade minima no estoque deve ser maior ou igual a 1")
    private Integer quantidadeEstoque;

    // Construtor
    public Produto() {}

    public Produto(Long id, String nome, String descricao, BigDecimal preco, Integer quantidadeEstoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }
    // GETTERS e SETTERS
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

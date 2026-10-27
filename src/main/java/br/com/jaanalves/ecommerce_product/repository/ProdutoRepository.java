package br.com.jaanalves.ecommerce_product.repository;

import br.com.jaanalves.ecommerce_product.entities.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByNome(String nome);

    Optional<Produto> findById(Long id);

    // Busca paginada filtrando por faixa de preço ou nome parcial.
    Page<Produto> findByNomeContainingIgnoreCaseAndPrecoBetween(
            String nome,
            BigDecimal precoInicio,
            BigDecimal precoFim,
            Pageable pageable
    );


}

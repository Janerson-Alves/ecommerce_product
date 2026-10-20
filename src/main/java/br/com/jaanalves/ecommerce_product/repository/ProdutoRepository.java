package br.com.jaanalves.ecommerce_product.repository;

import br.com.jaanalves.ecommerce_product.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}

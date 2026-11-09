package br.com.jaanalves.ecommerce_product.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ecommerce de Produtos")
                        .version("1.0.0")
                        .description("API RESTful para gestão de cadastro e estoque de produtos")
                );
    }
}
